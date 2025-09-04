package com.example.annexe6;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    SurfaceDessin sd;
    ConstraintLayout parent;

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

        parent.findViewById(R.id.main);

        // Instancie l'objet
        sd = new SurfaceDessin(this);
        // Lui donner une taille ==> la taille de l'ecran
        sd.setLayoutParams(new ConstraintLayout.LayoutParams(-1, -1));
        sd.setBackgroundColor(Color.BLUE);
        // Ajoute
        parent.addView(sd);

    }


    // Surface sur laquelle on va dessiner
    private class SurfaceDessin  extends View {


        public SurfaceDessin(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
            // dessiner ce qu'on veut dessiner


        }
    }


}