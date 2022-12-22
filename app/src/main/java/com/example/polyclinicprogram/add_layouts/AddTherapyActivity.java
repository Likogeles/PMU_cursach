package com.example.polyclinicprogram.add_layouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.polyclinicprogram.R;
import com.example.polyclinicprogram.models.Patient;
import com.example.polyclinicprogram.models.Therapy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AddTherapyActivity extends AppCompatActivity {

    EditText editTextName;
    SwitchCompat switchCompatImportant;
    EditText editTextScopeArea;

    TextView textViewImportant;


    Therapy therapy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_therapy);

        editTextName = findViewById(R.id.editName);
        switchCompatImportant = findViewById(R.id.switchCompatImportant);
        editTextScopeArea = findViewById(R.id.editScopeArea);
        textViewImportant = findViewById(R.id.textViewImportant);

        if (switchCompatImportant != null) {
            switchCompatImportant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if (isChecked) {
                        textViewImportant.setText("Срочно");
                    }
                    else {
                        textViewImportant.setText("Не срочно");
                    }
                }
            });
        }

        Button cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(view -> finish());

        Button addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(view -> {
            if (addTherapy()){
                Intent intent = new Intent();
                ArrayList<Therapy> singleArr = new ArrayList<>();
                singleArr.add(therapy);
                intent.putExtra("therapy", singleArr);
                setResult(78, intent);
                finish();
            }
        });

        Intent bundleIntent = getIntent();
        ArrayList<Therapy> arr = (ArrayList<Therapy>) bundleIntent.getSerializableExtra("therapy");
        if (arr != null){
            Therapy editTherapy = arr.get(0);
            therapy = editTherapy;
            editTextName.setText(editTherapy.name);
            switchCompatImportant.setChecked(editTherapy.important);
            editTextScopeArea.setText(editTherapy.scope_area);

            if (switchCompatImportant.isChecked()) {
                textViewImportant.setText("Срочно");
            }
            else {
                textViewImportant.setText("Не срочно");
            }

            addBtn.setText("Сохранить");
        }
    }

    private boolean addTherapy() {

        String name = String.valueOf(editTextName.getText()).trim();
        String scope_area = String.valueOf(editTextScopeArea.getText()).trim();

        if (name.isEmpty() || scope_area.isEmpty()) {
            Toast.makeText(this, "Одно из полей не заполнено!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(therapy == null) {
            therapy = new Therapy(
                    name,
                    switchCompatImportant.isChecked(),
                    scope_area
                    );
        }else{
            therapy.name = name;
            therapy.important = switchCompatImportant.isChecked();
            therapy.scope_area = scope_area;
            System.out.println(3);
        }
        return true;
    }
}