package com.example.polyclinicprogram.db_helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "polyclinicDB";

    public static final String TABLE_PATIENTS = "patients";
    public static final String PATIENTS_KEY_ID = "_id";
    public static final String PATIENTS_KEY_SURNAME = "surname";
    public static final String PATIENTS_KEY_NAME = "name";
    public static final String PATIENTS_KEY_PATRONYMIC = "patronymic";
    public static final String PATIENTS_KEY_PHONE_NUMBER = "phone_number";
    public static final String PATIENTS_KEY_DATE_OF_BIRTH = "date_of_birth";

    public static final String TABLE_THERAPIES = "therapies";
    public static final String THERAPIES_KEY_ID = "_id";
    public static final String THERAPIES_KEY_NAME = "name";
    public static final String THERAPIES_KEY_IMPORTANT = "important";
    public static final String THERAPIES_KEY_SCOPE_AREA = "scope_area";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_PATIENTS + "(" +
                PATIENTS_KEY_ID + " integer primary key,"+
                PATIENTS_KEY_SURNAME + " text," +
                PATIENTS_KEY_NAME + " text," +
                PATIENTS_KEY_PATRONYMIC + " text," +
                PATIENTS_KEY_PHONE_NUMBER + " text," +
                PATIENTS_KEY_DATE_OF_BIRTH + " date" + ")");

        db.execSQL("create table " + TABLE_THERAPIES + "(" +
                THERAPIES_KEY_ID + " integer primary key,"+
                PATIENTS_KEY_SURNAME + " text," +
                THERAPIES_KEY_NAME + " text," +
                THERAPIES_KEY_IMPORTANT + " integer," +
                THERAPIES_KEY_SCOPE_AREA + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TABLE_PATIENTS);
        db.execSQL("drop table if exists " + TABLE_THERAPIES);
        onCreate(db);
    }
}
