package com.example.polyclinicprogram.db_services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.polyclinicprogram.db_helpers.DBHelper;
import com.example.polyclinicprogram.models.Patient;
import com.example.polyclinicprogram.models.Therapy;

import java.util.ArrayList;

public class PatientsTherapyDBService {

    DBHelper DBHelper;
    TherapiesDBService therapiesDBService;

    public PatientsTherapyDBService(Context context) {
        this.DBHelper = new DBHelper(context);
        therapiesDBService = new TherapiesDBService(context);
    }

    public ArrayList<Therapy> therapiesByPatientId(Integer patient_id){
        ArrayList<Therapy> new_therapies = new ArrayList<>();
        ArrayList<Therapy> therapies = new ArrayList<>();
        therapiesDBService.readTherapies(therapies);

        SQLiteDatabase db = DBHelper.getReadableDatabase();
        String filter = DBHelper.PATIENTS_THERAPIES_KEY_PATIENT_ID + " = " + patient_id;
        Cursor cursor = db.query(DBHelper.TABLE_PATIENTS_THERAPIES, null, filter, null, null, null, null);

        if(cursor.moveToFirst()) {
            int therapyIdIndex = cursor.getColumnIndex(DBHelper.PATIENTS_THERAPIES_KEY_THERAPY_ID);
            do {
                new_therapies.add(therapies.get(cursor.getInt(therapyIdIndex)-1));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return new_therapies;
    }

    public void addTherapiesToPatient(Therapy therapy, Patient patient){
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        db.execSQL("INSERT or REPLACE INTO " + DBHelper.TABLE_PATIENTS_THERAPIES + " VALUES (" + patient.id + ", " + therapy.id + ")");
    }

    public void removeTherapiesFromPatient(Patient patient){
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        db.execSQL("DELETE FROM " + DBHelper.TABLE_PATIENTS_THERAPIES + " WHERE " + patient.id + " = " + DBHelper.PATIENTS_THERAPIES_KEY_PATIENT_ID + "");
    }
}
