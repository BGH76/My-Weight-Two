package com.zybooks.myweighttwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateUserActivity extends AppCompatActivity {

    EditText createUserName;
    EditText createUserFirstPassword;
    EditText createUserSecondPassword;
    EditText createUserAge;
    EditText createUserHeight;
    EditText createUserCurrentWeight;
    EditText createUserGoalWeight;
    Button createUserSubmit;
    Button profileDeleteButton;
    DatabaseHelper mydb = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        createUserName = findViewById(R.id.createUserName);
        createUserFirstPassword = findViewById(R.id.createUserFirstPassword);
        createUserSecondPassword = findViewById(R.id.createUserSecondPassword);
        createUserAge = findViewById(R.id.createUserAge);
        createUserHeight = findViewById(R.id.createUserHeight);
        createUserCurrentWeight = findViewById(R.id.createUserCurrentWeight);
        createUserGoalWeight = findViewById(R.id.createUserGoalWeight);
        createUserSubmit = findViewById(R.id.createUserSubmit);
        profileDeleteButton = findViewById(R.id.testDeleteButton);

        createUserSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Cursor data = mydb.getData();
                    ArrayList<String> listData = new ArrayList<>();
                    while(data.moveToNext()){
                        listData.add(data.getString(1));
                    }
                    if(listData.size() > 0){
                        Toast.makeText(getBaseContext(), "User already exist, Only 1 user allowed", Toast.LENGTH_LONG).show();
                    }else{
                        if(createUserFirstPassword.getText().toString().equals(createUserSecondPassword.getText().toString())){
                            mydb.addPassword(createUserFirstPassword.getText().toString());
                            mydb.addData(createUserName.getText().toString(),
                                    createUserAge.getText().toString(),
                                    createUserHeight.getText().toString(),
                                    createUserCurrentWeight.getText().toString(),
                                    createUserGoalWeight.getText().toString());
                            mydb.addUserWeight(createUserCurrentWeight.getText().toString());
                            openAppActivity();
                        }else{
                            Toast.makeText(getBaseContext(), "Your passwords do not match", Toast.LENGTH_LONG).show();
                        }
                    }
                }catch (Exception e) {
                    Log.d("Error in create User Submit button ", e.getMessage());
                }
            }
        });

        profileDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mydb.deleteData();
                }catch (Exception e){
                    Log.d("Error with profile delete button ", e.getMessage());
                }
            }
        });
    }

    public void openAppActivity(){
        Intent intent = new Intent(this, AppActivity.class);
        startActivity(intent);
    }

}