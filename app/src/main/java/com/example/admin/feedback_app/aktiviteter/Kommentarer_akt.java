package com.example.admin.feedback_app.aktiviteter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.example.admin.feedback_app.FeedbackSpørgsmål;
import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.adaptere.SpørgsmålAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Kommentarer_akt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kommentarer);

        ExpandableListView listView = findViewById(R.id.kom_listView);

        List<String> grouper = Arrays.asList(FeedbackSpørgsmål.SPØRGSMÅL);

        SpørgsmålAdapter adapter = new SpørgsmålAdapter(this, grouper, hentDummyData(grouper));

        listView.setAdapter(adapter);


    }

    private HashMap<String, List<String>> hentDummyData(List<String> grouper){
        HashMap<String, List<String>> map = new HashMap<>();

        for (String s : grouper){
            List<String> undergrouper = new ArrayList<>();
            int antal = new Random().nextInt(6);

            for(int i = 0; i <= antal; i++){
                undergrouper.add("Svar " + (i+1));
            }

            map.put(s,undergrouper);
        }

        return map;
    }
}
