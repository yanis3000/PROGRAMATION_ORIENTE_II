package com.example.tp2;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class GrilleDeLettres {
    HashMap<Character, Lettre> hashLettres;
    Lettre lettreRand;


    public GrilleDeLettres() {
        hashLettres = new HashMap<>();

        // système de pointage du jeu Scrabble
        // https://fr.wikipedia.org/wiki/Lettres_du_Scrabble#Fran%C3%A7ais

        hashLettres.put('A', new Lettre('A', 9, 1));
        hashLettres.put('B', new Lettre('B',2, 3));
        hashLettres.put('C', new Lettre('C',2, 3));
        hashLettres.put('D', new Lettre('D',3, 2));
        hashLettres.put('E', new Lettre('E',15, 1));
        hashLettres.put('F', new Lettre('F',2, 4));
        hashLettres.put('G', new Lettre('G',2, 2));
        hashLettres.put('H', new Lettre('H',2, 4));
        hashLettres.put('I', new Lettre('I',8, 1));
        hashLettres.put('J', new Lettre('J',1, 8));
        hashLettres.put('K', new Lettre('K',1, 10));
        hashLettres.put('L', new Lettre('L',5, 1));
        hashLettres.put('M', new Lettre('M',3, 2));
        hashLettres.put('N', new Lettre('N',6, 1));
        hashLettres.put('O', new Lettre('O',6, 1));
        hashLettres.put('P', new Lettre('P',2, 3));
        hashLettres.put('Q', new Lettre('Q',1, 8));
        hashLettres.put('R', new Lettre('R',6, 1));
        hashLettres.put('S', new Lettre('S',6, 1));
        hashLettres.put('T', new Lettre('T',6, 1));
        hashLettres.put('U', new Lettre('U',6, 1));
        hashLettres.put('V', new Lettre('V',2, 4));
        hashLettres.put('W', new Lettre('W',1, 10));
        hashLettres.put('X', new Lettre('X',1, 10));
        hashLettres.put('Y', new Lettre('Y',1, 10));
        hashLettres.put('Z', new Lettre('Z',1, 10));

    }

    public void genererLettres() {

        Random random = new Random();

        ArrayList<Lettre> lettresTot = new ArrayList<>(); // liste pour récupérer toute les lettres

        for (Lettre val : hashLettres.values()) { // Pour chaque lettre de la Hashmap
            for (int i = 0; i < val.getPoids(); i++) {
                lettresTot.add(val);    // On ajoute à la liste, les lettres selon leur poids (ex : La lettre E sera rajouté 15 fois)
            }
        }

        lettreRand = lettresTot.get(random.nextInt(lettresTot.size())); // On accède à une lettre aléatoire avec un index random

    }

    public ArrayList<Integer> genererMulti() {
        ArrayList<Integer> multiScore = new ArrayList<>(); // liste pour récupérer tous les mutliplicateurs

        for (int i = 0; i < 12; i++) {
            multiScore.add(1); // il y aura 11 : 1x
        }

        for (int i = 0; i < 2; i++) {
            multiScore.add(2); // il y aura 2 : 2x
        }

        multiScore.add(3); // il y aura 1 : 3x

        multiScore.add(4); // fonctionne comme le double, qui sera afficher par un "D" dans le jeu

        Collections.shuffle(multiScore); // on randomise

        return multiScore;
    }


}
