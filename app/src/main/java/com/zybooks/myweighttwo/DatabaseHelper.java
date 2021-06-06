package com.zybooks.myweighttwo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

import static java.util.Calendar.DATE;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "usersDataBase";

    private static final String TABLE_NAME = "user_table";
    private static final String USER_WEIGHT_TABLE = "user_weight";
    private static final String PASSWORD_TABLE = "password_table";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String AGE = "age";
    private static final String HEIGHT = "height";
    private static final String CURRENT_WEIGHT = "currentweight";
    private static final String GOAL_WEIGHT = "goalweight";


    private static final String WEIGHT = "weight";
    private static final String DATE_ENTERED = "date";

    private static final String PASSWD = "password";


    // Creates the database.
    public DatabaseHelper( Context context) {
        super(context, DATABASE_NAME, null, 4);
    }
    // Creates 3 tables. user_table, user_weight, password_table and sets up all the fields.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT," + AGE + " TEXT," + HEIGHT + " TEXT," + CURRENT_WEIGHT + " TEXT," + GOAL_WEIGHT + " TEXT)";

        String createTable2 = "CREATE TABLE " + USER_WEIGHT_TABLE + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WEIGHT + " TEXT," + DATE_ENTERED + " TEXT)";

        String createTable3 = "CREATE TABLE " + PASSWORD_TABLE + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PASSWD + " TEXT)";
        db.execSQL(createTable);
        db.execSQL(createTable2);
        db.execSQL(createTable3);

    }


    // Removes data base and creates a new one when version changes.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_WEIGHT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PASSWORD_TABLE);
        onCreate(db);
    }



    // Method to add data to the user_table.
    public boolean addData(String name, String age, String height, String currentweight, String goalweight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(AGE, age);
        contentValues.put(HEIGHT, height);
        contentValues.put(CURRENT_WEIGHT, currentweight);
        contentValues.put(GOAL_WEIGHT, goalweight);

        Log.d(TAG, "addData: Adding " + name + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    // addToPassword data table
    public boolean addPassword(String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PASSWD, password);

        Log.d(TAG, "addData: Adding " + password+ " to " + PASSWORD_TABLE);

        long result = db.insert(PASSWORD_TABLE, null, contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    // Method to add user weights.
    public boolean addUserWeight(String weight){
        LocalDate myDate = LocalDate.now();
        String formattedMyDate = myDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WEIGHT, weight);
        contentValues.put(DATE_ENTERED,String.valueOf(formattedMyDate));
        long result = db.insert(USER_WEIGHT_TABLE, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    // Method to get all data from user_table.
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }
    // Method to get data from password_table.
    public Cursor getPasswordData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + PASSWORD_TABLE;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    // Method to get goal weight from user_table.
    public Cursor getGoalWeight(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + GOAL_WEIGHT + " FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    // Method to get current weight from user_weight table.
    public Cursor getCurrentWeight(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + USER_WEIGHT_TABLE;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    //Method to delete a row from user_weight table.
    public void deleteRow(String item){
        SQLiteDatabase db = this.getWritableDatabase();                   // *******************************
        db.execSQL("DELETE FROM "+USER_WEIGHT_TABLE+ " WHERE ID="+item); // removed weight and changed to ID.
    }
    //Deletes data from all 3 database tables. Originally for testing but kept to delete profile.
    public void deleteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.execSQL("DELETE FROM " + PASSWORD_TABLE);
        db.execSQL("DELETE FROM " + USER_WEIGHT_TABLE);
    }
}
