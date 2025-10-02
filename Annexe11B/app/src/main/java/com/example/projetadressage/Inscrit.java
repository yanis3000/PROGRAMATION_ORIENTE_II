package com.example.projetadressage;


import java.util.ArrayList;
import java.util.Set;

import bla.HashtableAssociation;

public class Inscrit {
    private String nom;
    private String prenom;
    private String adresse;
    private String capitale;
    private String etat;
    private String codeZip;

    public Inscrit(String nom, String prenom, String adresse, String capitale, String etat, String codeZip) throws AdresseException{
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.codeZip = codeZip;


        // vérifier si la capitale fait partie de l'état à l'aide d'une Hashtable secrète ( classe HashtableAssociation )
        HashtableAssociation ha = new HashtableAssociation();
        // on verifie si la cle capital
        if (ha.containsKey(capitale)) {
            String etatTrouve = ha.get(capitale); // je vais comparer le resultat avec l'etat en parametre
            if (!etatTrouve.equals(etat)) { // si ca ne fonctionne pas je lance une exeception
                throw new AdresseException(capitale, etat);
            }
            // s'ils sont egaux, ca fonctionne je les assigne aux variables,cet inscrit est
            this.capitale = capitale;
            this.etat = etat;
        }

        else {
            throw new AdresseException(capitale, null);
        }

    }
}
