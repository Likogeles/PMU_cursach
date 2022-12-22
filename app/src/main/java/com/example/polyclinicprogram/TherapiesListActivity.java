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

import com.example.polyclinicprogram.add_layouts.AddPatientActivity;
import com.example.polyclinicprogram.add_layouts.AddTherapyActivity;
import com.example.polyclinicprogram.db_services.PatientsDBService;
import com.example.polyclinicprogram.db_services.TherapiesDBService;
import com.example.polyclinicprogram.models.Patient;
import com.example.polyclinicprogram.models.Therapy;

import java.util.ArrayList;

public class TherapiesListActivity extends AppCompatActivity {

    ArrayList<Therapy> therapyArrayList = new ArrayList<>();
    ArrayAdapter<Therapy> adapter;
    ListView listView;

    TherapiesDBService therapiesDBService;

    // Получение результата из страницы добавления пациента.
    ActivityResultLauncher<Intent> addActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 78){
                        Intent intent = result.getData();
                        if(intent != null){
                            ArrayList<Therapy> new_therapy = (ArrayList<Therapy>)intent.getSerializableExtra("therapy");

                            if(new_therapy.get(0).id == 0) {
                                therapyArrayList.add(new_therapy.get(0));
                            }else{
                                therapyArrayList.set(new_therapy.get(0).id - 1,(new_therapy.get(0)));
                            }

                            therapiesDBService.saveTherapies(therapyArrayList);
                            therapiesDBService.readTherapies(therapyArrayList);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapies_list);

        Button addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this::addBtnClick);

        Button removeBtn = findViewById(R.id.removeBtn);
        removeBtn.setOnClickListener(this::removeBtnClick);

        Button editBtn = findViewById(R.id.editBtn);
        editBtn.setOnClickListener(this::editBtnClick);

        therapiesDBService = new TherapiesDBService(this);
        therapiesDBService.readTherapies(therapyArrayList);
        adapter = new ArrayAdapter<>(this, R.layout.adapter_layout, therapyArrayList);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

    }

    private void editBtnClick(View view) {

        SparseBooleanArray sbArray = listView.getCheckedItemPositions();

        if(sbArray.size() == 0){
            return ;
        }

        Intent addTherapyActivity = new Intent(this, AddTherapyActivity.class);

        ArrayList<Therapy> singleArr = new ArrayList<>();
        singleArr.add(therapyArrayList.get(sbArray.keyAt(0)));
        addTherapyActivity.putExtra("therapy", singleArr);

        addActivityResultLauncher.launch(addTherapyActivity);
    }

    private void addBtnClick(View view) {

        Intent addTherapyActivity = new Intent(this, AddTherapyActivity.class);
        addActivityResultLauncher.launch(addTherapyActivity);
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
            if (index < therapyArrayList.size()) {
                therapyArrayList.remove(therapyArrayList.get(index));
            }
        }
        therapiesDBService.saveTherapies(therapyArrayList);
        therapiesDBService.readTherapies(therapyArrayList);
        adapter.notifyDataSetChanged();
    }

}