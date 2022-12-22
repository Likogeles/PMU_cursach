package com.example.polyclinicprogram.add_layouts;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.polyclinicprogram.R;
import com.example.polyclinicprogram.models.Patient;
import com.example.polyclinicprogram.models.Procedure;

import java.time.LocalDate;
import java.util.ArrayList;

public class AddProcedureActivity extends AppCompatActivity {

    EditText editProcedureName;
    EditText editProcedureDescription;
    EditText editProcedurePrice;

    Procedure procedure;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_procedure);

        editProcedureName = findViewById(R.id.editProcedureName);
        editProcedureDescription = findViewById(R.id.editProcedureDescription);
        editProcedurePrice = findViewById(R.id.editProcedurePrice);

        Button cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(view -> finish());

        Button addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(view -> {
            if (addProcedure()){
                Intent intent = new Intent();
                ArrayList<Procedure> singleArr = new ArrayList<>();
                singleArr.add(procedure);
                intent.putExtra("procedure", singleArr);

                setResult(78, intent);
                finish();
            }
        });

        Intent bundleIntent = getIntent();
        ArrayList<Procedure> arr = (ArrayList<Procedure>) bundleIntent.getSerializableExtra("procedure");
        if (arr != null){
            procedure = arr.get(0);
            editProcedureName.setText(procedure.name);
            editProcedureDescription.setText(procedure.description);
            editProcedurePrice.setText("" + procedure.price);
            addBtn.setText("Сохранить");
        }
    }

    private boolean addProcedure() {
        String name = String.valueOf(editProcedureName.getText()).trim();
        String description = String.valueOf(editProcedureDescription.getText()).trim();
        String price = String.valueOf(editProcedurePrice.getText()).trim();

        if (name.isEmpty() || description.isEmpty() || price.isEmpty()) {
            Toast.makeText(this, "Одно из полей не заполнено!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(procedure == null) {
            procedure = new Procedure(name, description, Integer.parseInt(price));
        }else{
            procedure.name = name;
            procedure.description = description;
            procedure.price = Integer.parseInt(price);
        }
        return true;
    }
}