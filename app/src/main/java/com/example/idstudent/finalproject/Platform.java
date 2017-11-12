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
    boolean on;
Paint paint;

    public Platform(int x, int y, int width) {
        this.x = x;
        this.y = y;
        this.width = width;
        paint = new Paint();
        paint.setColor(Color.BLACK);
    }
        public void checkCollision(Player player, Ground ground) {
        if(player.x + player.getWidth() > this.x - player.mapX && player.x + player.getWidth() < this.x + this.width - player.mapX) {
            if(player.y + player.getHeight() > this.y && player.oldY + player.getHeight() < this.y && player.x + player.getWidth() > this.x - player.oldMapX && player.x < this.x + this.width - player.oldMapX) {
                player.y = this.y - player.getHeight() + 10;
                player.ySpeed = 0;
                player.canJump = 1;
                ground.fall = false;
                on = true;
            }
        }
        else if(on == true){
            on = false;
            ground.fall = true;
        }

    }

    public void draw(Canvas canvas, Player player) {
        if(x - player.mapX + width > 0 && x - player.mapX < canvas.getWidth()) {
            canvas.drawRect(x - player.mapX, y, x + width - player.mapX, y - 10, paint);
        }
    }
    }



