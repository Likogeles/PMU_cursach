package com.example.polyclinicprogram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.polyclinicprogram.db_services.PatientsDBService;
import com.example.polyclinicprogram.db_services.PatientsTherapyDBService;
import com.example.polyclinicprogram.db_services.TherapiesDBService;
import com.example.polyclinicprogram.models.Patient;
import com.example.polyclinicprogram.models.Therapy;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    PatientsTherapyDBService patientsTherapyDBService;
    PatientsDBService patientsDBService;
    TherapiesDBService therapiesDBService;

    ArrayList<Patient> patients = new ArrayList<>();
    ArrayList<Therapy> therapies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button patientsBtn = findViewById(R.id.patientsBtn);
        patientsBtn.setOnClickListener(view -> {
            Intent patientListActivity = new Intent(this, PatientListActivity.class);
            startActivity(patientListActivity);
        });

        Button therapiesBtn = findViewById(R.id.therapiesBtn);
        therapiesBtn.setOnClickListener(view -> {
            Intent therapiesListActivity = new Intent(this, TherapiesListActivity.class);
            startActivity(therapiesListActivity);
        });

        patientsTherapyDBService = new PatientsTherapyDBService(this);
        patientsDBService = new PatientsDBService(this);
        therapiesDBService = new TherapiesDBService(this);

        patientsDBService.readPatients(patients);
        therapiesDBService.readTherapies(therapies);

//        Button addBtn = findViewById(R.id.addBtn);
//        addBtn.setOnClickListener(view -> {
//            patientsTherapyDBService.addTherapiesToPatient(therapies.get(0), patients.get(0));
//            patientsTherapyDBService.addTherapiesToPatient(therapies.get(0), patients.get(1));
//            patientsTherapyDBService.addTherapiesToPatient(therapies.get(0), patients.get(2));
//            patientsTherapyDBService.addTherapiesToPatient(therapies.get(1), patients.get(1));
//            patientsTherapyDBService.addTherapiesToPatient(therapies.get(1), patients.get(2));
//            patientsTherapyDBService.addTherapiesToPatient(therapies.get(2), patients.get(2));
//            patientsTherapyDBService.addTherapiesToPatient(therapies.get(2), patients.get(2));
//            patientsTherapyDBService.addTherapiesToPatient(therapies.get(2), patients.get(1));
//        });
//
//        Button readBtn = findViewById(R.id.readBtn);
//        readBtn.setOnClickListener(view -> {
//            patientsTherapyDBService.therapiesByPatientId(patients.get(0).id);
//            patientsTherapyDBService.therapiesByPatientId(patients.get(1).id);
//            patientsTherapyDBService.therapiesByPatientId(patients.get(2).id);
//            patientsTherapyDBService.therapiesByPatientId(patients.get(3).id);
//        });
    }

    @Override
    protected void onResume() {

        patientsDBService.readPatients(patients);
        therapiesDBService.readTherapies(therapies);
        super.onResume();
    }
}
