package com.zybooks.myweighttwo;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    TextView currentWeightValueTextView;
    TextView goalWeightValueTextView;
    TextView weightLostValueView;
    EditText enterWeightEditText;
    Button enterWeightSubmitButton;
    Button enterWeightButton;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> weightList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            DatabaseHelper mydb = new DatabaseHelper(getActivity());
            // Returns goal weight from the database.
            Cursor data = mydb.getGoalWeight();
            while (data.moveToNext()){
                list.add(data.getString(0));
            }
            // Returns current weight from database.
            Cursor weightData = mydb.getCurrentWeight();
            while (weightData.moveToNext()){
                weightList.add(weightData.getString(1));
            }
        }catch(Exception e) {
            Log.d("Error ", "with Home Fragment onCreate" + e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        enterWeightSubmitButton = view.findViewById(R.id.enterWeightSubmitButton);
        enterWeightEditText = view.findViewById(R.id.enterWeightEditText);
        enterWeightButton = view.findViewById(R.id.enterWeightButton);
        currentWeightValueTextView = view.findViewById(R.id.currentWeightValueTextView);
        goalWeightValueTextView = view.findViewById(R.id.goalWeightValueTextView);
        weightLostValueView = view.findViewById(R.id.weightLostValueView);

        //Set text views
        goalWeightValueTextView.setText((list.get(0)));
        // Get the last element in ArrayList<String> weightList.
        currentWeightValueTextView.setText(weightList.get(weightList.size()-1));

        // Calculate weight lost. Starting weight from database - last element in weightList.
        int weightLostStart = Integer.parseInt(weightList.get(0));
        int weightLostCurrent = Integer.parseInt(weightList.get(weightList.size()-1));
        String finalLost = String.valueOf(weightLostStart - weightLostCurrent);
        weightLostValueView.setText(finalLost);

        enterWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterWeightButton.setEnabled(false);
                enterWeightButton.setVisibility(View.INVISIBLE);
                enterWeightEditText.setEnabled(true);
                enterWeightSubmitButton.setEnabled(true);
                enterWeightEditText.setVisibility(View.VISIBLE);
                enterWeightSubmitButton.setVisibility(View.VISIBLE);
            }
        });
        enterWeightSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DatabaseHelper mydb = new DatabaseHelper(getActivity());
                    mydb.addUserWeight(enterWeightEditText.getText().toString());
                    Cursor weightData = mydb.getCurrentWeight();
                    while (weightData.moveToNext()){
                        weightList.add(weightData.getString(1));
                    }
                    currentWeightValueTextView.setText(weightList.get(weightList.size()-1));
                    enterWeightButton.setEnabled(true);
                    enterWeightButton.setVisibility(View.VISIBLE);
                    enterWeightEditText.getText().clear();
                    enterWeightEditText.setEnabled(false);
                    enterWeightSubmitButton.setEnabled(false);
                    enterWeightEditText.setVisibility(View.INVISIBLE);
                    enterWeightSubmitButton.setVisibility(View.INVISIBLE);
                    //Reloads the fragment after a new weight is entered.
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(HomeFragment.this).attach(HomeFragment.this).commit();
                }catch (Exception e){
                    Log.d("Error ", "in Enter Weight Submit Button" + e.getMessage());
                }
            }
        });
        return view;
    }
}