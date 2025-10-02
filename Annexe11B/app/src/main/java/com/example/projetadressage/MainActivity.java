package com.example.projetadressage;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import bla.HashtableAssociation;


public class MainActivity extends AppCompatActivity {

    EditText champPrenom, champNom, champAdresse, champZip;
    Spinner spinnerCapitale, spinnerEtat;
    Button bouton;
    Inscrit inscrit;
//    HashtableAssociation<, >

    



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // remplir les spinner à l'aide de la Hashtable

        champPrenom = findViewById(R.id.champPrenom);
        champNom= findViewById(R.id.champNom);
        champAdresse = findViewById(R.id.champAdresse);
        champZip = findViewById(R.id.champZip);

        spinnerCapitale = findViewById(R.id.spinnerCapitale);
        spinnerEtat = findViewById(R.id.spinnerEtat);

        bouton = findViewById(R.id.boutonInscrire);

        HashtableAssociation bla = new HashtableAssociation();

        bla.put("Tallahasse", "Floride");
        bla.put("Salem", "Oregon");
        bla.put("Juneau", "Alaska");
        bla.put("Columbia", "Caroline du Sud");
        bla.put("Madison", "Wisconsin");

        ArrayList listeChoix = new ArrayList<>();
        ArrayList listeYeee = new ArrayList<>();

        Set<String> ensembleCles = bla.keySet();
        Collection<String> ensembleValue = bla.values();

        listeChoix.addAll(ensembleCles);
        listeYeee.addAll(ensembleValue);

        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listeChoix);
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listeYeee);

        spinnerCapitale.setAdapter(adapter1);
        spinnerEtat.setAdapter(adapter2);

        // ec

        Ecouteur ec = new Ecouteur();
        bouton.setOnClickListener(ec);


    }
    private class Ecouteur implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {

            String nom = champNom.getText().toString();
            String prenom = champPrenom.getText().toString();
            String adresse = champAdresse.getText().toString();
            String capitale = spinnerCapitale.getSelectedItem().toString();
            String etat = spinnerEtat.getSelectedItem().toString();
            String zipcode = champZip.getText().toString();

            if (v == bouton) {
                try {
                    inscrit = new Inscrit(prenom, nom, adresse, capitale, etat, zipcode);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Electeur inscrit");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } catch (AdresseException ae) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage(ae.getMessage());
                    builder.setTitle("Problème");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        }
    }
}