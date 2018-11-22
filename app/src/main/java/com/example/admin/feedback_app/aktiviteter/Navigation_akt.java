package com.example.admin.feedback_app.aktiviteter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.fragmenter.Hjem_frg;
import com.example.admin.feedback_app.fragmenter.Moedeoversigt_frg;
import com.example.admin.feedback_app.fragmenter.Profil_frg;

public class Navigation_akt extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

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
