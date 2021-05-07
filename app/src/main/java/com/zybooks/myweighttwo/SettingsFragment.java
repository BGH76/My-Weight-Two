package com.zybooks.myweighttwo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    //private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    Button switch1;
    TextView settingsFragmentName;
    TextView settingsFragmentAge;
    TextView settingsFragmentHeight;
    TextView settingsFragmentCurrentWeight;
    TextView settingsFragmentGoalWeight;

    public SettingsFragment() {
        // Required empty public constructor
    }


    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        DatabaseHelper mydb = new DatabaseHelper(getActivity());
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        switch1 = view.findViewById(R.id.switch1);
        settingsFragmentName = view.findViewById(R.id.settingsFragmeentName);
        settingsFragmentAge = view.findViewById(R.id.settingsFragmentAge);
        settingsFragmentHeight = view.findViewById(R.id.settingsFragmentHeight);
        settingsFragmentCurrentWeight = view.findViewById(R.id.settingsFragmentCurrentWeight);
        settingsFragmentGoalWeight = view.findViewById(R.id.settingsFragmentGoalWeight);


        switch1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("You need permission to send messages");
                builder.setTitle("Alert!");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        Cursor data = mydb.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(1)); //name, array list 0
            listData.add(data.getString(2)); // age, array list 1
            listData.add(data.getString(3)); // height, array list 2
            listData.add(data.getString(4)); // current weight, array list 3
            listData.add(data.getString(5)); // goal weight, array list 4

        }
        settingsFragmentName.setText("Name: " + listData.get(0));
        settingsFragmentAge.setText("Age: " + listData.get(1));
        settingsFragmentHeight.setText("Height in inches : " + listData.get(2));
        settingsFragmentCurrentWeight.setText("Starting Weight: " + listData.get(3));
        settingsFragmentGoalWeight.setText("Goal Weight: " + listData.get(4));


        //return inflater.inflate(R.layout.fragment_settings, container, false);
        return view;
    }

}