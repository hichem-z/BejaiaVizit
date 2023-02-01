package com.exemple.projetws.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Taxi {
    @SerializedName("idtaxi")
    private int idtaxi;
    private String prix,adresse;
    private float X,Y;

    public Taxi(int idtaxi, String prix, String adresse, float x, float y) {
        this.idtaxi = idtaxi;
        this.prix = prix;
        this.adresse = adresse;
        X = x;
        Y = y;
    }

    public Taxi() {
    }

    public int getIdtaxi() {
        return idtaxi;
    }

    public void setIdtaxi(int idtaxi) {
        this.idtaxi = idtaxi;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
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

