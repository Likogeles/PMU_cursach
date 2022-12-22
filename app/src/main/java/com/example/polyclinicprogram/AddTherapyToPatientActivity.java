package com.example.polyclinicprogram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.polyclinicprogram.db_services.PatientsTherapyDBService;
import com.example.polyclinicprogram.db_services.TherapiesDBService;
import com.example.polyclinicprogram.models.Patient;
import com.example.polyclinicprogram.models.Therapy;

import java.util.ArrayList;

public class AddTherapyToPatientActivity extends AppCompatActivity {


    ArrayList<Therapy> therapyArrayList = new ArrayList<>();
    ArrayAdapter<Therapy> adapter;
    ListView listView;
    TherapiesDBService therapiesDBService;
    PatientsTherapyDBService patientsTherapyDBService;
    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_therapy_to_patient);

        Button cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(view -> finish());

        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this::saveBtnClick);

        therapiesDBService = new TherapiesDBService(this);
        therapiesDBService.readTherapies(therapyArrayList);
        adapter = new ArrayAdapter<>(this, R.layout.adapter_layout, therapyArrayList);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        patientsTherapyDBService = new PatientsTherapyDBService(this);
        Intent bundleIntent = getIntent();
        ArrayList<Patient> arr = (ArrayList<Patient>) bundleIntent.getSerializableExtra("patient");
        if (arr != null){
            patient = arr.get(0);

            ArrayList<Therapy> oldTherapies = patientsTherapyDBService.therapiesByPatientId(patient.id);
            for (Therapy therapy : oldTherapies) {
                listView.setItemChecked(therapy.id - 1, true);
            }
        }
    }

    private void saveBtnClick(View view) {
        SparseBooleanArray sbArray = listView.getCheckedItemPositions();

        ArrayList<Integer> toAddList = new ArrayList<>();

        for (int i = 0; i < sbArray.size(); i++) {
            int key = sbArray.keyAt(i);
            if (sbArray.get(key)){
                toAddList.add(key);
            }
        }

        patientsTherapyDBService.removeTherapiesFromPatient(patient);

        for (int i = 0; i < toAddList.size(); i++) {
            patientsTherapyDBService.addTherapiesToPatient(therapyArrayList.get(toAddList.get(i)), patient);
        }
        finish();
    }
}