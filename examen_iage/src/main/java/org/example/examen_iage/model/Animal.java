package org.example.examen_iage.model;

public class Animal {

    private int id;

    private String nom;
    private int age;
    private String type;
    private int nombre;

    public Animal(String nom, int age, String type, int nombre) {
        this.nom = nom;
        this.age = age;
        this.type = type;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

}
