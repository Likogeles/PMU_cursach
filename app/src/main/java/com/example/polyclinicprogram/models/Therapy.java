package com.example.polyclinicprogram.models;

import java.io.Serializable;

public class Therapy implements Serializable {
    public int id;
    public String name;
    public boolean important;
    public String scope_area;

    public Therapy(String name, boolean important, String scope_area) {
        this.name = name;
        this.important = important;
        this.scope_area = scope_area;
    }

    public Therapy(int id, String name, boolean important, String scope_area) {
        this.id = id;
        this.name = name;
        this.important = important;
        this.scope_area = scope_area;
    }


    @Override
    public String toString() {
        String importantStr = "Не срочно";
        if (important){
            importantStr = "Срочно";
        }
        return "ID: " + id + "\n" +
                "Название: " + name  + "\n" +
                "Важность: " + importantStr + "\n" +
                "Область действия: " + scope_area + "\n";
    }
}
