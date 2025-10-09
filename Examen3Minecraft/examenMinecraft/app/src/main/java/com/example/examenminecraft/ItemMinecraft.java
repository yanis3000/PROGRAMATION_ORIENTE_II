package com.example.examenminecraft;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class ItemMinecraft {
    private String type;
    private int couleur;
    private int largeur;

    public ItemMinecraft(String type, int couleur, int largeur)
    {
        this.type = type;
        this.couleur = couleur;
        this.largeur = largeur;
    }

    public void dessiner ( Canvas c ) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(couleur);
        p.setStrokeWidth(largeur);

        if (type.equals( "Bambou")) {

            Path pa = new Path();
            pa.moveTo(500, 20);
            pa.lineTo(500, 420);
            c.drawPath(pa, p);
        } else if (type.equals("Echelle")) {


            Path pa = new Path();
            pa.moveTo(450, 20);
            pa.lineTo(450, 480);
            c.drawPath(pa, p);
            Path pa2 = new Path();
            pa2.moveTo(600, 20);
            pa2.lineTo(600, 480);
            c.drawPath(pa2, p);
            for  ( int i = 0 ; i< 18; i++ )
            {
               c.drawLine(450, 40+25*i, 600, 40+25*i, p) ;
            }
        }
        else // champignon
        {
            p.setStyle(Paint.Style.FILL);
            c.drawOval(300,100, 700, 400, p);
            c.drawRect(300, 250, 700, 395, p);
            c.drawRect(470, 360, 535, 620, p);

        }



    }
}
