package com.example.admin.feedback_app.fragmenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;


public class IkkeAfholdt_frg extends Fragment {
    private static final String TAG = "ikkeAfholdt";
    private ListView listView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ikke_afholdt, container, false);
        listView = v.findViewById(R.id.listview1);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        indlæsListView();
    }

    private void indlæsListView(){
        //Få mødenavne vist i et listview
        ArrayAdapter<String> listviewAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                PersonData.getInstance().getIkkeAfholdteMøderAsStringArray());
        listView.setAdapter(listviewAdapter);
    }

}
