package com.exemple.projetws.model;


import java.util.ArrayList;
import java.util.List;


public class Hotel {

    private String id;
    private int id_hotel;
    private String nom,description,map,adresse,imageCover,telephone;
    private List<String> image = new ArrayList<>();

    public Hotel() {
    }

    public Hotel(int id_hotel, String nom, String description, String map, List<String> image,String adresse,String imageCover,String telephone) {
        this.id_hotel = id_hotel;
        this.nom = nom;
        this.description = description;
        this.map = map;
        this.telephone=telephone;
        this.image = image;
        this.adresse = adresse;
        this.imageCover=imageCover;

    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getId_hotel() {
        return id_hotel;
    }

    public void setId_hotel(int id_hotel) {
        this.id_hotel = id_hotel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
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

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public List<String> getImages() {
        return image;
    }

    public void setImages(List<String> image) {
        this.image = image;
    }

    public String getImageCover() {
        return imageCover;
    }

    public void setImageCover(String imageCover) {
        this.imageCover = imageCover;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
