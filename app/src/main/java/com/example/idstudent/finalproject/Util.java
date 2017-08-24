package com.example.idstudent.finalproject;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

public class Util {

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public static void drawTriangle(int x, int y, int x2, int y2, int x3, int y3, Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setColor(android.graphics.Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);

        Point a = new Point(x, y);
        Point b = new Point(x2, y2);
        Point c = new Point(x3, y3);

        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(b.x, b.y);
        path.lineTo(c.x, c.y);
        path.lineTo(a.x, a.y);
        path.lineTo(b.x, b.y);
        path.close();

        canvas.drawPath(path, paint);
    }

    public static boolean detectHitbox(Point a, Point b, Point c, Point mouse) {
       int slope1 = (a.y - b.y)/(a.x - b.x);
        int slope2 = (b.y - c.y)/(b.x - c.x);
        int slope3 = (c.y - a.y)/(c.x - a.x);
        int intercept1 = a.y - slope1 * a.x;
        int intercept2 = b.y - slope2 * b.x;
        int intercept3 = c.y - slope3 * c.x;

        for(int i = 0; i < 9999; i++) {
            if((mouse.y > i * slope1 + intercept1 && b.y > i * slope1 + intercept1) || (mouse.y < i * slope1 + intercept1 && b.y < i * slope1 + intercept1)) {

            }
            else {
                return false;
            }
        }
        for(int i = 0; i < 9999; i++) {
            if((mouse.y > i * slope2 + intercept2 && c.y > i * slope2 + intercept2) || (mouse.y < i * slope2 + intercept2 && c.y < i * slope2 + intercept2)) {

            }
            else {
                return false;
            }
        }
        for(int i = 0; i < 9999; i++) {
            if((mouse.y > i * slope3 + intercept3 && a.y > i * slope3 + intercept3) || (mouse.y < i * slope3 + intercept3 && a.y < i * slope3 + intercept3)) {

            }
            else {
                return false;
            }
        }
        return true;
    }

}