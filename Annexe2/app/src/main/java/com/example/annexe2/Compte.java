package com.example.annexe2;

// Pas une activite, c'est une classe normale
public class Compte {

    private String nom;
    private double solde;

    // Il faut faire les variables pour ensuite faire un refactor du constructor

    public Compte(String nom, double solde) {
        this.nom = nom;
        this.solde = solde;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }


    // representer l'action de transferer de l'argent

    public boolean transfertArgent(double transfert) {
        if (transfert > this.solde) return false;
        this.solde -= transfert;
        return true;
    }



}
