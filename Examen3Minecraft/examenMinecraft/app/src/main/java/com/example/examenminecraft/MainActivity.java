package com.example.examenminecraft;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {


    Surface surf;
    ConstraintLayout conteneur;
    TextView texteSpinner, texteCouleur, texteLargeur;

    Button boutonVert, boutonRouge, boutonApercu;
    int couleur, largeur;
    SeekBar seek;
    ItemMinecraft i;
    String type;

    ChipGroup groupe;
    Chip chipBambou;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        conteneur = findViewById(R.id.conteneur);

        texteCouleur = findViewById(R.id.texteCouleur);
        texteLargeur = findViewById(R.id.texteLargeur);
        boutonRouge = findViewById(R.id.boutonRouge);
        boutonVert = findViewById(R.id.boutonVert);
        seek = findViewById(R.id.seekBar);
        boutonApercu = findViewById(R.id.boutonApercu);
        groupe = findViewById(R.id.groupe);




        surf = new Surface(this);
        surf.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        conteneur.addView(surf);
        surf.setBackgroundColor(Color.LTGRAY);

        Ecouteur ec = new Ecouteur();

        boutonRouge.setOnClickListener(ec);
        boutonVert.setOnClickListener(ec);
        seek.setOnSeekBarChangeListener(ec);
        boutonApercu.setOnClickListener(ec);

       for ( int i = 0; i < groupe.getChildCount(); i++)
           ((Chip)groupe.getChildAt(i)).setOnCheckedChangeListener(ec);

        chipBambou = findViewById(R.id.chipBambou);
        chipBambou.setChecked(true);



    }

    private class Ecouteur implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener
    {

        @Override
        public void onClick(View v) {
            if ( v == boutonRouge ) {
                texteCouleur.setText("Couleur de l'item : " + " Rouge");
                couleur = Color.RED;
            }
            else if (v == boutonVert) {
                texteCouleur.setText("Couleur de l'item : " + " Vert");
                couleur = Color.GREEN;
            }

            try {
                if (v == boutonApercu) {
                    i = new ItemMinecraft(type, couleur, largeur);

                    if (type.equals("Bambou") && couleur == Color.RED) {
                        throw new MinecraftException("Le bambou ne peut pas être de couleur rouge");
                    }
                    else if (type.equals("Champignon") && couleur == Color.GREEN) {
                        throw new MinecraftException("Le champignon ne peut pas être de couleur verte");
                    }
                    surf.invalidate();
                }
            } catch (MinecraftException me) {
                Toast.makeText(MainActivity.this, me.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            texteLargeur.setText("Largeur : " + progress);
            largeur = progress;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if ( isChecked) {
                type = buttonView.getText().toString();
                
            }

        }
    }

    private class Surface extends View{

        public Surface(Context context) {
            super(context);
        }
        @Override
        protected void onDraw (Canvas c)
        {
            super.onDraw(c);
            if ( i != null )
                i.dessiner(c);

        }
    }

}