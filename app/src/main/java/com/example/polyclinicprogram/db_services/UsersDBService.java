package com.example.polyclinicprogram.db_services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.polyclinicprogram.db_helpers.DBHelper;
import com.example.polyclinicprogram.models.User;

import java.util.ArrayList;
import java.util.Objects;

public class UsersDBService {
    DBHelper DBHelper;

    public UsersDBService(Context context) {
        this.DBHelper = new DBHelper(context);
    }

    public void addUser(User user){

        int admin = 0;
        if (user.admin){
            admin = 1;
        }

        SQLiteDatabase db = DBHelper.getReadableDatabase();
        db.execSQL("INSERT INTO " + DBHelper.TABLE_USERS + " VALUES (\"" + user.id + "\", \""+ user.login + "\", \"" + user.password + "\", \"" + user.email + "\", \"" + admin + "\")");
    }

    public void getUsers (ArrayList<User> users){

        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);

        if(cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.THERAPIES_KEY_ID);
            int loginIndex = cursor.getColumnIndex(DBHelper.USERS_KEY_LOGIN);
            int passwordIndex = cursor.getColumnIndex(DBHelper.USERS_KEY_PASSWORD);
            int emailIndex = cursor.getColumnIndex(DBHelper.USERS_KEY_EMAIL);
            int adminIndex = cursor.getColumnIndex(DBHelper.USERS_KEY_ADMIN);

            do {
                boolean admin = false;
                if (cursor.getInt(adminIndex) == 1){
                    admin = true;
                }
                users.add(new User(
                                cursor.getInt(idIndex),
                                cursor.getString(loginIndex),
                                cursor.getString(passwordIndex),
                                cursor.getString(emailIndex),
                                admin
                        )
                );
            } while (cursor.moveToNext());
        }

        cursor.close();
    }

    public int getLastID (){
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);
        int ind = 0;
        if(cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.THERAPIES_KEY_ID);
            do {
                ind = cursor.getInt(idIndex);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return ind+1;
    }

    public boolean haveUser (User checkUser){

        ArrayList<User> users = new ArrayList<>();
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);

        if(cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.THERAPIES_KEY_ID);
            int loginIndex = cursor.getColumnIndex(DBHelper.USERS_KEY_LOGIN);
            int passwordIndex = cursor.getColumnIndex(DBHelper.USERS_KEY_PASSWORD);
            int emailIndex = cursor.getColumnIndex(DBHelper.USERS_KEY_EMAIL);
            int adminIndex = cursor.getColumnIndex(DBHelper.USERS_KEY_ADMIN);

            do {
                boolean admin = false;
                if (cursor.getInt(adminIndex) == 1){
                    admin = true;
                }
                users.add(new User(
                                cursor.getInt(idIndex),
                                cursor.getString(loginIndex),
                                cursor.getString(passwordIndex),
                                cursor.getString(emailIndex),
                                admin
                        )
                );
            } while (cursor.moveToNext());
        }

        cursor.close();

        for(User user : users){
            if (Objects.equals(user.login, checkUser.login)){
                return true;
            }
        }

        return false;
    }

}
