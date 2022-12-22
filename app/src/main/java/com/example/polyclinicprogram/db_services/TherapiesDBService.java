package com.example.polyclinicprogram.db_services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.polyclinicprogram.db_helpers.DBHelper;
import com.example.polyclinicprogram.models.Therapy;

import java.util.ArrayList;

public class TherapiesDBService {

    DBHelper DBHelper;

    public TherapiesDBService(Context context) {
        this.DBHelper = new DBHelper(context);
    }

    public void saveTherapies(ArrayList<Therapy> therapyArrayList){

        SQLiteDatabase db = DBHelper.getReadableDatabase();
        db.delete(DBHelper.TABLE_THERAPIES, null, null);

        for (Therapy therapy : therapyArrayList){
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBHelper.THERAPIES_KEY_NAME, therapy.name);

            if(therapy.important)
                contentValues.put(DBHelper.THERAPIES_KEY_IMPORTANT, 1);
            else
                contentValues.put(DBHelper.THERAPIES_KEY_IMPORTANT, 0);

            contentValues.put(DBHelper.THERAPIES_KEY_SCOPE_AREA, therapy.scope_area);

            db.insert(DBHelper.TABLE_THERAPIES, null, contentValues);
        }
    }

    public void readTherapies(ArrayList<Therapy> therapyArrayList){
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_THERAPIES, null, null, null, null, null, null);

        therapyArrayList.clear();
        if(cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.THERAPIES_KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.THERAPIES_KEY_NAME);
            int importantIndex = cursor.getColumnIndex(DBHelper.THERAPIES_KEY_IMPORTANT);
            int scopeAreaIndex = cursor.getColumnIndex(DBHelper.THERAPIES_KEY_SCOPE_AREA);

            do {
                boolean important = false;
                if (cursor.getInt(importantIndex) == 1){
                    important = true;
                }
                therapyArrayList.add(new Therapy(
                                cursor.getInt(idIndex),
                                cursor.getString(nameIndex),
                                important,
                                cursor.getString(scopeAreaIndex)
                        )
                );
            } while (cursor.moveToNext());
        }

        cursor.close();
    }
}
