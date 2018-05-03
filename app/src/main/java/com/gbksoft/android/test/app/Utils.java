package com.gbksoft.android.test.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class Utils {

  public static final int DEFAULT_ZOOM = 15;

  public static BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId, int dipSize) {
    Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
    int size = dipToPixels(context, dipSize);
    vectorDrawable.setBounds(0, 0, size, size);
    Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    vectorDrawable.draw(canvas);
    context = null;
    return BitmapDescriptorFactory.fromBitmap(bitmap);
  }

  public static BitmapDescriptor createClusterIcon(Context context, int clusterItemsCount) {
    String text = String.valueOf(clusterItemsCount);
    int drawableId = R.drawable.ic_cluster;

    int size = dipToPixels(context, 24);
    Bitmap bitmap = bitmapFromVector(context, drawableId);
    Canvas canvas = new Canvas(bitmap);
    Paint paint = new Paint();
    paint.setColor(Color.RED);
    paint.setTextSize(size);
    //Center the text into canvas
    Rect rect = canvas.getClipBounds();
    int clipWidth = rect.width();
    int clipHeight = rect.height();
    paint.setTextAlign(Paint.Align.LEFT);
    paint.getTextBounds(text, 0, text.length(), rect);
    float x = clipWidth / 2f - rect.width() / 2f - rect.left;
    float y = clipHeight / 2f + rect.height() / 2f - rect.bottom;
    //draw centered text
    canvas.drawText(String.valueOf(clusterItemsCount), x, y, paint);
    context = null;
    return BitmapDescriptorFactory.fromBitmap(bitmap);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public static Bitmap bitmapFromVector(Context context, int drawableId) {
    Drawable drawable = ContextCompat.getDrawable(context, drawableId);
    Bitmap result;
    if(drawable instanceof BitmapDrawable) {
      result = BitmapFactory.decodeResource(context.getResources(), drawableId);
    } else if(drawable instanceof VectorDrawable) {
      result = getBitmap((VectorDrawable) drawable);
    } else {
      throw new IllegalArgumentException("unsupported drawable type");
    }
    context = null;
    return result;
  }

  private static Bitmap getBitmap(VectorDrawable vectorDrawable) {
    Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(),
        Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
    vectorDrawable.draw(canvas);
    return bitmap;
  }

  public static int dipToPixels(Context context, int dipValue) {
    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
    context = null;
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
  }
}