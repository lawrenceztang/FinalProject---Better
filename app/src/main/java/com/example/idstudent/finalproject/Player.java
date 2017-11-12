package com.example.idstudent.finalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;


public class Player {

    public Bitmap runBitmap;
    public Bitmap standBitmap;
    public Bitmap checkBitmap;
    int ticker;
    int xSpeed;
    int ySpeed;
    int running;
    int mapX;
    public Bitmap flipRun;
    int y;
    int x;
    int score;
    final int fallingSpeed = 20;
    int canvasHeight;
    Ground ground;
    int canvasWidth;
    int fallingAcceleration = 1;
    boolean jumping;
    int canJump;
    int jumpingTicker;
    ArrayList<Integer> checkPoints;
    int currentCheckPoint;
    int swimming;
    int swimmingTick;
    int oldY;
    int oldMapX;
    int olderY;
    int olderMapX;
    int stopX;
    int stopY;

    public Player(int x, int y, int canvasHeight, int canvasWidth, Ground ground) {
        this.x = x;
        this.y = y;
        this.canvasHeight=canvasHeight;
        this.canvasWidth=canvasWidth;
        mapX = 0;
        ground = ground;
        checkPoints = new ArrayList<Integer>();
        int addCheckpoints = 0;
        int numToAdd = 0;
        for(int i = 0; i < 100; i++) {

            checkPoints.add(addCheckpoints);
                    numToAdd += 1000;
                    addCheckpoints += numToAdd;
        }
    }

    public void jump() {
        if (canJump > 0) {
            jumping = true;
            jumpingTicker = 0;
        }
    }
    public void swimUp() {

        swimming = 1;
        swimmingTick = 0;
    }

    public void draw(Canvas canvas, Ground ground) {
        Matrix m = new Matrix();
        if(running != 0) {
            if(ground.oceanTrue == 1 &&  y + getHeight() > ground.oceanArray.get(0).seaLevel) {
                if (ticker % 10 > 5) {
                    if (xSpeed < 0) {
                        m.postScale(-1, 1, runBitmap.getWidth() / 2, runBitmap.getHeight());
                        m.postTranslate(x, y);
                        canvas.drawBitmap(Util.rotateBitmap(standBitmap, 70), m, null);
                    }
                    else {
                        canvas.drawBitmap(Util.rotateBitmap(runBitmap, 70), x, y, null);
                    }
                }
                else {
                    if (xSpeed < 0) {
                        m.postScale(-1, 1, standBitmap.getWidth() / 2, standBitmap.getHeight());
                        m.postTranslate(x, y);
                        canvas.drawBitmap(Util.rotateBitmap(standBitmap, 70), m, null);
                    }
                    else {
                        canvas.drawBitmap(Util.rotateBitmap(standBitmap, 70), x, y, null);
                    }
                }

            }
            else {
                if (ticker % 10 > 5) {
                    if (xSpeed < 0) {
                        m.postScale(-1, 1, runBitmap.getWidth() / 2, runBitmap.getHeight());
                        m.postTranslate(x, y);
                    } else {
                        m.postScale(1, 1, runBitmap.getWidth() / 2, runBitmap.getHeight());
                        m.postTranslate(x, y);
                    }
                    canvas.drawBitmap(runBitmap, m, null);
                } else {
                    if (xSpeed < 0) {
                        m.postScale(-1, 1, standBitmap.getWidth() / 2, standBitmap.getHeight());
                        m.postTranslate(x, y);
                    } else {
                        m.postScale(1, 1, standBitmap.getWidth() / 2, standBitmap.getHeight());
                        m.postTranslate(x, y);
                    }
                    canvas.drawBitmap(standBitmap, m, null);
                }
            }
        }
        else {
            canvas.drawBitmap(standBitmap, x, y, null);
        }

        stopX = ground.checkCollisionX(this, canvas);
        stopY = ground.checkCollisionY(this, canvas);

        if(stopY > 0) {
            this.y = stopY;
            ySpeed = 0;
        }
        else if(stopY == 0){
            if(ground.fall) {
                this.ySpeed = this.ySpeed + fallingAcceleration;

            }

        }
        if(jumping) {
            if(jumpingTicker == 0) {
                ySpeed = -20;
            }
            if(jumpingTicker > 5) {
                jumping = false;
            }
            else {
            }
            jumpingTicker++;
        }
        if(score<mapX){score = mapX;}
        Paint scoreColor = new Paint();
        setTextSizeForWidth(scoreColor, 100, Integer.toString(score/300));
        canvas.drawText(Integer.toString(score/300), 20, 200, scoreColor);
        mapX = mapX - stopX;
        mapX += xSpeed;
        ticker++;
        this.y = this.y + this.getYSpeed();
        if(this.y > canvas.getHeight()) {
        this.mapX = currentCheckPoint - x;
            this.y = 0;
        }
        for(int i = 0; i < checkPoints.size(); i++) {
            if(this.mapX + x > checkPoints.get(i) && this.mapX + x > currentCheckPoint) {
                currentCheckPoint = checkPoints.get(i);
            }
        }
        if(swimming == 1) {
            if(swimmingTick > 10) {
                swimming = 0;
            }
            else {
                this.y -= 5;
                swimmingTick++;
            }
        }
        for(int u = 0; u < checkPoints.size(); u++) {
            for(int o = 0; o < ground.shapes.size(); o++) {
                if (ground.shapes.get(o).x < checkPoints.get(u) && ground.shapes.get(o).x + ground.shapes.get(o).shapeWidth > checkPoints.get(u)) {
                    if(ground.shapes.get(o).shapeHeight > 5000) {
                        checkPoints.set(u, ground.shapes.get(o).shapeHeight + ground.shapes.get(o).x);
                    }
                }
            }
        }
if(ground.oceanTrue == 1) {
    y += 5;
}

oldY = olderY;
        oldMapX = olderMapX;
        olderMapX = mapX;
        olderY = y;

    }

    public int getWidth() {
        return standBitmap.getWidth();
    }

    public int getHeight() {
        return standBitmap.getHeight();
    }

    public void setRun(int i) { running = i;
    xSpeed = running;}

    public int getYSpeed() {if(ySpeed > -50 && ySpeed < 50) {return ySpeed;}
    if(ySpeed > 50) {
        return 50;
    }

        if(ySpeed < -50) {
            return -50;
        }
        return 0;

    }

    private static void setTextSizeForWidth(Paint paint, float desiredWidth,
                                            String text) {
        final float testTextSize = 48f;
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        float desiredTextSize = testTextSize * desiredWidth / bounds.width();
        paint.setTextSize(desiredTextSize);
    }

    public void drawCheckpoint(Canvas canvas) {
        for(int i = 0; i < checkPoints.size(); i++) {
            if(checkPoints.get(i) + checkBitmap.getWidth() - mapX > 0 && checkPoints.get(i) - mapX < canvasWidth) {
                canvas.drawBitmap(checkBitmap, checkPoints.get(i) - mapX, 100, new Paint());
            }
        }
    }

}
