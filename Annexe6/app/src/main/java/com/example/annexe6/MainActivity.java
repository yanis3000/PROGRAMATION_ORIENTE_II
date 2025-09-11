package com.example.annexe6;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

        parent = findViewById(R.id.main);

        // Instancie l'objet
        sd = new SurfaceDessin(this);
        // Lui donner une taille ==> la taille de l'ecran
        sd.setLayoutParams(new ConstraintLayout.LayoutParams(-1, -1)); // Reviens en plein ecran !
        //sd.setLayoutParams(new ConstraintLayout.LayoutParams(dpToPx(200), dpToPx(200))); // Les valeurs sont en pixels
        sd.setBackgroundColor(Color.GRAY);
        // Ajoute
        parent.addView(sd);

    }

    public int dpToPx(int dp) {
        float densite = this.getResources().getDisplayMetrics().density; // densite du telephone
        return Math.round(dp * densite);
        // Pixel 4 sa densite est de 2.75, largeur en pixels d'un Pixel 4 : 1080
    }


    // Surface sur laquelle on va dessiner
    private class SurfaceDessin  extends View {

        Paint crayon;
        Paint crayon2;

        Paint crayon3;

        public SurfaceDessin(Context context) {
            super(context);
            crayon = new Paint(Paint.ANTI_ALIAS_FLAG); // Pour adoucir les courbes (anti-crenelage)
            crayon.setColor(Color.GREEN);

            crayon2 = new Paint(Paint.ANTI_ALIAS_FLAG); // Pour adoucir les courbes (anti-crenelage)
            crayon2.setColor(Color.YELLOW);
            crayon2.setStyle(Paint.Style.STROKE);
            crayon2.setStrokeWidth(30);

            crayon3 = new Paint(Paint.ANTI_ALIAS_FLAG);


        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
            // dessiner ce qu'on veut dessiner

            // Draw the shadow.
            canvas.drawCircle(600, 600, 80, crayon);

            canvas.drawCircle(60, 60, 80, crayon2);

            canvas.drawArc(300, 300, 600, 600, 0, 120, true, crayon);
            crayon.setColor(Color.YELLOW);
            canvas.drawArc(300, 300, 600, 600, 120, 120, true, crayon);
            crayon.setColor(Color.BLACK);
            canvas.drawArc(300, 300, 600, 600, 240, 120, true, crayon);



        }
    }


}