package com.example.tp2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Composant  extends ConstraintLayout {

    private TextView texteLettre, texteValeur, texteMulti;

    public Composant(@NonNull Context context) {
        super(context);
        init(context);
    }

    public Composant(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Composant(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.composant, this, true);
        texteLettre = findViewById(R.id.texteLettre);
        texteValeur = findViewById(R.id.texteValeur);
        texteMulti = findViewById(R.id.texteMulti);

    }

    public TextView getTexteLettre() {
        return texteLettre;
    }

    public TextView getTexteValeur() {
        return texteValeur;
    }

    public TextView getTexteMulti() {
        return texteMulti;
    }
}
