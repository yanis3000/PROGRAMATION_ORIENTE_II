package com.example.tp1_datherealone;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.view.MotionEvent;

public class Efface extends SuperPaint{

    public Efface(Context context) {
        super(context);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        setCouleur(Color.rgb(238, 238, 238));

        float x = event.getX();
        float y = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN ) {
            path.moveTo(x, y);
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            path.lineTo(x, y);
        }

        invalidate();

        return true;
    }







}