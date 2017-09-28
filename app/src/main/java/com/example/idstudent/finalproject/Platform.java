package com.example.idstudent.finalproject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by iD Student on 7/25/2017.
 */

public class Platform {

    int x;
    int y;
    int width;
    int height;
Paint paint;
    public Platform(int x, int y, int width) {
        this.x = x;
        this.y = y;
        this.width = width;
        paint = new Paint();
        paint.setColor(Color.BLACK);
    }
        public void checkCollision(Player player, Canvas canvas) {
        if(player.mapX > this.x && player.mapX < this.x + this.width) {
            if(player.y < this.y && player.ySpeed < 0) {
                player.y += 10;
            }
        }

    }

    public void draw(Canvas canvas, Player player) {
        if(x - player.mapX + width > 0 && x - player.mapX < canvas.getWidth()) {
            canvas.drawRect(x - player.mapX, y, x + width - player.mapX, y - 10, paint);
        }
    }
    }



