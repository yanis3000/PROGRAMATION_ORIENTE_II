package com.example.tp1_datherealone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout parent;
    TraceLibre traceLibre;
    ImageView traceLibre2;


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
        traceLibre2 = findViewById(R.id.traceLibre);


//
           Ecouteur ec = new Ecouteur();
           traceLibre2.setOnClickListener(ec);

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
    private class Ecouteur extends MainActivity implements View.OnClickListener{

        @Override
        public void onClick(View source) {

            if (source == traceLibre2) {
                traceLibre = new TraceLibre(this);
                traceLibre.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
                parent.addView(traceLibre);
            }

        }
    }
}