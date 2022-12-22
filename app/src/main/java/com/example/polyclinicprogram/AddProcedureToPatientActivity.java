package com.example.polyclinicprogram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.polyclinicprogram.db_services.PatientsProceduresDBService;
import com.example.polyclinicprogram.db_services.PatientsTherapyDBService;
import com.example.polyclinicprogram.db_services.ProceduresDBService;
import com.example.polyclinicprogram.db_services.TherapiesDBService;
import com.example.polyclinicprogram.models.Patient;
import com.example.polyclinicprogram.models.Procedure;
import com.example.polyclinicprogram.models.Therapy;

import java.util.ArrayList;

public class AddProcedureToPatientActivity extends AppCompatActivity {

    ArrayList<Procedure> procedureArrayList = new ArrayList<>();
    ArrayAdapter<Procedure> adapter;
    ListView listView;
    ProceduresDBService proceduresDBService;
    PatientsProceduresDBService patientsProceduresDBService;
    Patient patient;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_procedure_to_patient);
        
        Button cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(view -> finish());

        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this::saveBtnClick);

        proceduresDBService = new ProceduresDBService(this);
        proceduresDBService.readProcedures(procedureArrayList);
        adapter = new ArrayAdapter<>(this, R.layout.adapter_layout, procedureArrayList);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        patientsProceduresDBService = new PatientsProceduresDBService(this);
        Intent bundleIntent = getIntent();
        ArrayList<Patient> arr = (ArrayList<Patient>) bundleIntent.getSerializableExtra("patient");
        if (arr != null){
            patient = arr.get(0);
            ArrayList<Procedure> oldProcedures = patientsProceduresDBService.proceduresByPatientId(patient.id);
            for (Procedure procedure : oldProcedures) {
                listView.setItemChecked(procedure.id - 1, true);
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

        patientsProceduresDBService.removeProceduresFromPatient(patient);

        for (int i = 0; i < toAddList.size(); i++) {
            patientsProceduresDBService.addProceduresToPatient(procedureArrayList.get(toAddList.get(i)), patient);
        }

        finish();
    }
}