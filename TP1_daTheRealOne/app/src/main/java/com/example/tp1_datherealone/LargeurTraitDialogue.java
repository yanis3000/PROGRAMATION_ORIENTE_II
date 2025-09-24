//package com.example.tp1_datherealone;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//public class LargeurTraitDialogue extends Dialog {
//
//    SeekBar seekBar;
//    TextView texte;
//
//    Button button2;
//
//    MainActivity parent;
//
//
//    public LargeurTraitDialogue(@NonNull Context context) {
//        super(context);
//        this.parent = parent;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_largeur_trait_dialogue);
//
//        seekBar = findViewById(R.id.seekBar);
//        texte = findViewById(R.id.texte);
//        button2 = findViewById(R.id.button2);
//
//        Ecouteuse ec = new Ecouteuse();
//        seekBar.setOnSeekBarChangeListener(ec);
//        button2.setOnClickListener(ec);
//    }
//
//    private class Ecouteuse implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
//        @Override
//        public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser) {
//            texte.setText("Largeur" + progress);
//        }
//
//        @Override
//        public void onStartTrackingTouch(SeekBar seekBar) {
//
//        }
//
//        @Override
//        public void onStopTrackingTouch(SeekBar seekBar) {
//
//        }
//
//        @Override
//        public void onClick(View v){
//            dismiss();
//        }
//
//    }
//}