package com.exemple.projetws.model;

public class Guide {

    private String _id;
    private String nom,prenom,telephone,email,description,photo;
    private int age;

    public Guide(String _id, String nom, String prenom, int age, String telephone, String email, String description, String photo) {
        this._id = _id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.telephone = telephone;
        this.email = email;
        this.description = description;
        this.photo = photo;
    }

    public Guide() {
    }



    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        if (nom.length()>0){
            this.nom = nom;

        }
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        if (prenom.length()>0){
            this.prenom = prenom;

        }
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {

        if (age>18){
            this.age = age;

        }else{
            this.age=0;
        }
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        boolean faux = true;
        if (telephone.length()==10 && telephone.charAt(0)=='0') {
            for (int i = 1; i < 10; i++) {
                if ((telephone.charAt(i)<='0' && telephone.charAt(i)>='9')) {
                    faux = false;
                    break;

                }
            }
            if (faux) {
                this.telephone = "+213 "+telephone.substring(1);
            }

        }

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(email.length()>0) {

            if (email.matches(emailPattern)) {
                this.email = email;
            }

    }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description.length()>0){
            this.description = description;

        }
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
