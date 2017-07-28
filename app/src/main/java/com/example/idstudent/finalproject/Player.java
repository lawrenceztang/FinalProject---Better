package com.example.idstudent.finalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;


public class Player {

    public Bitmap runBitmap;
    public Bitmap standBitmap;
    int ticker;
    int xSpeed;
    int ySpeed;
    int running;
    int mapX;
    public Bitmap flipRun;
    int y;
    int x;
    final int fallingSpeed = 20;
    int canvasHeight;
    Ground ground;
    int canvasWidth;
    int jumpingAcceleration = -4;
    int fallingAcceleration = 2;
    boolean jumping;
    int canJump;
    int jumpingTicker;
    public Player(int x, int y, int canvasHeight, int canvasWidth, Ground ground) {
        this.x = x;
        this.y = y;
        this.canvasHeight=canvasHeight;
        this.canvasWidth=canvasWidth;
        mapX = 0;
        ground = ground;

    }

    public void jump() {
        if (canJump > 0) {
            jumping = true;
            ySpeed = 0;
            jumpingTicker = 0;
        }
    }

    public void draw(Canvas canvas, Ground ground) {
        Matrix m = new Matrix();
        if(running == 1) {
            if (ticker % 10 > 5) {
                if(xSpeed < 0) {
                    m.postScale(-1, 1,runBitmap.getWidth() / 2 , runBitmap.getHeight());
                    m.postTranslate(x,y);
                } else {
                    m.postScale(1, 1,runBitmap.getWidth() / 2 , runBitmap.getHeight());
                    m.postTranslate(x,y);
                }
                canvas.drawBitmap(runBitmap, m, null);
            } else {
                if(xSpeed < 0) {
                    m.postScale(-1, 1,standBitmap.getWidth() / 2 , standBitmap.getHeight());
                    m.postTranslate(x,y);
                } else {
                    m.postScale(1, 1,standBitmap.getWidth() / 2 , standBitmap.getHeight());
                    m.postTranslate(x,y);
                }
                canvas.drawBitmap(standBitmap, m, null);
            }
        }
        else {
            canvas.drawBitmap(standBitmap, x, y, null);
        }

        int stopX = ground.checkCollisionX(this, canvas);
        int stopY = ground.checkCollisionY(this, canvas);
        if(stopY > 0) {
            this.y = stopY;
        }
        else if(stopY == 0){
            if(ground.fall) {
                this.ySpeed = this.ySpeed + fallingAcceleration;
                this.y = this.y + this.getYSpeed();
            }

        }
        if(jumping) {
            if(jumpingTicker > 5) {
                jumping = false;
                ySpeed = 0;
            }
            else {
                ySpeed = -20;
                this.y = this.y + ySpeed;
            }
            jumpingTicker++;
        }
        Paint scoreColor = new Paint();
        scoreColor.setColor(Color.BLUE);
        canvas.drawText(Integer.toString(mapX), 20, 20, scoreColor);
        mapX = mapX - stopX;
        mapX = xSpeed + mapX;
        ticker++;
    }

    public int getWidth() {
        return standBitmap.getWidth();
    }

    public int getHeight() {
        return standBitmap.getHeight();
    }

    public void setRun(int i) { running = i; }

    public int getYSpeed() {if(ySpeed > -50 && ySpeed < 50) {return ySpeed;}
    if(ySpeed > 50) {
        return 50;
    }

        if(ySpeed < -50) {
            return -50;
        }
        return 0;

    }



}
