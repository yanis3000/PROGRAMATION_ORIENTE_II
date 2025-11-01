package com.example.annexe3b;
import java.util.Vector;

public class Commande {

    private Vector<Produit> listeCommande;

    public Commande () {
        listeCommande = new Vector();
    }

    public void ajouterProduit ( Produit p )
    {
        listeCommande.add(p);
    }

    public double total()
    {
	double total =0;
      // compléter : total de la commande
//        for (int i = 0; i < listeCommande.size(); i++) {
//            total +=  listeCommande.get(i).getPrixUnitaire() * listeCommande.get(i).getQte();
//        }

        // Boucle ameliorer

        for (Produit p : listeCommande) { // Pour chaque produit qui vient de ma liste de commande
            total += p.getPrixUnitaire() * p.getQte();
        }


	return total;
    }

    public double taxes()
    {
        double taxes = 0;

	// compléter : montant des taxes sur le total de la commande
        taxes = total();
        // tps sur le montant avant taxes ( 5% )
        double tps = taxes * 0.05;
        //tvq sur le montant avant taxes ( 9.975% )
        double tvq = taxes * 0.09975;
        // taxes total = tps + tvq
        taxes = tps + tvq;


        return taxes;
    }

    public double grandTotal(){

	double grTotal = 0;

	// compléter

        grTotal = total() + taxes();


	return grTotal;


    }
}