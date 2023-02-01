package com.exemple.projetws.model;

public class Agence {
    private int id_agence;
    private String prix,nom,modelVehicule,adresse,numero;
    private float X,Y;



    public Agence() {
    }

    public int getId_agence() {
        return id_agence;
    }

    public void setId_agence(int id_agence) {
        this.id_agence = id_agence;
    }

    public Agence(int id_agence, String prix, String nom, String modelVehicule, String adresse, String numero, float x, float y) {
        this.id_agence = id_agence;
        this.prix = prix;
        this.nom = nom;
        this.modelVehicule = modelVehicule;
        this.adresse = adresse;
        this.numero = numero;
        X = x;
        Y = y;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getModelVehicule() {
        return modelVehicule;
    }

    public void setModelVehicule(String modelVehicule) {
        this.modelVehicule = modelVehicule;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public float getX() {
        return X;
    }

    public void setX(float x) {
        X = x;
    }

    public float getY() {
        return Y;
    }

    public void setY(float y) {
        Y = y;
    }
}
