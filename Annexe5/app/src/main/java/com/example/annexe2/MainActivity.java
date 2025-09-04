package com.example.annexe2;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;


public class MainActivity extends AppCompatActivity {

    Button boutonEnvoyer;
    Spinner nomCompte;
    EditText champCourriel;
    EditText champMontant;
    ArrayList<String> listeChoix;
    HashMap<String,Compte> lesComptes;
    double solde;
    double transfer;
    TextView champsSolde;

    Compte compte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialiser les composants

        boutonEnvoyer = findViewById(R.id.bouton_envoyer);
        nomCompte = findViewById(R.id.texte_de);


        lesComptes = new HashMap<>();
        // ajouter les 3 comptes a la hashmap
        lesComptes.put("CHEQUE", new Compte("CHEQUE", 455));
        lesComptes.put("EPARGNE", new Compte("EPARGNE", 3000));
        lesComptes.put("EPARGNEPLUS", new Compte("EPARGNEPLUS", 90));

        listeChoix = new ArrayList<>();

        Set<String> ensembleCles = lesComptes.keySet();

        listeChoix.addAll(ensembleCles);
        Collections.sort(listeChoix);

        champsSolde = findViewById(R.id.texte_solde);
        champCourriel = findViewById(R.id.texte_a);
        champMontant = findViewById(R.id.texte_transfer);
        champMontant.setText("0");


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listeChoix);
        nomCompte.setAdapter(adapter);

        // Créer un écouteur
        Ecouteur ec = new Ecouteur();

        // Source à l'écouteur

        boutonEnvoyer.setOnClickListener(ec);
        nomCompte.setOnItemSelectedListener(ec);

    }
    // Définir classe
    private class Ecouteur implements View.OnClickListener, AdapterView.OnItemSelectedListener
    {
        @SuppressLint("NewApi")
        @Override
        public void onClick(View source) {

            if (source == boutonEnvoyer){
                String montant = String.valueOf(champMontant.getText()).trim();
                String courriel = champCourriel.getText().toString().trim();

                if(!courriel.matches("[.\\w\\-]+@+[\\w\\-]+[.]+[.\\w\\-]+")){
                    // 1. Instantiate an AlertDialog.Builder with its constructor.
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

// 2. Chain together various setter methods to set the dialog characteristics.
                    builder.setMessage(R.string.dialog_message)
                            .setTitle(R.string.dialog_title);

// 3. Get the AlertDialog.
                    AlertDialog dialog = builder.create();
                    dialog.show(); // Pour qu'on le voit !
                }
//                else {
//                    {
//                        transfer = Double.parseDouble(String.valueOf(champMontant));
//                        solde -= transfer;
//                        champsSolde.setText(String.valueOf(solde));
//                    }
//
//
//                }
            }

        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // Trouver le nom du compte et de l'afficher
            Toast.makeText(MainActivity.this, "Vous avez sélectionner le compte : " + parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

            String selec = parent.getSelectedItem().toString();
            double yee = lesComptes.get(selec).getSolde();
            champsSolde.setText(String.valueOf(yee));





            // Alternative
            //  TextView temp = (TextView) view;
            //  Toast.makeText(MainActivity.this, temp.getText(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}