package com.example.tp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GestionDB extends SQLiteOpenHelper {

    private static GestionDB instance;
    private SQLiteDatabase database;
    private Context context;
    private String scoreMax;


    public static GestionDB getInstance(Context contexte) {
        if ( instance == null ) {
            instance = new GestionDB(contexte);
        }
        return instance;
    }
    public GestionDB(@Nullable Context contexte) {
        super(contexte, "BD", null, 1);
        this.context = contexte.getApplicationContext();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE lexique (ortho varchar(80),`phon` varchar(80),`lemme` varchar(80),`cgram` " +
                "varchar(80),`genre` varchar(1),`nombre` varchar(1),`freqlemfilms` float,`freqlemlivres` float," +
                "`freqfilms` float,`freqlivres` float,`infover` varchar(80),`nbhomogr` int(11),`nbhomoph` int(11)," +
                "`islem` tinyint(1),`nblettres` int(11),`nbphons` int(11),`cvcv` varchar(80),`p_cvcv` varchar(80)," +
                "`voisorth` int(11),`voisphon` int(11),`puorth` int(11),`puphon` int(11),`syll` varchar(80),`nbsyll" +
                "` int(11),`cv_cv` varchar(80),`orthrenv` varchar(80)," +
                "`phonrenv` varchar(80),`orthosyll` varchar(80))");
        convertStreamToString(db);
        db.execSQL("CREATE TABLE score (scoreRonde INTEGER, temps TEXT)");
    }

    public void ajouterScore(Score i) {
        ContentValues cv = new ContentValues();
        cv.put("scoreRonde", i.getScore());

        // Source : https://www.w3schools.com/java/java_date.asp

        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String currentTime = myDateObj.format(myFormatObj);

        cv.put("temps", currentTime);

        database.insert("score", null, cv);
    }


    public String afficherScoreMax() {

        Cursor c = database.rawQuery("SELECT scoreRonde FROM score ORDER BY scoreRonde DESC LIMIT 1", null);
        if (c.moveToFirst()) {
            scoreMax = c.getString(0);
        }
        c.close();

        return scoreMax != null ? scoreMax : "Aucun score inscrit"; // operation ternaire pour indiq
    }

    public ArrayList<String> afficherScores() {
        ArrayList<String> liste = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT scoreRonde, temps FROM score ORDER BY scoreRonde DESC", null);
        while (c.moveToNext()) { // tant qu'il y a des resultats
            liste.add("SCORE : " + c.getString(0) + " | TIME : " + c.getString(1)); // j'ajoute l'invention dans le vector, je veux tous les nommer
        }
        c.close();

        return liste; // operation ternaire pour indiq
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS lexique");
        db.execSQL("DROP TABLE IF EXISTS score");
        onCreate(db);
    }

    public void ouvrirConnectionDB() {
        database = getWritableDatabase();
    }

    public void fermerConnectionDB() {
        database.close();
    }


    public boolean verifMot (String ortho) {
        String [] tab = {ortho};
        Cursor c = database.rawQuery("SELECT ortho FROM lexique WHERE ortho = ? ", tab);
        boolean rep = c.moveToFirst();
        c.close();
        return rep;
    }


    public void convertStreamToString( SQLiteDatabase db) {
        InputStream is = context.getResources().openRawResource(R.raw.data);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        try {
            while (br.ready()) {
                String insertStmt = br.readLine();
                db.execSQL(insertStmt);

            }
            br.close();
        }
        catch ( Exception e)
        {
            e.printStackTrace();
        }

    }
}
