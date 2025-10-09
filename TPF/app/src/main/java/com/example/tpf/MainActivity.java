package com.example.tpf;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    SingletonBD instance;



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

        instance = SingletonBD.getInstance(getApplication()); // cas pour les singleton
        instance.ouvrirConnectionDB();

        Toast.makeText(this, instance.compterMots() + " mots", Toast.LENGTH_LONG).show(); // dans le contexte du toast, on laisse this

        instance.fermerConnectionDB(); // A enlever apres

        // View, Tool Windows, App Inspection -> on devrait le voir en bas

    }
}