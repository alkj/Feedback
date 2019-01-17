package com.example.admin.feedback_app.aktiviteter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.fragmenter.Feedback_frg;

public class Overholdt_Giv_Feedback_akt extends AppCompatActivity implements Feedback_frg.OnFragmentInteractionListener {

    private Fragment fragmentFeedback;

    private String TAG = "feedbackfragmentet";

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager viewPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
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
            return Feedback_frg.newInstance("spørgsmål " + position + " hvor gammel er du?");
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


}
