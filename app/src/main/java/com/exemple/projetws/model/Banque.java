package com.exemple.projetws.model;

public class Banque {
    private int id_banque;
    private String nom,adresse,telephone,image;
    private float x,y;

    public Banque() {
    }

    public Banque(int id_banque, String nom, String adresse, String telephone, String image,float x,float y) {
        this.id_banque = id_banque;
        this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.image = image;
        this.x =x;
        this.y=y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getId_banque() {
        return id_banque;
    }

    public void setId_banque(int id_banque) {
        this.id_banque = id_banque;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

