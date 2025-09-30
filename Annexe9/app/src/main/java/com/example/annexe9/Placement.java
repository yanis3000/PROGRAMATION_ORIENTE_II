package com.example.annexe9;

public class Placement 
{
private double montant;
private int nbMois;

private static final double INTERET = 0.024/12;  // 12 mois, taux d interet annuel

  public Placement( double montant, int nbMois) throws NegativeException   // indique explicitement qu'une exception peut être lancée
  {
    // meilleur emplacement pour gerer les exceptions
    if (montant < 0) {
      throw new NegativeException("Le montant est negative, shame", montant, "montant");
    }
    this.montant = montant;
    this.nbMois =   nbMois;
  }

  public double calculerMontantFinal () {

    double total = montant;
    for ( int i = 0; i < nbMois ; i ++ )
      total = total + total * INTERET;
    return total;
  }
  
}