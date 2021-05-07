package com.zybooks.myweighttwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
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

                Cursor data2 = mydb.getPasswordData();
                ArrayList<String> listData2 = new ArrayList<>();
                while(data2.moveToNext()){
                    listData2.add(data2.getString(1));
                }
                if(password.getText().toString().equals(listData2.get(0))){
                    Log.d("Your login", "is Working");
                    openAppActivity();
                }else{
                    Toast.makeText(getBaseContext(),"Incorrect Password", Toast.LENGTH_LONG).show();
                    Log.d("Incorrect", "Password");

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