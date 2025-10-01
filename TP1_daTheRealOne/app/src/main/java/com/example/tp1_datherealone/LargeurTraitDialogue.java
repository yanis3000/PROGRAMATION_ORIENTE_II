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
    SeekBar seekBar;
    TextView texte;
    Button confirm;
    MainActivity parent;
    int progressGlobal;

    public LargeurTraitDialogue(@NonNull MainActivity parent) {
        super(parent);
        this.parent = parent;
        //largeur du parent
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_largeur_trait_dialogue);

        seekBar = findViewById(R.id.seekBar);
        texte = findViewById(R.id.texte);
        confirm = findViewById(R.id.confirm);

        texte.setText(String.valueOf(parent.progressChoisi));


        Ecouteuse ec = new Ecouteuse();
        seekBar.setOnSeekBarChangeListener(ec);
        confirm.setOnClickListener(ec);
    }

    private class Ecouteuse implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
        SharedPreferences sharedPref = parent.getSharedPreferences("seekbarPrefs", Context.MODE_PRIVATE);

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

        }

        @Override
        public void onClick(View source){
            if (source == confirm) {
                parent.progressChoisi = progressGlobal;
//                sharedPref.edit().putInt("seekbarPosition", parent.progressChoisi).apply();
//                int pos = sharedPref.getInt("seekbarPosition", 4);

                dismiss();
            }

        }


    }

}