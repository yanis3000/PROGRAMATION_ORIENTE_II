package com.example.tp2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GestionDB extends SQLiteOpenHelper {

    private static GestionDB instance;
    private SQLiteDatabase database;
    private Context context;

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS lexique");
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
