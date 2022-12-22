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

    public static final String TABLE_PATIENTS_THERAPIES = "patients_therapies";
    public static final String PATIENTS_THERAPIES_KEY_ID = "_id";
    public static final String PATIENTS_THERAPIES_KEY_PATIENT_ID = "patient_id";
    public static final String PATIENTS_THERAPIES_KEY_THERAPY_ID = "therapy_id";

    public static final String TABLE_USERS = "users";
    public static final String USERS_KEY_ID = "_id";
    public static final String USERS_KEY_LOGIN = "login";
    public static final String USERS_KEY_PASSWORD = "password";
    public static final String USERS_KEY_EMAIL = "email";
    public static final String USERS_KEY_ADMIN = "admin";

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

        db.execSQL("create table " + TABLE_PATIENTS_THERAPIES + "(" +
                PATIENTS_THERAPIES_KEY_PATIENT_ID + " integer references " + TABLE_PATIENTS +"(" + PATIENTS_KEY_ID + ")," +
                PATIENTS_THERAPIES_KEY_THERAPY_ID + " integer references " + TABLE_THERAPIES +"(" + THERAPIES_KEY_ID + ")," +
                " constraint " + PATIENTS_THERAPIES_KEY_ID + " primary key (" + PATIENTS_THERAPIES_KEY_PATIENT_ID + "," + PATIENTS_THERAPIES_KEY_THERAPY_ID + "))");

        db.execSQL("create table " + TABLE_USERS + "(" +
                USERS_KEY_ID + " integer primary key,"+
                USERS_KEY_LOGIN + " text," +
                USERS_KEY_PASSWORD + " text," +
                USERS_KEY_EMAIL + " text," +
                USERS_KEY_ADMIN + " integer" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TABLE_PATIENTS);
        db.execSQL("drop table if exists " + TABLE_THERAPIES);
        db.execSQL("drop table if exists " + TABLE_PATIENTS_THERAPIES);
        db.execSQL("drop table if exists " + TABLE_USERS);
        onCreate(db);
    }
}
