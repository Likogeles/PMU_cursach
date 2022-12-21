package com.example.polyclinicprogram;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.polyclinicprogram.Addlayouts.AddPatientActivity;
import com.example.polyclinicprogram.models.Patient;

import java.util.ArrayList;
import java.util.Date;

public class PatientListActivity extends AppCompatActivity {

    ArrayList<Patient> patientArrayList = new ArrayList<>();
    ArrayAdapter<Patient> adapter;
    ListView listView;

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

        patientArrayList.add(new Patient("asd", "asd", "asd", "88005553535", new Date()));
        patientArrayList.add(new Patient("asd", "asd", "asd", "88005553535", new Date()));
        patientArrayList.add(new Patient("asd", "asd", "asd", "88005553535", new Date()));
        patientArrayList.add(new Patient("asd", "asd", "asd", "88005553535", new Date()));
        patientArrayList.add(new Patient("asd", "asd", "asd", "88005553535", new Date()));

        Button addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this::addBtnClick);

        Button removeBtn = findViewById(R.id.removeBtn);
        removeBtn.setOnClickListener(this::removeBtnClick);

        adapter = new ArrayAdapter<>(this, R.layout.adapter_layout, patientArrayList);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
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
