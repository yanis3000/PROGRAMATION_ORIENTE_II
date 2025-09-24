package com.example.tp1_datherealone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

public class TraceLibre extends SuperPaint {

    public TraceLibre(Context context) {
        super(context);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

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