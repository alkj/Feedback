package com.example.admin.feedback_app.fragmenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;
import com.example.admin.feedback_app.adaptere.MødeAdapter;


public class IkkeAfholdt_frg extends Fragment {
    private static final String TAG = "ikkeAfholdt";
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
    public void onStart() {
        super.onStart();

        indlæsListView();
    }

    private void indlæsListView(){
        //Få mødenavne vist i et listview
        MødeAdapter listviewAdapter = new MødeAdapter(getActivity(),
                R.layout.moede_liste_item,
                PersonData.getInstance().getIkkeAfholdteMøder());
        listView.setAdapter(listviewAdapter);
    }

}
