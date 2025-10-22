//package com.example.tp2;
//
//// vu que c'est uen classe interne, on peut creer des objets de cette classe, la palcer static dit simplement au compilateur
//// que cette classe est totalement indépendante de la classe englobante, si pas static, la classe interne a une référence implicite à la classe englobante
//// ce qui peut créer des pertes de mémoire ( memory leaks )
//
//import android.graphics.Canvas;
//import android.graphics.Point;
//import android.view.View;
//
//private static class ShadowInvisible extends View.DragShadowBuilder {
//
//    @Override
//    public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
//        // tout petit
//        outShadowSize.set(1, 1);
//        outShadowTouchPoint.set(0, 0);
//    }
//
//    @Override
//    public void onDrawShadow(Canvas canvas) {
//        // rien faire, on ne dessine rien
//    }
//
//}
