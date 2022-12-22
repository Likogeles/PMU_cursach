package com.example.polyclinicprogram.models;

import java.io.Serializable;

public class Procedure implements Serializable {

    public int id;
    public String name;
    public String description;
    public int price;

    public Procedure(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Procedure(int id, String name, String description, int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ID: " + id + '\n' +
                "Название: " + name + '\n' +
                "Описание: " + description + '\n' +
                "Цена: " + price;
    }
}
