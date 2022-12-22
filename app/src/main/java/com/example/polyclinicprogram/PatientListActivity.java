package com.example.polyclinicprogram;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.polyclinicprogram.add_layouts.AddPatientActivity;
import com.example.polyclinicprogram.db_services.PatientsDBService;
import com.example.polyclinicprogram.models.Patient;

import java.util.ArrayList;

public class PatientListActivity extends AppCompatActivity {

    ArrayList<Patient> patientArrayList = new ArrayList<>();
    ArrayAdapter<Patient> adapter;
    ListView listView;

    PatientsDBService patientsDBService;

    // Получение результата из страницы добавления пациента.
    ActivityResultLauncher<Intent> addActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 78){
                        Intent intent = result.getData();
                        if(intent != null){
                            ArrayList<Patient> new_patient = (ArrayList<Patient>)intent.getSerializableExtra("patient");

                            if(new_patient.get(0).id == 0) {
                                patientArrayList.add(new_patient.get(0));
                            }else{
                                patientArrayList.set(new_patient.get(0).id - 1,(new_patient.get(0)));
                            }

                            patientsDBService.savePatients(patientArrayList);
                            patientsDBService.readPatients(patientArrayList);
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

        Button addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this::addBtnClick);

        Button removeBtn = findViewById(R.id.removeBtn);
        removeBtn.setOnClickListener(this::removeBtnClick);

        Button editBtn = findViewById(R.id.editBtn);
        editBtn.setOnClickListener(this::editBtnClick);

        Button addTherapyBtn = findViewById(R.id.addTherapyBtn);
        addTherapyBtn.setOnClickListener(this::addTherapyBtnClick);

        Button addProcedureBtn = findViewById(R.id.addProcedureBtn);
        addProcedureBtn.setOnClickListener(this::addProcedureBtnClick);

        patientsDBService = new PatientsDBService(this);
        patientsDBService.readPatients(patientArrayList);
        adapter = new ArrayAdapter<>(this, R.layout.adapter_layout, patientArrayList);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    private void addProcedureBtnClick(View view) {
        SparseBooleanArray sbArray = listView.getCheckedItemPositions();

        if(sbArray.size() == 0){
            return ;
        }

        Intent addProcedureToPatientActivity = new Intent(this, AddProcedureToPatientActivity.class);
        ArrayList<Patient> singleArr = new ArrayList<>();
        singleArr.add(patientArrayList.get(sbArray.keyAt(0)));
        addProcedureToPatientActivity.putExtra("patient", singleArr);
        startActivity(addProcedureToPatientActivity);
    }

    private void addTherapyBtnClick(View view) {
        SparseBooleanArray sbArray = listView.getCheckedItemPositions();

        if(sbArray.size() == 0){
            return ;
        }

        Intent addTherapyToPatientActivity = new Intent(this, AddTherapyToPatientActivity.class);
        ArrayList<Patient> singleArr = new ArrayList<>();
        singleArr.add(patientArrayList.get(sbArray.keyAt(0)));
        addTherapyToPatientActivity.putExtra("patient", singleArr);

        startActivity(addTherapyToPatientActivity);
    }

    private void editBtnClick(View view) {

        SparseBooleanArray sbArray = listView.getCheckedItemPositions();

        if(sbArray.size() == 0){
            return ;
        }

        Intent addPatientActivity = new Intent(this, AddPatientActivity.class);

        ArrayList<Patient> singleArr = new ArrayList<>();
        singleArr.add(patientArrayList.get(sbArray.keyAt(0)));
        addPatientActivity.putExtra("patient", singleArr);

        addActivityResultLauncher.launch(addPatientActivity);
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
        patientsDBService.savePatients(patientArrayList);
        patientsDBService.readPatients(patientArrayList);
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        patientsDBService.readPatients(patientArrayList);
        adapter.notifyDataSetChanged();
        super.onResume();
    }
}
