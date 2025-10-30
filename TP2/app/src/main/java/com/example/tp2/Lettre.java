package com.example.tp2;

public class Lettre {

    private int poids, points;

    public Lettre(int poids, int points) {
        this.poids = poids;
        this.points = points;
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
