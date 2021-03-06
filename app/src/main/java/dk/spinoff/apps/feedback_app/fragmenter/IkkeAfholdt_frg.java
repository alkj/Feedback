package dk.spinoff.apps.feedback_app.fragmenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import dk.spinoff.apps.feedback_app.PersonData;
import dk.spinoff.apps.feedback_app.R;
import dk.spinoff.apps.feedback_app.adaptere.IkkeAfholdtMødeAdapter;
import dk.spinoff.apps.feedback_app.aktiviteter.IkkeAfholdtMoede_akt;


public class IkkeAfholdt_frg extends Fragment implements AdapterView.OnItemClickListener {
    private static final String TAG = "ikkeAfholdt";
    private ListView listView;
    private SwipeRefreshLayout refreshLayout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_moedeliste, container, false);
        listView = v.findViewById(R.id.moedeliste_listView);
        refreshLayout = v.findViewById(R.id.pullToRefresh);
        //setting an setOnRefreshListener on the SwipeDownLayout
        refreshLayout.setEnabled(false);


        return v;
    }

    @Override
    public void onResume(){
        super.onResume();

        indlæsListView();
    }

    private void indlæsListView(){
        //Få mødenavne vist i et listview
        IkkeAfholdtMødeAdapter listviewAdapter = new IkkeAfholdtMødeAdapter(getActivity(),
                PersonData.getInstance().getIkkeAfholdteMøder());
        listView.setAdapter(listviewAdapter);
        listView.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(getContext(), IkkeAfholdtMoede_akt.class);
        intent.putExtra("INDEKS", position);

        startActivity(intent);
    }
}
