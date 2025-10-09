package com.example.tpf;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SingletonBD extends SQLiteOpenHelper {
    private static SingletonBD instance;
    private Context contexte;
    private SQLiteDatabase database;

    public static SingletonBD getInstance(Context contexte) {
        if ( instance == null ) {
            instance = new SingletonBD(contexte);
        }
        return instance;
    }

    private SingletonBD(@Nullable Context contexte) {
        super(contexte, "tpf", null, 1);
        this.contexte = contexte;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE lexique (ortho varchar(80),`phon` varchar(80),`lemme` varchar(80),`cgram` varchar(80),`genre` varchar(1),`nombre` varchar(1),`freqlemfilms` float,`freqlemlivres` float,`freqfilms` float,`freqlivres` float,`infover` varchar(80),`nbhomogr` int(11),`nbhomoph` int(11),`islem` tinyint(1),`nblettres` int(11),`nbphons` int(11),`cvcv` varchar(80),`p_cvcv` varchar(80),`voisorth` int(11),`voisphon` int(11),`puorth` int(11),`puphon` int(11),`syll` varchar(80),`nbsyll` int(11),`cv_cv` varchar(80),`orthrenv` varchar(80),`phonrenv` varchar(80),`orthosyll` varchar(80))");
        convertStreamToString(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    public void convertStreamToString( SQLiteDatabase db) {
        InputStream is = contexte.getResources().openRawResource(R.raw.data);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);// pour que le flux soit multiple
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

    public void ouvrirConnectionDB() {
        database = getWritableDatabase();
    }

    public void fermerConnectionDB() {
        database.close();
    }

    public int compterMots() {
        int rep = 0;
        Cursor c = database.rawQuery("SELECT COUNT(*) FROM lexique", null);
        if (c.moveToFirst()) {
            rep = c.getInt(0); // les _id commencent a 0
            c.close();
        }
        return rep;
    }


}
