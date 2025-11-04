package com.example.tp2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Lettre { // Cette classe permet d'assigner des instances d'objets à la classe GrilleDeLettres
                      // On passera les points, les poids et les lettres dans GrilleDeLettres

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

    public int getPoints() {
        return points;
    }

    public int getPoids() {
        return poids;
    }

}
