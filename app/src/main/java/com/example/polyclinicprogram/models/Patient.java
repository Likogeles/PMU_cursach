package com.example.polyclinicprogram.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Patient implements Serializable {

    public int id;
    public String surname;
    public String name;
    public String patronymic;
    public String phone_number;
    public String date_of_birth;
    public ArrayList<Therapy> therapies;

    public Patient(String surname, String name, String patronymic,
                   String phone_number,
                   String date_of_birth) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.phone_number = phone_number;
        this.date_of_birth = date_of_birth;
    }

    public Patient(int id, String surname, String name, String patronymic,
                   String phone_number,
                   String date_of_birth) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.phone_number = phone_number;
        this.date_of_birth = date_of_birth;
    }

    public int getBirthDay(){
        return Integer.parseInt(date_of_birth.substring(0,2));
    }

    public int getBirthMonth(){
        return Integer.parseInt(date_of_birth.substring(3,5));
    }

    public int getBirthYear(){
        return Integer.parseInt(date_of_birth.substring(6,10));
    }

    @Override
    public String toString() {
        String number = phone_number.substring(0,1) + "-";
        number += phone_number.substring(1,4) + "-";
        number += phone_number.substring(4,7) + "-";
        number += phone_number.substring(7,9) + "-";
        number += phone_number.substring(9,11);

        return "ID: " + id + "\n" +
                "ФИО: " + surname + " " + name + " " + patronymic + " " + "\n" +
                "Телефон: " + number + "\n" +
                "Дата рождения: " + date_of_birth;
    }
}
