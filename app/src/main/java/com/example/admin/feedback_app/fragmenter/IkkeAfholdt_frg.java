package com.example.admin.feedback_app.fragmenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.feedback_app.Møde;
import com.example.admin.feedback_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class IkkeAfholdt_frg extends Fragment {

    ArrayList<Møde> møder;

    private static final String TAG = "ikkeAfholdt";

    private TextView møde1, møde2;

    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_ikke_afholdt, container, false);

        møde1 = v.findViewById(R.id.møde1);
        møde2 = v.findViewById(R.id.møde2);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();



        møder = new ArrayList<>();


        mFirestore.collection("møder")
                .whereEqualTo("mødeholderID", mAuth.getCurrentUser().getUid())
                //.whereEqualTo("afholdt",false)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int index=0;
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Møde mødeObj = new Møde(
                                        document.get("navn").toString(),
                                        document.get("tid").toString(),
                                        document.get("sted").toString(),
                                        document.get("dato").toString(),
                                        document.get("formål").toString(),
                                        document.get("mødeholderID").toString());


                               møder.add(index,mødeObj);

                                Log.d(TAG, "navn fra firebase: " + document.get("navn").toString());

                                Log.d(TAG, "mødelistens navn: " + møder.get(index).getNavn());

                                Log.d(TAG, "mødelistens navn ved index 0: " + møder.get(0).getNavn());

                                Log.d(TAG, document.getId() + " => " + document.getData());

                                index++;
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        
                        if(!møder.isEmpty()) {
                            Log.d(TAG, "onCreateView: navn"+møder.get(0).getNavn());
                            møde1.setText(møder.get(0).getNavn());
                            //møde1.setText(møder.get(2).getNavn());
                        }
                    }
                });


        return v;
    }

}
