package com.example.tp1_datherealone;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class LargeurTraitDialogue extends Dialog {
    SeekBar seekBar; // barre de changement de l'epaisseur
    TextView texte; // affiche la valeur de l'epaisseur
    Button confirm; //
    MainActivity parent;
    int progressGlobal; // intermediaire servant a faire passer la valeur du seekbar dans la variable parent.progressChoisi

    public LargeurTraitDialogue(@NonNull MainActivity parent) { // garde la reference a mainActivity
        super(parent);
        this.parent = parent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_largeur_trait_dialogue);

        seekBar = findViewById(R.id.seekBar);
        texte = findViewById(R.id.texte);
        confirm = findViewById(R.id.confirm);

        texte.setText(String.valueOf(parent.progressChoisi)); // pour afficher l'etat actuel de l'epaisseur en texte

        Ecouteuse ec = new Ecouteuse();
        seekBar.setOnSeekBarChangeListener(ec);
        confirm.setOnClickListener(ec);

        seekBar.setProgress(parent.progressChoisi); // pour afficher l'etat actuel de l'epaisseur sur le seekbar
    }

    private class Ecouteuse implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
        @Override
        public void onProgressChanged(SeekBar seekbar, int p, boolean fromUser) {
            progressGlobal = p;
            texte.setText(String.valueOf(progressGlobal));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            seekBar.setProgress(progressGlobal); // pour afficher l'epaisseur en court du texte
        }

        @Override
        public void onClick(View source){
            // Les valeurs ont ete modifie, si confirme
            if (source == confirm) {
                parent.progressChoisi = progressGlobal;
                seekBar.setProgress(parent.progressChoisi);
                dismiss();
            }

        }


    }

}