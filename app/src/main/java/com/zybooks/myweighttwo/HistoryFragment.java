package com.zybooks.myweighttwo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {

    ArrayList<String> weightList = new ArrayList<>();
    GridView gridView;

    public HistoryFragment() {
        // Required empty public constructor
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper mydb = new DatabaseHelper(getActivity());
        try{
            Cursor weightData = mydb.getCurrentWeight();
            weightList.add("Date");
            weightList.add("Weight");
            weightList.add("Delete");
            while (weightData.moveToNext()){
                weightList.add(weightData.getString(2));
                weightList.add(weightData.getString(1));
                weightList.add("x");
            }
        }catch (Exception e){
            Log.d("Error, ","History Fragment onCreate: " + e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        gridView = view.findViewById(R.id.gridView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, weightList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseHelper mydb = new DatabaseHelper(getActivity());
                String gridItem = gridView.getItemAtPosition(position).toString();
                if(gridItem.equals("x")){
                    try {
                        mydb.deleteRow(gridView.getItemAtPosition(position-1).toString());
                        adapter.clear();
                        Cursor weightData = mydb.getCurrentWeight();
                        weightList.add("Date");
                        weightList.add("Weight");
                        weightList.add("Delete");
                        while (weightData.moveToNext()){
                            weightList.add(weightData.getString(2));
                            weightList.add(weightData.getString(1));
                            weightList.add("x");
                        }
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(HistoryFragment.this).attach(HistoryFragment.this).commit();
                    }catch (Exception e){
                        Log.d("Error, ","deleting item from history grid fragment, " + e.getMessage());
                    }
                }
            }
        });
        return view;
    }
}