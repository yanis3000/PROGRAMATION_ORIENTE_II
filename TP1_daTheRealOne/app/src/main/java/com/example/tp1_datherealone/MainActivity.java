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
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    LinearLayout parent;
    Surface s;
    android.graphics.Path path;


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

        s = new Surface(this);
        s.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        parent.addView(s);

        path = new Path();

        Ecouteur ec = new Ecouteur();

        s.setOnTouchListener(ec);

    }

    private class Surface extends View {
        Paint crayon;

        public Surface(Context context) {
            super(context);
            crayon = new Paint(Paint.ANTI_ALIAS_FLAG);
            crayon.setColor(Color.RED);
            crayon.setStyle(Paint.Style.STROKE);
            crayon.setStrokeWidth(15);
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawPath(path, crayon);

        }

    }

    private class Ecouteur implements View.OnTouchListener, View.OnClickListener {
        @Override
        public void onClick(View source) {
            // A mettre les boutons, mais faut faire les classes d'abord
        }

        @Override
        public boolean onTouch(View source, MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                path.moveTo(x, y);
                s.invalidate();
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                path.lineTo(x, y);
                s.invalidate();
            }

            return true;
        }
    }
}