package com.zybooks.myweighttwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button createUser;
    EditText username;
    EditText password;
    Button loginButton;
    DatabaseHelper mydb = new DatabaseHelper(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        createUser = findViewById(R.id.createUserButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().length() > 10 ||
                        password.getText().toString().length() >8){
                    Toast.makeText(getBaseContext(),"Incorrect User Name or Password", Toast.LENGTH_LONG).show();
                }else {
                    try{
                        Cursor passwordData = mydb.getPasswordData();
                        Cursor userNameData = mydb.getData();
                        ArrayList<String> listData2 = new ArrayList<>();
                        while(passwordData.moveToNext()){
                            listData2.add(passwordData.getString(1));
                        }
                        while (userNameData.moveToNext()){
                            listData2.add(userNameData.getString(1).toLowerCase());
                        }
                        if(password.getText().toString().equals(listData2.get(0)) &&
                                username.getText().toString().toLowerCase().equals(listData2.get(1))){
                            openAppActivity();
                        }else{
                            Toast.makeText(getBaseContext(),"Incorrect User Name or Password", Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){
                        Log.d("Error with loginButton", e.getMessage());
                    }
                }
            }
        });

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateUserActivity();
            }
        });
    }
    public void openAppActivity(){
        Intent intent = new Intent(this, AppActivity.class);
        startActivity(intent);
    }
    public void openCreateUserActivity(){
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }


}