package com.example.admin.feedback_app.aktiviteter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.fragmenter.Hjem_frg;
import com.example.admin.feedback_app.fragmenter.Moedeoversigt_frg;
import com.example.admin.feedback_app.fragmenter.Profil_frg;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Navigation_akt extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    private static final String TAG = "loginSide";

    TextView overskrift_txt, tilføj_btn;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;

    public static String fornavn, efternavn, email, tlf, password;

    //TODO: ved tryk af tilbage knappe, spørg brugeren om han vil logge ud.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        BottomNavigationView navigation = findViewById(R.id.navigation_bar);
        navigation.setOnNavigationItemSelectedListener(this);

        overskrift_txt = findViewById(R.id.navigation_title_txt);
        tilføj_btn = findViewById(R.id.navigation_opret_btn);

        tilføj_btn.setOnClickListener(this);

        indlæsFragment(new Hjem_frg());
        overskrift_txt.setText(getString(R.string.hjem));

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();




        mFirestore.collection("mødeholder")
                .whereEqualTo("id", mAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                password =  document.get("password").toString();
                                email =  document.get("email").toString();
                                fornavn =  document.get("fornavn").toString();
                                efternavn =  document.get("efternavn").toString();
                                tlf =  document.get("tlf").toString();

                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Log.d(TAG, document.getId() + " => " + password);
                                Log.d(TAG, document.getId() + " => " + email);
                                Log.d(TAG, document.getId() + " => " + fornavn);
                                Log.d(TAG, document.getId() + " => " + efternavn);

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    private boolean indlæsFragment(Fragment fragment) {
        if (fragment != null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.navigation_fragment_container, fragment)
                    .commit();

            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_hjem:
                fragment = new Hjem_frg();
                overskrift_txt.setText(getString(R.string.hjem));
                break;

            case R.id.navigation_moedeoversigt:
                fragment = new Moedeoversigt_frg();
                overskrift_txt.setText(getString(R.string.moder));
                break;

            case R.id.navigation_profil:
                fragment = new Profil_frg();
                overskrift_txt.setText(getString(R.string.hjem));
                break;
        }

        return indlæsFragment(fragment);
    }

    @Override
    public void onClick(View view) {
        if (view == tilføj_btn){
            Intent intent = new Intent(this, OpretMoede_akt.class);
            startActivity(intent);
        }
    }
}
