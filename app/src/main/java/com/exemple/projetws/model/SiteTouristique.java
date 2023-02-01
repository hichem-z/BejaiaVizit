package com.exemple.projetws.model;

import java.util.ArrayList;
import java.util.List;

public class SiteTouristique {


    private int id_site;
    private String nom,description,adresse;
    private List<String> image = new ArrayList<>();
    private float x,y;

    public SiteTouristique(int id_site, String nom, String description, String adresse,float x,float y) {
        this.id_site = id_site;
        this.x=x;
        this.y=y;
        this.nom = nom;
        this.description = description;
        this.adresse = adresse;
    }

    public SiteTouristique() {

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

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getId_site() {
        return id_site;
    }

    public void setId_site(int id_site) {
        this.id_site = id_site;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return image;
    }

    public void setImages(List<String> image) {
        this.image = image;
    }
}
