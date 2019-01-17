package com.example.admin.feedback_app.aktiviteter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.feedback_app.FeedbackManager;
import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.Svar;
import com.example.admin.feedback_app.fragmenter.Feedback_frg;

/**
 * Noget af koden er taget direkte fra Googles egen dokumentation om ViewPager
 * https://developer.android.com/training/animation/screen-slide#java
 *
 */
public class Overholdt_Giv_Feedback_akt extends AppCompatActivity implements View.OnClickListener {

    private Button knapVidere, knapTilbage;
    private TextView tekstNummer;
    private String TAG = "feedbackfragmentet";

    private static final int NUM_PAGES = 8; //Fast indtil videre
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    private FeedbackManager feedbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overholdt__giv__feedback);

        String datoString = getIntent().getStringExtra("MØDEID");
        String stedString = getIntent().getStringExtra("MØDEIDdel");

        feedbackManager = FeedbackManager.getInstance();
        feedbackManager.startFeedback(NUM_PAGES);

        // Instantiate a ViewPager and a PagerAdapter.
        viewPager = findViewById(R.id.feedback_pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int i) {
                updateView(i);
            }
        });

        //Knapper
        knapVidere = findViewById(R.id.feedback_videre_btn);
        knapTilbage = findViewById(R.id.feedback_tilbage_btn);
        knapVidere.setOnClickListener(this);
        knapTilbage.setOnClickListener(this);

        //Tekst
        tekstNummer = findViewById(R.id.feedback_nummer_txtView);
        updateView(viewPager.getCurrentItem());
    }

    private void updateView(int pos){
        if (pos > 0){
            if (!feedbackManager.erFeedbackUdfyldt(pos -1)){
                viewPager.setCurrentItem(pos - 1);
                //TODO give besked om at der mangler at vælges smiley + ryste animation
                return;
            }
        }

        tekstNummer.setText(String.format(getString(R.string.feedback_side_format), pos + 1, NUM_PAGES));

        if (pos <= 0){
            knapTilbage.setVisibility(View.INVISIBLE);
        }
        else if (pos >= NUM_PAGES - 1){
            knapVidere.setText(R.string.afslut);
            //knapVidere.setBackgroundColor(getColor(R.color.colorGrøn)); TODO mangler knapper
        }
        else {
            knapTilbage.setVisibility(View.VISIBLE);
            knapVidere.setText(R.string.videre);
            //knapVidere.setBackgroundColor(til orgiginal); TODO mangler knapper
        }
    }

    private void manglerSvar(){
        //TODO ryst animation
        Toast.makeText(this, R.string.mangler_smiley, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {

            new AlertDialog.Builder(this)
                    .setMessage(R.string.anuller_feedback)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.annuller, null)
                    .create()
                    .show();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == knapTilbage){
            onBackPressed();
        }
        else if (view == knapVidere){
            if (viewPager.getCurrentItem() + 1 < NUM_PAGES){
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
            else {
                if (feedbackManager.erFeedbackUdfyldt(NUM_PAGES-1)) {
                    Intent intent = new Intent(this, TakForFeedback.class);
                    startActivity(intent);
                }
                else {

                }
            }
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return Feedback_frg.newInstance(position,"Spørgsmål " + ++position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


}
