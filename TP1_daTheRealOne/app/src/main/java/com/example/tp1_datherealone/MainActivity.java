package com.example.tp1_datherealone;

import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView bouton;

    ConstraintLayout parent;

    int couleurCourante = Color.BLACK;

    Chip black;
    Chip gray;
    Chip blue;
    Chip yellow;
    Chip red;
    Chip orange;
    Chip green;
    Chip purple;
    TraceLibre traceLibre;
    Efface efface;
    ImageView imgTraceLibre;
    ImageView imgEfface;
    ChipGroup chipGroup;
    ArrayList<TraceLibre> dessin = new ArrayList<TraceLibre>();


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

        parent = findViewById(R.id.parent);
        chipGroup = findViewById(R.id.ChipGroup);

        bouton = findViewById(R.id.bouton);
        Ecouteur ec = new Ecouteur();
        bouton.setOnClickListener(ec);

//        black = findViewById(R.id.black);
//        gray = findViewById(R.id.gray);
//        blue = findViewById(R.id.blue);
//        yellow = findViewById(R.id.yellow);
//        red = findViewById(R.id.red);
//        orange = findViewById(R.id.orange);
//        green = findViewById(R.id.green);
//        purple = findViewById(R.id.purple);
//
//        for (int i = 0; i < chipGroup.getChildCount(); i++) {
//            (Tag)
//        }

        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            ((Chip) chipGroup.getChildAt(i)).setOnClickListener(new Ecouteur());
        }

        imgTraceLibre = findViewById(R.id.traceLibre);
        imgEfface = findViewById(R.id.efface);


//
        imgTraceLibre.setOnClickListener(new Ecouteur());
        imgEfface.setOnClickListener(new Ecouteur());

//        black.setOnClickListener(new Ecouteur());
//        gray.setOnClickListener(new Ecouteur());
//        blue.setOnClickListener(new Ecouteur());
//        yellow.setOnClickListener(new Ecouteur());
//        red.setOnClickListener(new Ecouteur());
//        orange.setOnClickListener(new Ecouteur());
//        green.setOnClickListener(new Ecouteur());
//        purple.setOnClickListener(new Ecouteur());


    }

//    private class Surface extends View {
//        Paint crayon;
//
//        public Surface(Context context) {
//            super(context);
//            crayon = new Paint(Paint.ANTI_ALIAS_FLAG);
//            crayon.setColor(Color.RED);
//            crayon.setStyle(Paint.Style.STROKE);
//            crayon.setStrokeWidth(15);
//        }
//
//        @Override
//        protected void onDraw(@NonNull Canvas canvas) {
//            super.onDraw(canvas);
//            canvas.drawPath(path, crayon);
//
//        }
//
//    }

//
    private class Ecouteur implements View.OnClickListener{

        @Override
        public void onClick(View source) {

            LargeurTraitDialogue dialog = new LargeurTraitDialogue(MainActivity.this);
            dialog.show();

            if (source == black) {
                traceLibre.setCouleur(Color.BLACK);
            }
            if (source == gray) {
                 traceLibre.setCouleur(Color.GRAY);
            }
            if (source == blue) {
                traceLibre.setCouleur(Color.BLUE);
            }
            if (source == yellow) {
                traceLibre.setCouleur(Color.YELLOW);
            }
            if (source == red) {
                traceLibre.setCouleur(Color.RED);
            }
            if (source == orange) {
                traceLibre.setCouleur(Color.rgb(255,165,0));
            }
            if (source == green) {
                traceLibre.setCouleur(Color.GREEN);
            }
            if (source == purple) {
                traceLibre.setCouleur(Color.MAGENTA);
            }



            if (source == imgTraceLibre) {
                traceLibre = new TraceLibre(MainActivity.this);
                traceLibre.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
                dessin.add(traceLibre);
                parent.addView(traceLibre);

            }

            if (source == imgEfface) {
                efface = new Efface(MainActivity.this);
                traceLibre.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
                parent.addView(efface);
            }


        }

        // onSidebar -> essayer d'en extraire sa valeur apres
    }
}