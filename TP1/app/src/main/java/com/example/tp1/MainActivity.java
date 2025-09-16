package com.example.tp1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout parent;
    Surface s;

    Path path;

    TextView x;
    TextView y;
    Button valider;

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
        x = findViewById(R.id.moveTo);
        y = findViewById(R.id.lineTo);
        valider = findViewById(R.id.valider);


        s = new Surface(this);
        s.setLayoutParams(new ConstraintLayout.LayoutParams(-1, -1));
        parent.addView(s);

        path = new Path();

        Ecouteur ec = new Ecouteur();

        valider.setOnClickListener(ec);

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

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View source) {
            if (path.isEmpty()) {
                path.moveTo(Integer.parseInt(x.getText().toString()), Integer.parseInt(y.getText().toString()));
            }
            else {
                path.lineTo(Integer.parseInt(x.getText().toString()), Integer.parseInt(y.getText().toString()));
            }
            s.invalidate();
        }
    }
}
