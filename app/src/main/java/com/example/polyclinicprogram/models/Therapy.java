package com.example.polyclinicprogram.models;

public class Therapy {
    int id;
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
        return "ID: " + id + "\n" +
                "Название: " + name  + "\n" +
                "Срочность: " + important + "\n" +
                "Область действия: " + scope_area + "\n";
    }
}
