package com.example.tp1_datherealone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

public class SuperPaint extends View {

    protected Paint crayon;
    protected Path path;

    protected float epaisseur = 15;
    protected int couleur = Color.BLACK;




    public SuperPaint(Context context) {
        super(context);
        crayon = new Paint(Paint.ANTI_ALIAS_FLAG);
        crayon.setColor(couleur);
        crayon.setStyle(Paint.Style.STROKE);
        crayon.setStrokeWidth(epaisseur);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, crayon);
    }

    public void setCouleur(int newColor) {
        couleur = newColor;
        crayon.setColor(couleur);
        invalidate();
    }

    public void setEpaisseur(float newWidth) {
        epaisseur = newWidth;
        crayon.setStrokeWidth(epaisseur);
        invalidate();
    }

}