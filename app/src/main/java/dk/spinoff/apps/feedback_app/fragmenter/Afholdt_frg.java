package dk.spinoff.apps.feedback_app.fragmenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dk.spinoff.apps.feedback_app.Møde;
import dk.spinoff.apps.feedback_app.PersonData;
import dk.spinoff.apps.feedback_app.R;
import dk.spinoff.apps.feedback_app.Svar;
import dk.spinoff.apps.feedback_app.adaptere.AfholdtMødeAdapter;
import dk.spinoff.apps.feedback_app.aktiviteter.AfholdtMeode_akt;


public class Afholdt_frg extends Fragment implements AdapterView.OnItemClickListener {
    private static final String TAG = "Afholdt";
    private ListView listView;
    private SwipeRefreshLayout refreshLayout;
    private FirebaseAuth firebaseAuth;
    private int taskCount = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_moedeliste, container, false);
        listView = v.findViewById(R.id.moedeliste_listView);
        refreshLayout = v.findViewById(R.id.pullToRefresh);
        //setting an setOnRefreshListener on the SwipeDownLayout
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                //Here you can update your data from internet or from local SQLite data
                //Toast.makeText(getContext(), "Du refreshede ", Toast.LENGTH_SHORT).show();
                PersonData.getInstance().rydmøder();
                hentMøderFraFire();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        indlæsListView();
    }

    private void indlæsListView() {
        //Få mødenavne vist i et listview
        AfholdtMødeAdapter listviewAdapter = new AfholdtMødeAdapter(getActivity(),
                PersonData.getInstance().getAfholdteMøder());
        listView.setAdapter(listviewAdapter);
        listView.setOnItemClickListener(this);
        refreshLayout.setRefreshing(false);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), AfholdtMeode_akt.class);
        intent.putExtra("INDEKS", position);


        startActivity(intent);
    }

    private void hentMøderFraFire() {
        //updateProgressDialog("Henter møderne");
        FirebaseFirestore.getInstance().collection("Møder")
                .whereEqualTo("mødeholderID", firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new HeentMøderListener());
    }

    class HeentMøderListener implements OnCompleteListener<QuerySnapshot> {

        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                if (task.getResult().isEmpty()){
                    indlæsListView();
                    return;
                }
                taskCount += task.getResult().size();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Møde mødeObj = new Møde(
                            document.get("navn").toString(),
                            document.get("formål").toString(),
                            document.get("dato").toString(),
                            document.get("startTid").toString(),
                            document.get("slutTid").toString(),
                            document.get("sted").toString(),
                            document.get("mødeholderID").toString(),
                            document.getBoolean("afholdt")

                    );
                    mødeObj.setIgang(document.getBoolean("igang"));
                    mødeObj.setMødeIDtildeltager(document.get("mødeIDtildeltager").toString());
                    mødeObj.setMødeID(document.get("mødeID").toString());

                    PersonData.getInstance().tilføjMøde(mødeObj);

                    if (mødeObj.isAfholdt()) { 
                        FirebaseFirestore.getInstance()
                                .collection("Feedback")
                                .whereEqualTo("mødeId", mødeObj.getMødeID())
                                .get().addOnCompleteListener(new HeentFeedbackListener(mødeObj.getMødeID()));
                    }

                    taskCount--;
                }

                //Sorterer møderne så de ligger i sorteret rækkefølge ud fra dato
                //personData.sorterMøderne();

                if (taskCount <= 0)
                    indlæsListView();
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }

        }
    }

        class HeentFeedbackListener implements OnCompleteListener<QuerySnapshot> {

            private String mødeid;

            public HeentFeedbackListener(String mødeid) {
                this.mødeid = mødeid;
                taskCount++;
            }

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<List<Svar>> feedback = new ArrayList<>();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> map = document.getData();
                        List<Svar> svarListe = new ArrayList<>();

                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if (entry.getKey().equals("svar")) {
                                List<Object> person = (List<Object>) entry.getValue();
                                for (Object obj : person) {
                                    svarListe.add(Svar.getSvar(obj.toString()));
                                }
                            }
                        }

                        feedback.add(svarListe);
                    }

                    if (feedback.size() > 0)
                        PersonData.getInstance().tilføjFeedback(mødeid, feedback);

                    taskCount--;

                    if (taskCount <= 0) {
                        indlæsListView();
                    }
                }
            }
        }


}
