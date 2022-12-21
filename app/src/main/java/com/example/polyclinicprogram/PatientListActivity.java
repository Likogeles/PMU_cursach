package com.example.polyclinicprogram;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.polyclinicprogram.Addlayouts.AddPatientActivity;
import com.example.polyclinicprogram.databasehelpers.PatientsDBHelper;
import com.example.polyclinicprogram.models.Patient;

import java.util.ArrayList;
import java.util.Date;

public class PatientListActivity extends AppCompatActivity {

    ArrayList<Patient> patientArrayList = new ArrayList<>();
    ArrayAdapter<Patient> adapter;
    ListView listView;

    PatientsDBHelper patientsDBHelper;

    // Получение результата из страницы добавления пациента.
    ActivityResultLauncher<Intent> addActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d(TAG, "onActivityResult: ");
                    if (result.getResultCode() == 78){
                        Intent intent = result.getData();
                        if(intent != null){
                            ArrayList<Patient> new_patient = (ArrayList<Patient>)intent.getSerializableExtra("patient");
                            patientArrayList.add(new_patient.get(0));
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

//        patientArrayList.add(new Patient("asd1", "asd", "asd1", "88005553535", "24.12.2022"));
//        patientArrayList.add(new Patient("asd2", "asd", "asd2", "88005553535", "24.12.2022"));
//        patientArrayList.add(new Patient("asd3", "asd", "asd3", "88005553535", "24.12.2022"));
//        patientArrayList.add(new Patient("asd4", "asd", "asd4", "88005553535", "24.12.2022"));
//        patientArrayList.add(new Patient("asd5", "asd", "asd5", "88005553535", "24.12.2022"));

        Button addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this::addBtnClick);

        Button removeBtn = findViewById(R.id.removeBtn);
        removeBtn.setOnClickListener(this::removeBtnClick);

        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this::saveBtnClick);

        Button readBtn = findViewById(R.id.readBtn);
        readBtn.setOnClickListener(this::readBtnClick);

        adapter = new ArrayAdapter<>(this, R.layout.adapter_layout, patientArrayList);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        patientsDBHelper = new PatientsDBHelper(this);
    }

    private void saveBtnClick(View view) {
        SQLiteDatabase db = patientsDBHelper.getReadableDatabase();
        db.delete(PatientsDBHelper.TABLE_PATIENTS, null, null);


        for (Patient patient : patientArrayList){
            ContentValues contentValues = new ContentValues();

            System.out.println(patient);
            contentValues.put(PatientsDBHelper.KEY_SURNAME, patient.surname);
            contentValues.put(PatientsDBHelper.KEY_NAME, patient.name);
            contentValues.put(PatientsDBHelper.KEY_PATRONYMIC, patient.patronymic);
            contentValues.put(PatientsDBHelper.KEY_PHONE_NUMBER, patient.phone_number);
            contentValues.put(PatientsDBHelper.KEY_DATE_OF_BIRTH, patient.date_of_birth);

            db.insert(PatientsDBHelper.TABLE_PATIENTS, null, contentValues);
        }
    }

    private void readBtnClick(View view) {
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
            adapter.notifyDataSetChanged();
        }

        cursor.close();
    }


    private void addBtnClick(View view) {
        Intent addPatientActivity = new Intent(this, AddPatientActivity.class);
        addActivityResultLauncher.launch(addPatientActivity);
    }

    private void removeBtnClick(View view) {
        SparseBooleanArray sbArray = listView.getCheckedItemPositions();

        ArrayList<Integer> toRemoveList = new ArrayList<>();

        for (int i = 0; i < sbArray.size(); i++) {
            int key = sbArray.keyAt(i);
            if (sbArray.get(key)){
                toRemoveList.add(key);
            }
        }
        for(int i = 0 ; i < adapter.getCount(); i++){
            listView.setItemChecked(i, false);
        }

        for (int i = toRemoveList.size() - 1; i > -1; i--) {
            Integer index = toRemoveList.get(i);
            if (index < patientArrayList.size()) {
                patientArrayList.remove(patientArrayList.get(index));
            }
        }
        adapter.notifyDataSetChanged();
    }
}
