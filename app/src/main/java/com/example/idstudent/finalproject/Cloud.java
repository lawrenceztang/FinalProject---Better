package com.example.idstudent.finalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

public class Cloud {
    int y;
    int x;
    int speed;
    Random rand = new Random();
    int randomCloud;
    int randomDraw;
    Bitmap cloud1;
    static Canvas canvas;


    public Cloud(Bitmap cloud1) {
        randomCloud = rand.nextInt(3);
        y = rand.nextInt(canvas.getHeight()/2);
        x = canvas.getWidth();
        this.cloud1 = cloud1;
    }

    public void draw(Canvas canvas) {
        if(y + cloud1.getWidth() > 0 && y < canvas.getWidth()) {
            canvas.drawBitmap(cloud1, x, y, null);
            x = x - 1;
        }

    }
}
