package dk.spinoff.apps.feedback_app.aktiviteter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.transition.AutoTransition;
import android.view.MenuItem;
import android.widget.TextView;

import dk.spinoff.apps.feedback_app.PersonData;
import com.spinoff.apps.feedback_app.R;
import dk.spinoff.apps.feedback_app.fragmenter.OpretMoede_1_frg;
import dk.spinoff.apps.feedback_app.fragmenter.Moedeoversigt_frg;
import dk.spinoff.apps.feedback_app.fragmenter.Profil_frg;
import com.google.firebase.auth.FirebaseAuth;

public class Navigation_akt extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    private static final String TAG = "loginSide";

    TextView overskrift_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        BottomNavigationView navigation = findViewById(R.id.navigation_bar);
        navigation.setOnNavigationItemSelectedListener(this);

        overskrift_txt = findViewById(R.id.navigation_title_txt);


        indlæsFragment(new OpretMoede_1_frg());
        overskrift_txt.setText(getString(R.string.opretmoede));


    }

    private boolean indlæsFragment(Fragment fragment) {
        if (fragment != null) {

            fragment.setEnterTransition(new AutoTransition().setDuration(100));

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

        if (getSupportFragmentManager().getBackStackEntryCount() > 0 ) {
            getSupportFragmentManager().popBackStack();
        }

        switch (item.getItemId()) {
            case R.id.navigation_hjem:
                fragment = new OpretMoede_1_frg();
                overskrift_txt.setText(getString(R.string.opretmoede));
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
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 0 ) {
            getSupportFragmentManager().popBackStack();
        }
        else{

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

    }
}
