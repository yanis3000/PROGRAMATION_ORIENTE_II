package com.example.annexe12;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class OperationBD extends SQLiteOpenHelper {
    private static OperationBD instance;
    private SQLiteDatabase database; // un moyen de l'obtenir APRES avoir ouvert l'activite

    public static OperationBD getInstance( Context contexte) {
        if ( instance == null ) {
            instance = new OperationBD(contexte);
        }
        return instance;
    }

    private OperationBD(@Nullable Context context) {
        super(context, "BD", null, 1);
    }

    /* ATTENTION APPELER UNE SEULE FOIS LORS DE L'INSTALLATION DE L'APP SUR L'APPAREIL */

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE inventeur ( _id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, origine TEXT, invention TEXT, annee INTEGER)");
        ajouterInventeur(new Inventeur("Laszlo Biro", "Hongrie", "Stylo a bille", 1938), db);
        ajouterInventeur(new Inventeur("Benjamin Franklin", "USA", "Paratonnerre", 1752), db);
        ajouterInventeur(new Inventeur("Mary Anderson", "USA", "Essuie-glace", 1903), db);
        ajouterInventeur(new Inventeur("Grace Hopper", "USA", "Compilateur", 1952), db);
        ajouterInventeur(new Inventeur("Benoit Rouquayrot", "France", "Scaphandre", 1864), db);
    }

    public void ajouterInventeur(Inventeur i, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put("nom", i.getNom());
        cv.put("origine", i.getOrigine());
        cv.put("invention", i.getInvention());
        cv.put("annee", i.getAnnee());
        db.insert("inventeur", null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS inventeur");
        onCreate(db);
    }

    public void ouvrirConnectionDB() {
        database = getWritableDatabase();
    }

    public void fermerConnectionDB() {
        database.close();
    }

    public ArrayList<String> retournerInventions() {
        ArrayList<String> liste = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT invention FROM inventeur", null);
        while (c.moveToFirst()) {
            liste.add(c.getString(0));
        }
        return liste;
    }

}
