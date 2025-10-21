package com.example.annexe13;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GestionDB extends SQLiteOpenHelper {
    private static GestionDB instance;
    private SQLiteDatabase database; // un moyen de l'obtenir APRES avoir ouvert l'activite

    public static GestionDB getInstance(Context contexte) {
        if ( instance == null ) {
            instance = new GestionDB(contexte);
        }
        return instance;
    }

    private GestionDB(@Nullable Context context) {
        super(context, "BD", null, 1);
    }

    /* ATTENTION APPELER UNE SEULE FOIS LORS DE L'INSTALLATION DE L'APP SUR L'APPAREIL */

    @Override
    public void onCreate(SQLiteDatabase db) { // important : EXECUTE UNE SEULE FOIS LORS DE L'INSTALLATION DE L'APP
        db.execSQL("CREATE TABLE evaluation ( _id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, microbrasserie TEXT, etoiles INTEGER)");
//        ajouterBiere(new Evaluation("Coors Light", "nahh", 1), db);
//        ajouterBiere(new Evaluation("H20", "Terre", 3), db);
//        ajouterBiere(new Evaluation("Essence", "Terre", 5), db);
    }

    public void ajouterBiere(Evaluation i /*SQLiteDatabase db*/) {
        ContentValues cv = new ContentValues();
        cv.put("nom", i.getNom());
        cv.put("microbrasserie", i.getMicrobrasserie());
        cv.put("etoiles", i.getEtoiles());
        database.insert("evaluation", null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS evaluation");
        onCreate(db);
    }

    public void ouvrirConnectionDB() {
        database = getWritableDatabase();
    }

    public void fermerConnectionDB() {
        database.close();
    }

    public ArrayList<String> retournerMeilleurs() /*throws Exception*/ {
        ArrayList<String> liste = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT nom FROM evaluation ORDER BY etoiles DESC LIMIT 3", null);
        while (c.moveToNext()) { // tant qu'il y a des resultats
            liste.add(c.getString(0)); // j'ajoute l'invention dans le vector, je veux tous les nommer
        }

//        if (c.moveToLast()) return c.getString(0); // scaphandre

        c.close();
//
//        if (liste.size() < 3) {
//            throw new Exception("Moins de 3 enregistrements");
//        }
        return liste;
    }

}
