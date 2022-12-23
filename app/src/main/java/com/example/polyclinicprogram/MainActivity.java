package com.example.polyclinicprogram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.Toast;

import com.example.polyclinicprogram.db_services.PatientsDBService;
import com.example.polyclinicprogram.models.Patient;
import com.example.polyclinicprogram.models.User;
import com.example.polyclinicprogram.reports.PatientsReport;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    User user;

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

        Button proceduresBtn = findViewById(R.id.proceduresBtn);
        proceduresBtn.setOnClickListener(view -> {
            Intent proceduresListActivity = new Intent(this, ProceduresListActivity.class);
            startActivity(proceduresListActivity);
        });

        Button reportBtn = findViewById(R.id.reportBtn);
        reportBtn.setOnClickListener(view -> {

            ArrayList<Patient> patientArrayList = new ArrayList<>();
            PatientsDBService patientsDBService = new PatientsDBService(this);
            patientsDBService.readPatients(patientArrayList);
            try {
                PatientsReport patientsReport = new PatientsReport("PolyclinicBeIll");
                patientsReport.createPDF(this, patientArrayList);
            }catch (Exception ex){
                System.out.println(ex);
            }
        });

        Intent bundleIntent = getIntent();
        ArrayList<User> arr = (ArrayList<User>) bundleIntent.getSerializableExtra("user");
        if (arr != null){
            user = arr.get(0);
            if (!user.admin){
                therapiesBtn.setEnabled(false);
                proceduresBtn.setEnabled(false);
            }
        }
    }


}
