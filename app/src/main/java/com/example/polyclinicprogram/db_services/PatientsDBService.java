package com.example.polyclinicprogram.db_services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.polyclinicprogram.db_helpers.PatientsDBHelper;
import com.example.polyclinicprogram.models.Patient;

import java.util.ArrayList;

public class PatientsDBService {

    PatientsDBHelper patientsDBHelper;


    public PatientsDBService(Context context) {
        this.patientsDBHelper = new PatientsDBHelper(context);
    }

    public void save(ArrayList<Patient> patientArrayList){

        SQLiteDatabase db = patientsDBHelper.getReadableDatabase();
        db.delete(PatientsDBHelper.TABLE_PATIENTS, null, null);


        for (Patient patient : patientArrayList){
            ContentValues contentValues = new ContentValues();

            contentValues.put(PatientsDBHelper.KEY_SURNAME, patient.surname);
            contentValues.put(PatientsDBHelper.KEY_NAME, patient.name);
            contentValues.put(PatientsDBHelper.KEY_PATRONYMIC, patient.patronymic);
            contentValues.put(PatientsDBHelper.KEY_PHONE_NUMBER, patient.phone_number);
            contentValues.put(PatientsDBHelper.KEY_DATE_OF_BIRTH, patient.date_of_birth);

            db.insert(PatientsDBHelper.TABLE_PATIENTS, null, contentValues);
        }
    }

    public void read(ArrayList<Patient> patientArrayList){
        SQLiteDatabase db = patientsDBHelper.getReadableDatabase();
        Cursor cursor = db.query(PatientsDBHelper.TABLE_PATIENTS, null, null, null, null, null, null);

        patientArrayList.clear();
        if(cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(PatientsDBHelper.KEY_ID);
            int surnameIndex = cursor.getColumnIndex(PatientsDBHelper.KEY_SURNAME);
            int nameIndex = cursor.getColumnIndex(PatientsDBHelper.KEY_NAME);
            int patronymicIndex = cursor.getColumnIndex(PatientsDBHelper.KEY_PATRONYMIC);
            int phoneNumberIndex = cursor.getColumnIndex(PatientsDBHelper.KEY_PHONE_NUMBER);
            int dateOfBirthIndex = cursor.getColumnIndex(PatientsDBHelper.KEY_DATE_OF_BIRTH);

            do {
                patientArrayList.add(new Patient(
                                cursor.getInt(idIndex),
                                cursor.getString(surnameIndex),
                                cursor.getString(nameIndex),
                                cursor.getString(patronymicIndex),
                                cursor.getString(phoneNumberIndex),
                                cursor.getString(dateOfBirthIndex)
                        )
                );
            } while (cursor.moveToNext());
        }

        cursor.close();
    }
}
