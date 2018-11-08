package com.example.admin.feedback_app.aktiviteter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.fragmenter.Hjem_frg;
import com.example.admin.feedback_app.fragmenter.Moedeoversigt_frg;
import com.example.admin.feedback_app.fragmenter.Profil_frg;

public class Navigation_akt extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_bar);
        navigation.setOnNavigationItemSelectedListener(this);

        indlæsFragment(new Hjem_frg());
    }

    private boolean indlæsFragment(Fragment fragment){
        if (fragment != null){

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

        switch (item.getItemId()){
            case R.id.navigation_hjem:
                fragment = new Hjem_frg();
                break;

            case R.id.navigation_moedeoversigt:
                fragment = new Moedeoversigt_frg();
                break;

            case R.id.navigation_profil:
                fragment = new Profil_frg();
                break;
        }

        return indlæsFragment(fragment);
    }
}
