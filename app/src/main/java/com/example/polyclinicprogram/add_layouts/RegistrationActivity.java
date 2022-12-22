package com.example.polyclinicprogram.add_layouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.polyclinicprogram.R;
import com.example.polyclinicprogram.db_services.UsersDBService;
import com.example.polyclinicprogram.models.User;

import java.util.ArrayList;

public class RegistrationActivity extends AppCompatActivity {

    EditText editLogin;
    EditText editPassword;
    EditText editEmail;
    SwitchCompat switchCompatAdmin;
    UsersDBService usersDBService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        usersDBService = new UsersDBService(this);

        editLogin = findViewById(R.id.editLogin);
        editPassword = findViewById(R.id.editPassword);
        editEmail = findViewById(R.id.editEmail);
        switchCompatAdmin = findViewById(R.id.switchCompatAdmin);

        Button cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(view -> finish());

        Button registrationBtn = findViewById(R.id.registrationBtn);
        registrationBtn.setOnClickListener(view -> {
            if (addUser()){
                finish();
            }
        });
    }

    private boolean addUser() {
        String login = String.valueOf(editLogin.getText());
        String password = String.valueOf(editPassword.getText());
        String email = String.valueOf(editEmail.getText());

        if(login.isEmpty() || password.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "Одно из полей не заполнено!", Toast.LENGTH_SHORT).show();
            return false;
        }
        User user = new User(login, password, email, switchCompatAdmin.isChecked());

        user.id = usersDBService.getLastID();

        ArrayList<User> users = new ArrayList<>();
        usersDBService.getUsers(users);

        if(usersDBService.haveUser(user)){
            Toast.makeText(this, "Пользователь с таким логином уже существует!", Toast.LENGTH_SHORT).show();
            return false;
        }
        usersDBService.addUser(user);
        return true;
    }
}