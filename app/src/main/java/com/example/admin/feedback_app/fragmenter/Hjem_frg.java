package com.example.admin.feedback_app.fragmenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.feedback_app.Mødeholder;
import com.example.admin.feedback_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Hjem_frg extends Fragment {



    private TextView fraFire, fraFire2;
    public static Mødeholder mødeholder;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hjem, container, false);

        fraFire = v.findViewById(R.id.textViewFire);
        fraFire2 = v.findViewById(R.id.textViewfraFire2);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        mødeholder = new Mødeholder(null,null,null,null,null,null);



        DocumentReference docRef = mFirestore.collection("Mødeholder").document(mAuth.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        mødeholder.setFornavn(document.get("fornavn").toString());
                        mødeholder.setEfternavn(document.get("efternavn").toString());
                        mødeholder.setEmail(document.get("email").toString());
                        mødeholder.setPassword(document.get("password").toString());
                        mødeholder.setTlf(document.get("tlf").toString());
                        mødeholder.setVirk_id(document.get("virk_id").toString());



                       // Log.d(TAG, "onComplete: møder holder navn: "+Mødeholder.getFornavn());
                    } else {
                       // Log.d(TAG, "No such document");
                    }
                } else {
                   //Log.d(TAG, "get failed with ", task.getException());
                }

                fraFire.setText("Dit navn er "+mødeholder.getFornavn());
                fraFire2.setText("Din mail er "+mødeholder.getEmail());
            }
        });







        // Inflate the layout for this fragment
        return v;
    }

}
