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
import com.example.polyclinicprogram.add_layouts.AddProcedureActivity;
import com.example.polyclinicprogram.db_services.PatientsDBService;
import com.example.polyclinicprogram.db_services.ProceduresDBService;
import com.example.polyclinicprogram.models.Patient;
import com.example.polyclinicprogram.models.Procedure;

import java.util.ArrayList;

public class ProceduresListActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<Procedure> adapter;
    ArrayList<Procedure> procedureArrayList;
    ProceduresDBService proceduresDBService;

    // Получение результата из страницы добавления пациента.
    ActivityResultLauncher<Intent> addActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 78){
                        Intent intent = result.getData();
                        if(intent != null){
                            ArrayList<Procedure> new_procedure = (ArrayList<Procedure>)intent.getSerializableExtra("procedure");
                            if(new_procedure.get(0).id == 0) {
                                procedureArrayList.add(new_procedure.get(0));
                            }else{
                                procedureArrayList.set(new_procedure.get(0).id - 1,(new_procedure.get(0)));
                            }

                            proceduresDBService.saveProcedures(procedureArrayList);
                            proceduresDBService.readProcedures(procedureArrayList);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procedures_list);


        Button addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this::addBtnClick);

        Button removeBtn = findViewById(R.id.removeBtn);
        removeBtn.setOnClickListener(this::removeBtnClick);

        Button editBtn = findViewById(R.id.editBtn);
        editBtn.setOnClickListener(this::editBtnClick);

        proceduresDBService = new ProceduresDBService(this);
        procedureArrayList = new ArrayList<>();
        proceduresDBService.readProcedures(procedureArrayList);
        adapter = new ArrayAdapter<>(this, R.layout.adapter_layout, procedureArrayList);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    private void editBtnClick(View view) {
        SparseBooleanArray sbArray = listView.getCheckedItemPositions();

        if(sbArray.size() == 0){
            return ;
        }

        Intent addProcedureActivity = new Intent(this, AddProcedureActivity.class);

        ArrayList<Procedure> singleArr = new ArrayList<>();
        singleArr.add(procedureArrayList.get(sbArray.keyAt(0)));
        addProcedureActivity.putExtra("procedure", singleArr);

        addActivityResultLauncher.launch(addProcedureActivity);
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
            if (index < procedureArrayList.size()) {
                procedureArrayList.remove(procedureArrayList.get(index));
            }
        }
        proceduresDBService.saveProcedures(procedureArrayList);
        proceduresDBService.readProcedures(procedureArrayList);
        adapter.notifyDataSetChanged();
    }

    private void addBtnClick(View view) {
        Intent addProcedureActivity = new Intent(this, AddProcedureActivity.class);
        addActivityResultLauncher.launch(addProcedureActivity);
    }
}