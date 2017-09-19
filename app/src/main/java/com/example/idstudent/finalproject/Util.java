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

    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static boolean detectHitbox(Point a, Point b, Point c, Point mouse) {
            double slope1 = ((double)a.y - (double)b.y) / ((double)a.x - (double)b.x);
            double slope2 = ((double)b.y - (double)c.y) / ((double)b.x - (double)c.x);
            double slope3 = ((double)c.y - (double)a.y) / ((double)c.x - (double)a.x);
            double intercept1 = a.y - slope1 * a.x;
            double intercept2 = b.y - slope2 * b.x;
            double intercept3 = c.y - slope3 * c.x;
        if(mouse.y < mouse.x * slope1 + intercept1 && c.y < c.x * slope1 + intercept1 || mouse.y > mouse.x * slope1 + intercept1 && c.y > c.x * slope1 + intercept1) {

        }
        else {return false;}

        if(mouse.y < mouse.x * slope2 + intercept2 && a.y < a.x * slope2 + intercept2 || mouse.y > mouse.x * slope2 + intercept2 && a.y > a.x * slope2 + intercept2) {

        }
        else {return false;}

        if(mouse.y < mouse.x * slope3 + intercept3 && b.y < b.x * slope3 + intercept3 || mouse.y > mouse.x * slope3 + intercept3 && b.y > b.x * slope3 + intercept3) {

        }
        else {return false;}

            return true;
        }
}
