package com.example.annexe9;

public class NegativeException extends Exception {

    // c'est une classe d'exception, car controllée
    // Exception : il y a une variable message et une methode getMessage()

    private double montantErrone;
    private String nomVariable;

    public NegativeException(String message, double montantErrone, String nomVariable) {
        super(message);
        this.montantErrone = montantErrone;
        this.nomVariable = nomVariable;
    }

    public double getMontantErrone() {
        return montantErrone;
    }

    public void setMontantErrone(double montantErrone) {
        this.montantErrone = montantErrone;
    }

    public String getNomVariable() {
        return nomVariable;
    }

    public void setNomVariable(String nomVariable) {
        this.nomVariable = nomVariable;
    }

}
