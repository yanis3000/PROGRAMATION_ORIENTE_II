package com.example.annexe4b;

import static java.lang.Integer.parseInt;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {




    LinearLayout yeet;
    TextView textView;

    String value = "";

    int digit;
    String password = "1414";



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

        // Initialisation des composants

        yeet = findViewById(R.id.yeet);
        textView = findViewById(R.id.textView);

        Ecouteur ec = new Ecouteur();


        for (int i = 0; i < yeet.getChildCount(); i++) {
            if ( yeet.getChildAt(i) instanceof LinearLayout) {
                LinearLayout skibidi = (LinearLayout) yeet.getChildAt(i);
                for (int j = 0; j < skibidi.getChildCount(); j++) {
                    if (skibidi.getChildAt(j) instanceof Button) { // Vu qu'on a des spaces et pas
                        skibidi.getChildAt(j).setOnClickListener(ec);
                    }
                }
            }
        }


    }

    private class Ecouteur implements View.OnClickListener {
    @Override
        public void onClick(View source) {

        if (textView.length() < 4) {
            Button b = (Button) source;
            digit = Integer.parseInt(b.getText().toString());
            value += digit;
            textView.setText(value);
        }
        if (textView.length() == 4) {
            if (textView.getText().toString().equals(password)) {
                yeet.setBackgroundColor(Color.GREEN);
            }
            else {
                yeet.setBackgroundColor(Color.RED);
            }
            textView.setText("");
            value = "";
        }



        }
    }
}