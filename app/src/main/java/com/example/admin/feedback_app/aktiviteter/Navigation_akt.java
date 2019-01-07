package com.example.admin.feedback_app.aktiviteter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.fragmenter.Hjem_frg;
import com.example.admin.feedback_app.fragmenter.Moedeoversigt_frg;
import com.example.admin.feedback_app.fragmenter.Profil_frg;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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


    }

    @Override
    public void onBackPressed(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(Navigation_akt.this);
        builder.setMessage("Er du sikker på du vil logge ud?");
        builder.setCancelable(true);
        builder.setNegativeButton("Anuller", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Log ud", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                PersonData.getInstance().ryd();
                finish();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
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
                overskrift_txt.setText(getString(R.string.profil));
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
