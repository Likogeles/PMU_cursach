package com.example.polyclinicprogram.db_services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.polyclinicprogram.db_helpers.DBHelper;
import com.example.polyclinicprogram.models.Patient;
import com.example.polyclinicprogram.models.Procedure;

import java.util.ArrayList;

public class PatientsProceduresDBService {
    com.example.polyclinicprogram.db_helpers.DBHelper DBHelper;
    ProceduresDBService proceduresDBService;

    public PatientsProceduresDBService(Context context) {
        this.DBHelper = new DBHelper(context);
        proceduresDBService = new ProceduresDBService(context);
    }

    public ArrayList<Procedure> proceduresByPatientId(Integer patient_id){
        ArrayList<Procedure> new_procedures = new ArrayList<>();
        ArrayList<Procedure> procedures = new ArrayList<>();
        proceduresDBService.readProcedures(procedures);

        SQLiteDatabase db = DBHelper.getReadableDatabase();
        String filter = DBHelper.PATIENTS_PROCEDURES_KEY_PATIENT_ID + " = " + patient_id;
        Cursor cursor = db.query(DBHelper.TABLE_PATIENTS_PROCEDURES, null, filter, null, null, null, null);

        if(cursor.moveToFirst()) {
            int procedureIdIndex = cursor.getColumnIndex(DBHelper.PATIENTS_PROCEDURES_KEY_PROCEDURE_ID);
            do {
                new_procedures.add(procedures.get(cursor.getInt(procedureIdIndex)-1));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return new_procedures;
    }

    public void addProceduresToPatient(Procedure procedure, Patient patient){
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        db.execSQL("INSERT or REPLACE INTO " + DBHelper.TABLE_PATIENTS_PROCEDURES + " VALUES (" + patient.id + ", " + procedure.id + ")");
    }

    public void removeProceduresFromPatient(Patient patient){
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        db.execSQL("DELETE FROM " + DBHelper.TABLE_PATIENTS_PROCEDURES + " WHERE " + patient.id + " = " + DBHelper.PATIENTS_PROCEDURES_KEY_PATIENT_ID + "");
    }
}
