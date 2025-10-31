package com.example.tp2;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GrilleDeLettres {
    HashMap<Character, Lettre> hashLettres;
    Lettre lettreRand;


    public GrilleDeLettres() {
        hashLettres = new HashMap<>();

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

        ArrayList<Lettre> lettresTot = new ArrayList<>();

        for (Lettre val : hashLettres.values()) {
            for (int i = 0; i < val.getPoids(); i++) {
                lettresTot.add(val);
            }
        }

        lettreRand = lettresTot.get(random.nextInt(lettresTot.size()));

    }


}
