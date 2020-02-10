package com.gabrielmiguelpedro.maclarenapp.Assets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public abstract class BitmapUtils {
    /**
     * Método que converte uma imagem vectorial para bitmap.
     * @param context
     * @param vectorResId
     * @param width
     * @param height
     * @return
     */
    public static BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId,
                                                              int width, int height) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, width, height);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
