package com.example.annexe12;

public class Inventeur {

    private String nom, origine, invention;
    private int annee;

    public Inventeur(String nom, String origine, String invention, Integer annee) {
        this.nom = nom;
        this.origine = origine;
        this.invention = invention;
        this.annee = annee;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getInvention() {
        return invention;
    }

    public void setInvention(String invention) {
        this.invention = invention;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

}
