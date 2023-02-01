package com.exemple.projetws.model;

public class Bus {
        private int idbus;
        private String idligne,depart,arrive;
            private float X,Y,A,B;

    public Bus(int idbus, String idligne, String depart, String arrive, float x, float y, float a, float b) {
        this.idbus = idbus;
        this.idligne = idligne;
        this.depart = depart;
        this.arrive = arrive;
        X = x;
        Y = y;
        A = a;
        B = b;
    }

    public int getIdbus() {
        return idbus;
    }

    public void setIdbus(int idbus) {
        this.idbus = idbus;
    }

    public String getIdligne() {
        return idligne;
    }

    public void setIdligne(String idligne) {
        this.idligne = idligne;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
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

    public float getA() {
        return A;
    }

    public void setA(float a) {
        A = a;
    }

    public float getB() {
        return B;
    }

    public void setB(float b) {
        B = b;
    }
}
