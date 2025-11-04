 package com.example.annexe14;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    LinearLayout parent;
    LinearLayout layout1, layout2, layout3, layout4;
    ImageView pastille1, pastille2, pastille3, pastille4;

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

        Ecouteur ec = new Ecouteur();


        for (int i = 0; i < parent.getChildCount(); i++) {
            LinearLayout temp = (LinearLayout) parent.getChildAt(i);
            temp.setOnDragListener(ec);
            temp.getChildAt(0).setOnTouchListener(ec);
        }

    }

    private class Ecouteur implements View.OnTouchListener, View.OnDragListener {
        @Override
        public boolean onDrag(View source, DragEvent event) { // la source ce sont les linearLayout
            Drawable normal = getDrawable(R.drawable.background_contenant);
            Drawable select = getDrawable(R.drawable.background_contenant_selectionne);
            View jeton = null;

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    source.setBackground(select);
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    source.setBackground(normal);
                    break;

                case DragEvent.ACTION_DROP:
                    // aller chercher le jeton
                    jeton = (View) event.getLocalState();
                    // aller chercher les parents initials du jeton
                    LinearLayout parentOriginel = (LinearLayout) jeton.getParent();
                    // enlever le jeton reste sur le conteneur d'origine
                    parentOriginel.removeView(jeton);
                    // recupererr le linearlayout actuel et lui ajouter le jeton
                    LinearLayout nouveauParent = (LinearLayout) source;
                    nouveauParent.addView(jeton);
                    // le remettre visible
                    jeton.setVisibility(VISIBLE);

                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    source.setBackground(normal);
                    break;

            }

            return true;
        }

        @Override
        public boolean onTouch(View source, MotionEvent event) {
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(source);
            source.startDragAndDrop(null, shadowBuilder, source, 512); // 512 : POur le rendre opaque apparamment
            source.setVisibility(INVISIBLE); // View.INVISIBLE
            return true;
        }
    }



}