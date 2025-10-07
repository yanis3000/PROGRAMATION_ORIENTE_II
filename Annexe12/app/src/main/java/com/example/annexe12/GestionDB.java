package com.example.annexe12;

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

    public boolean aBonneReponse(String invention, String nom) {
        String[] tab = {nom, invention};

        Cursor c = database.rawQuery("SELECT invention FROM inventeur WHERE nom = ? AND invention = ?", tab);
        //si vide, moveToFirst va retouner faux sinon ca retourne true
        boolean rep = c.moveToFirst();
        c.close();
        return rep; // ce n'est pas une bonne maniere de proceder

    }

    // trouver l'indice de la bonne reponse etant donne un nom d'inventeur

    public int trouverIndiceBonneReponse(String nom) throws Exception {
        String[] tab = {nom};
        Cursor c = database.rawQuery("SELECT _id FROM inventeur WHERE nom = ?", tab);
        if (c.moveToFirst()) {
            int rep = c.getInt(0) -  1; // les _id commencent a 0
            c.close();
            return rep;
        }
        else {
            throw new Exception("Le nom de l'inventeur n'est pas dans la table");
        }

    }

    public void fermerConnectionDB() {
        database.close();
    }

    public ArrayList<String> retournerInventions() {
        ArrayList<String> liste = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT invention FROM inventeur", null);
        while (c.moveToNext()) { // tant qu'il y a des resultats
            liste.add(c.getString(0)); // j'ajoute l'invention dans le vector, je veux tous les nommer
        }

//        if (c.moveToLast()) return c.getString(0); // scaphandre
        c.close();
        return liste;
    }

}
