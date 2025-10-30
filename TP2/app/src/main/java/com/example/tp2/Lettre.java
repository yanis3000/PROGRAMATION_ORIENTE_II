package com.example.tp2;

public class Lettre {

    private int poids, points;
    private char lettre;

    public Lettre(char lettre, int poids, int points) {
        this.lettre = lettre;
        this.poids = poids;
        this.points = points;
    }


    public char getLettre() {
        return lettre;
    }

    public void setLettre(char lettre) {
        this.lettre = lettre;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }
}
