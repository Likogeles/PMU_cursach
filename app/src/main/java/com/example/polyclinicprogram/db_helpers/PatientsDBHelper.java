package com.example.polyclinicprogram.db_helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PatientsDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "patientsDB";
    public static final String TABLE_PATIENTS = "patients";

    public static final String KEY_ID = "_id";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_NAME = "name";
    public static final String KEY_PATRONYMIC = "patronymic";
    public static final String KEY_PHONE_NUMBER = "phone_number";
    public static final String KEY_DATE_OF_BIRTH = "date_of_birth";

    public PatientsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_PATIENTS + "(" +
                KEY_ID + " integer primary key,"+
                KEY_SURNAME + " text," +
                KEY_NAME + " text," +
                KEY_PATRONYMIC + " text," +
                KEY_PHONE_NUMBER + " text," +
                KEY_DATE_OF_BIRTH + " date" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TABLE_PATIENTS);
        onCreate(db);
    }
}
