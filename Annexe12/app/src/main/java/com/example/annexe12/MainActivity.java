package com.example.annexe12;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView texte;
    ListView liste;
    GestionDB instance;

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

        texte = findViewById(R.id.texte);
        liste = findViewById(R.id.liste);

    }

    @Override
    protected void onStart() {
        super.onStart();
        instance = GestionDB.getInstance(getApplicationContext()); // dans l'idee que c'est un singleton
        // this foncitonne aussi
        instance.ouvrirConnectionDB();
//        choix = instance.retournerInventions(), on aurait pu faire ca
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, instance.retournerInventions());
        liste.setAdapter(adapter);
        Ecouteur ec = new Ecouteur();
        liste.setOnItemClickListener(ec); // spinner : OnItemSelectedListener
    }

    @Override
    protected void onStop() {
        super.onStop();
        instance.fermerConnectionDB();
    }

    private class Ecouteur implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//            if (view == liste.getChildAt(2))  texte.setText("Bonne reponse");
//            else texte.setText("Mauvaise reponse");

//            String itemSelectionne = instance.retournerInventions().get(position);
//
            String itemSelectionne = (String) liste.getItemAtPosition(position);

                if (instance.aBonneReponse(itemSelectionne, "Mary Anderson")) {
                    texte.setText("oui");
                    view.setBackgroundColor(Color.GREEN);
                } else {
                    texte.setText("non");
                    view.setBackgroundColor(Color.RED);
                    try {
                        liste.getChildAt(instance.trouverIndiceBonneReponse("Mary Anderson")).setBackgroundColor(Color.GREEN);
                    } catch (Exception e) {
//                        throw new RuntimeException(e);
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                liste.setOnItemClickListener(null);


        }
    }
}