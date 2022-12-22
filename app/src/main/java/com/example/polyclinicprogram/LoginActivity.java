package com.example.polyclinicprogram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.polyclinicprogram.add_layouts.AddPatientActivity;
import com.example.polyclinicprogram.add_layouts.RegistrationActivity;
import com.example.polyclinicprogram.db_services.UsersDBService;
import com.example.polyclinicprogram.models.User;

import java.util.ArrayList;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    UsersDBService usersDBService;
    EditText editLogin;
    EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usersDBService = new UsersDBService(this);

        editLogin = findViewById(R.id.editLogin);
        editPassword = findViewById(R.id.editPassword);

        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this::loginBtnClick);
        
        Button registrationBtn = findViewById(R.id.registrationBtn);
        registrationBtn.setOnClickListener(this::registrationBtnClick);
    }

    private void registrationBtnClick(View view) {
        Intent registrationActivity = new Intent(this, RegistrationActivity.class);
        startActivity(registrationActivity);
    }

    private void loginBtnClick(View view) {
        ArrayList<User> users = new ArrayList<>();
        usersDBService.getUsers(users);
        if(checkLoginAndPassword(String.valueOf(editLogin.getText()), String.valueOf(editPassword.getText()), users)){
            Intent mainActivity = new Intent(this, MainActivity.class);
            startActivity(mainActivity);
            editLogin.setText("");
            editPassword.setText("");
        }else{
            Toast.makeText(this, "Неверный логин или пароль!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkLoginAndPassword(String login, String password, ArrayList<User> users){
        for (User user : users){
            if (Objects.equals(login, user.login) && Objects.equals(password, user.password)){
                return true;
            }
        }
        return false;
    }

}