package com.example.polyclinicprogram.Addlayouts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.polyclinicprogram.R;
import com.example.polyclinicprogram.models.Patient;

import android.app.DatePickerDialog.OnDateSetListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class AddPatientActivity extends AppCompatActivity {

    EditText editTextSurname;
    EditText editTextName;
    EditText editTextPatronymic;
    EditText editTextPhoneNumber;
    TextView textViewDateOfBirth;

    LocalDate birthDate;

    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        editTextSurname = findViewById(R.id.editSurname);
        editTextName = findViewById(R.id.editName);
        editTextPatronymic = findViewById(R.id.editPatronymic);
        editTextPhoneNumber = findViewById(R.id.editPhoneNumber);

        textViewDateOfBirth = findViewById(R.id.dateOfBirth);

        birthDate = LocalDate.of(2000, 1, 1);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        textViewDateOfBirth.setText(birthDate.format(dateTimeFormatter));

//        textViewDateOfBirth.setText(localDate);

        Button cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(view -> finish());

        Button addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(view -> {
            if (addPatient()){
                Intent intent = new Intent();
                ArrayList<Patient> singleArr = new ArrayList<>();
                singleArr.add(patient);
                intent.putExtra("patient", singleArr);
                setResult(78, intent);
                finish();
            }
        });

        Button editDateBtn = findViewById(R.id.editDateBtn);
        editDateBtn.setOnClickListener(this::editDateClick);

    }

    private void editDateClick(View view) {
        new DatePickerDialog(this, myCallBack, birthDate.getYear(), birthDate.getMonthValue() - 1, birthDate.getDayOfMonth()).show();
    }

    OnDateSetListener myCallBack = new OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            birthDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            textViewDateOfBirth.setText(birthDate.format(dateTimeFormatter));
        }
    };

    private boolean addPatient() {
        String surname = String.valueOf(editTextSurname.getText()).trim();
        String name = String.valueOf(editTextName.getText()).trim();
        String patronymic = String.valueOf(editTextPatronymic.getText()).trim();
        String phoneNumber = String.valueOf(editTextPhoneNumber.getText()).trim();
        if (surname.isEmpty() || name.isEmpty() || patronymic.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(this, "Одно из полей не заполнено!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phoneNumber.length() != 11){
            Toast.makeText(this, "Номер телефона введён неверно!", Toast.LENGTH_SHORT).show();
            return false;
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        patient = new Patient(surname,
                name,
                patronymic,
                phoneNumber,
                birthDate.format(dateTimeFormatter));

        return true;
    }
}
