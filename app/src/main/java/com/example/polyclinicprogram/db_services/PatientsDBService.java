package com.example.polyclinicprogram.db_services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.polyclinicprogram.db_helpers.DBHelper;
import com.example.polyclinicprogram.models.Patient;
import com.example.polyclinicprogram.models.Therapy;

import java.util.ArrayList;

public class PatientsDBService {

    DBHelper DBHelper;
    PatientsTherapyDBService patientsTherapyDBService;

    public PatientsDBService(Context context) {
        this.DBHelper = new DBHelper(context);
        patientsTherapyDBService = new PatientsTherapyDBService(context);
    }

    public void savePatients(ArrayList<Patient> patientArrayList){

        SQLiteDatabase db = DBHelper.getReadableDatabase();
        db.delete(DBHelper.TABLE_PATIENTS, null, null);


        for (Patient patient : patientArrayList){
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBHelper.PATIENTS_KEY_SURNAME, patient.surname);
            contentValues.put(DBHelper.PATIENTS_KEY_NAME, patient.name);
            contentValues.put(DBHelper.PATIENTS_KEY_PATRONYMIC, patient.patronymic);
            contentValues.put(DBHelper.PATIENTS_KEY_PHONE_NUMBER, patient.phone_number);
            contentValues.put(DBHelper.PATIENTS_KEY_DATE_OF_BIRTH, patient.date_of_birth);

            db.insert(DBHelper.TABLE_PATIENTS, null, contentValues);
        }
    }

    public void readPatients(ArrayList<Patient> patientArrayList){
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_PATIENTS, null, null, null, null, null, null);

        patientArrayList.clear();

        if(cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.PATIENTS_KEY_ID);
            int surnameIndex = cursor.getColumnIndex(DBHelper.PATIENTS_KEY_SURNAME);
            int nameIndex = cursor.getColumnIndex(DBHelper.PATIENTS_KEY_NAME);
            int patronymicIndex = cursor.getColumnIndex(DBHelper.PATIENTS_KEY_PATRONYMIC);
            int phoneNumberIndex = cursor.getColumnIndex(DBHelper.PATIENTS_KEY_PHONE_NUMBER);
            int dateOfBirthIndex = cursor.getColumnIndex(DBHelper.PATIENTS_KEY_DATE_OF_BIRTH);

            do {
                ArrayList<Therapy> therapies = patientsTherapyDBService.therapiesByPatientId(cursor.getInt(idIndex));
                patientArrayList.add(new Patient(
                        cursor.getInt(idIndex),
                        cursor.getString(surnameIndex),
                        cursor.getString(nameIndex),
                        cursor.getString(patronymicIndex),
                        cursor.getString(phoneNumberIndex),
                        cursor.getString(dateOfBirthIndex),
                        therapies
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
    }
}
