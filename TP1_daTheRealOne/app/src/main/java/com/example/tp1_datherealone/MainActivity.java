package com.example.tp1_datherealone;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout parent;
    TraceLibre traceLibre;
    Efface efface;
    ImageView imgTraceLibre;
    ImageView imgEfface;
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

        imgTraceLibre = findViewById(R.id.traceLibre);
        imgEfface = findViewById(R.id.efface);


//
           imgTraceLibre.setOnClickListener(new Ecouteur());
           imgEfface.setOnClickListener(new Ecouteur());

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
    }
}