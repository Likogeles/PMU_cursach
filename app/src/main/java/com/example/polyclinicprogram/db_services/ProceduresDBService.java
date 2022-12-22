package com.example.polyclinicprogram.db_services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.polyclinicprogram.db_helpers.DBHelper;
import com.example.polyclinicprogram.models.Patient;
import com.example.polyclinicprogram.models.Procedure;
import com.example.polyclinicprogram.models.Therapy;

import java.util.ArrayList;

public class ProceduresDBService {
    DBHelper DBHelper;

    public ProceduresDBService(Context context) {
        this.DBHelper = new DBHelper(context);
    }

    public void saveProcedures(ArrayList<Procedure> procedureArrayList){

        SQLiteDatabase db = DBHelper.getReadableDatabase();
        db.delete(DBHelper.TABLE_PROCEDURES, null, null);


        for (Procedure procedure : procedureArrayList){
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBHelper.PROCEDURES_KEY_NAME, procedure.name);
            contentValues.put(DBHelper.PROCEDURES_KEY_DESCRIPTION, procedure.description);
            contentValues.put(DBHelper.PROCEDURES_KEY_PRICE, procedure.price);

            db.insert(DBHelper.TABLE_PROCEDURES, null, contentValues);
        }
    }

    public void readProcedures(ArrayList<Procedure> procedureArrayList){
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_PROCEDURES, null, null, null, null, null, null);

        procedureArrayList.clear();

        if(cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.PROCEDURES_KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.PROCEDURES_KEY_NAME);
            int descriptionIndex = cursor.getColumnIndex(DBHelper.PROCEDURES_KEY_DESCRIPTION);
            int priceIndex = cursor.getColumnIndex(DBHelper.PROCEDURES_KEY_PRICE);

            do {
                procedureArrayList.add(new Procedure(
                        cursor.getInt(idIndex),
                        cursor.getString(nameIndex),
                        cursor.getString(descriptionIndex),
                        Integer.parseInt(cursor.getString(priceIndex))
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
    }
}
