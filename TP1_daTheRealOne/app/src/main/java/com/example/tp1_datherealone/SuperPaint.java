package com.example.tp1_datherealone;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;

public class SuperPaint {

    Paint crayon;
    Path path;
    Surface s;
    Ecouteur ec = new Ecouteur();

    public SuperPaint(Paint crayon, Path path) {
        this.crayon = crayon;
        this.path = path;
    }

    private class Ecouteur implements View.OnTouchListener, View.OnClickListener {
        @Override
        public void onClick(View source) {

        }

        @Override
        public boolean onTouch(View source, MotionEvent event) {

            return true;
        }
    }


}
