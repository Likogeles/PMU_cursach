package com.example.polyclinicprogram.models;

import java.io.Serializable;

public class User implements Serializable {
    public int id;
    public String login;
    public String password;
    public String email;
    public boolean admin;

    public User(int id, String login, String password, String email, boolean admin) {
        this.id = id;
        this.login = login;
        this.admin = admin;
        this.password = password;
        this.email = email;
    }

    public User(String login, String password, String email, boolean admin) {
        this.login = login;
        this.admin = admin;
        this.password = password;
        this.email = email;
    }


    @Override
    public String toString() {
        return "ID: " + id + '\n' +
                "Login: " + login + '\n' +
                "Password: " + password + '\n' +
                "Email: " + email + '\n' +
                "Admin: " + admin;
    }
}
