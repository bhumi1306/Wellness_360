package com.example.wellness360;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import javax.xml.validation.TypeInfoProvider;
public class DBHandler extends SQLiteOpenHelper{

    private static final String DB_NAME = "SignIn.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "signin";
    private static final String TABLE_NAME1 = "details";
    private static final String id_col = "Id";
    private static final String Email_col = "Email";
    private static final String passwd_col = "Password";
    private static final String Name_col = "Name";
    private static final String Age_col = "Age";
    private static final String Gender_col = "Gender";
    private static final String Height_col = "Height";
    private static final String Weight_col = "Weight";
    private static final String Country_col = "Country";
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE " + TABLE_NAME + " ("+ id_col +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Email_col + " TEXT UNIQUE NOT NULL, "+ passwd_col +" TEXT)";
        String query2 = "CREATE TABLE " + TABLE_NAME1 + " ("+ id_col +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ Name_col +" TEXT, "+ Age_col+ " TEXT, "+ Gender_col +" TEXT, "+ Height_col +" TEXT, "+ Weight_col +" TEXT,"+ Country_col +" TEXT)";
        db.execSQL(query1);
        db.execSQL(query2);
    }

    public void addInfo(String email, String passwd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Email_col, email);
        values.put(passwd_col, passwd);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public void addInfo1(String name, String age,String gender,String height,String weight, String country){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Name_col,name);
        values.put(Age_col, age);
        values.put(Gender_col, gender);
        values.put(Height_col, height);
        values.put(Weight_col, weight);
        values.put(Country_col, country);
        db.insert(TABLE_NAME1, null, values);
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
