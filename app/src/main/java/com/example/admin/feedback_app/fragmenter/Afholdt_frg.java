package com.example.admin.feedback_app.fragmenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.adaptere.MødeAdapter;
import com.example.admin.feedback_app.aktiviteter.AfholdtMeode_akt;


public class Afholdt_frg extends Fragment implements AdapterView.OnItemClickListener {
    private static final String TAG = "Afholdt";
    private ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_moedeliste, container, false);
        listView = v.findViewById(R.id.moedeliste_listView);

        return v;
    }

    @Override
    public void onResume(){
        super.onResume();

        indlæsListView();
    }

    private void indlæsListView(){
        //Få mødenavne vist i et listview
        MødeAdapter listviewAdapter = new MødeAdapter(getActivity(),
                R.layout.list_row_item_moede,
                PersonData.getInstance().getAfholdteMøder());
        listView.setAdapter(listviewAdapter);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), AfholdtMeode_akt.class);
        intent.putExtra("INDEKS", position);

        startActivity(intent);
    }
}
