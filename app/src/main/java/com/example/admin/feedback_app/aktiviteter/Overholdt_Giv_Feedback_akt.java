package com.example.admin.feedback_app.aktiviteter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.fragmenter.Feedback_frg;

/**
 * Noget af koden er taget direkte fra Googles egen dokumentation om ViewPager
 * https://developer.android.com/training/animation/screen-slide#java
 *
 */
public class Overholdt_Giv_Feedback_akt extends AppCompatActivity implements Feedback_frg.OnFragmentInteractionListener, View.OnClickListener {

    private Fragment fragmentFeedback;

    private Button knapVidere, knapTilbage;
    private TextView tekstNummer;
    private String TAG = "feedbackfragmentet";

    private static final int NUM_PAGES = 5; //Fast indtil videre
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overholdt__giv__feedback);

        String datoString = getIntent().getStringExtra("MØDEID");
        String stedString = getIntent().getStringExtra("MØDEIDdel");

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
        tekstNummer.setText(pos + 1 + " af " + NUM_PAGES);

        if (pos <= 0){
            knapTilbage.setVisibility(View.INVISIBLE);
        }
        else if (pos >= NUM_PAGES - 1){
            knapVidere.setText("Afslut");
            knapVidere.setBackgroundResource(R.drawable.knap_groen);
        }
        else {
            knapTilbage.setVisibility(View.VISIBLE);
            knapVidere.setText("Videre");
            knapVidere.setBackground(null);
        }
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void onFragmentInteraction(int smiley, String tekstUddybning) {
        Log.d("FRAGMENT", "Int: " + smiley + " and " + tekstUddybning);
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
                //TODO: afslut feedback
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
            return Feedback_frg.newInstance("Spørgsmål " + position + 1);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


}
