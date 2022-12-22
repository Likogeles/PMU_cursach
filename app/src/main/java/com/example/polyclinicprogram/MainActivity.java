package com.example.polyclinicprogram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    Executor executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        executor.execute(() -> {
            handler.post(() -> {
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
            });
        });
    }
}
