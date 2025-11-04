package com.example.tp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ScoreDB extends SQLiteOpenHelper {
    private static ScoreDB instance;
    private SQLiteDatabase database;
    private String scoreMax;

    public static ScoreDB getInstance(Context contexte) {
        if ( instance == null ) {
            instance = new ScoreDB(contexte);
        }
        return instance;
    }

    private ScoreDB(@Nullable Context context) {
        super(context, "BD", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
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

    public String afficherScores() {
        Cursor c = database.rawQuery("SELECT scoreRonde FROM score ORDER BY scoreRonde DESC", null);
        if (c.moveToFirst()) {
            scoreMax = c.getString(0);
        }
        c.close();

        return scoreMax != null ? scoreMax : "Aucun score inscrit"; // operation ternaire pour indiq
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS score");
        onCreate(db);
    }

    public void fermerConnectionDB() {
        database.close();
    }

    public void ouvrirConnectionDB() {
        database = getWritableDatabase();
    }







}
