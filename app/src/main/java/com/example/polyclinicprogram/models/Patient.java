package com.example.polyclinicprogram.models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Patient implements Serializable {

    String surname;
    String name;
    String patronymic;
    String phone_number;
    Date date_of_birth;

    public Patient(String surname,String name,String patronymic,
                   String phone_number,
                   Date date_of_birth) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.phone_number = phone_number;
        this.date_of_birth = date_of_birth;
    }

    @Override
    public String toString() {

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyy");
        String strDate = dateFormat.format(date_of_birth);
        String number = phone_number.substring(0,1) + "-";
        number += phone_number.substring(1,4) + "-";
        number += phone_number.substring(4,7) + "-";
        number += phone_number.substring(7,9) + "-";
        number += phone_number.substring(9,11);

        return "ФИО: " + surname + " " + name + " " + patronymic + " " + "\n" +
                "Телефон: " + number + "\n" +
                "Дата рождения: " + strDate;
    }
}
