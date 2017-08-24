package com.example.idstudent.finalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    MediaPlayer mp;
    Canvas canvas;
    GameThread thread;
    Player player;
    Random rand;
    Ground ground;
    int randomDraw;
    long aFrame;
    float downY;
    long frames;
    Paint paint;
    int pause;
    ArrayList<Cloud> cloudList;
    Point point1;
    Point point2;
    Point point3;
    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        setDrawingCacheEnabled(true);
        thread = new GameThread(getHolder(), this);
         mp = MediaPlayer.create(context, R.raw.theme);
        mp.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        rand = new Random();
        player = new Player(getWidth()/3, 0, getHeight(), getWidth(), ground);
        player.runBitmap = Util.getResizedBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.run_burned),100,150);
        player.standBitmap = Util.getResizedBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.stand ),100,150);
        Bitmap temporary = BitmapFactory.decodeResource(getResources(),R.drawable.good_grass);
        Ground.grassBitmap = Util.getResizedBitmap(temporary, temporary.getWidth()/10, temporary.getHeight()/10);
        ground = new Ground();
        ground.initializeDraw(getWidth(),getHeight());
        thread.running = true;
        thread.start();
        cloudList = new ArrayList<Cloud>();

    }
    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        paint = new Paint();
        paint.setColor(Color.GRAY);
        int times = 0;
        frames++;
        canvas.drawColor(Color.rgb(128, 191, 255));
        for(int i = 0; i < cloudList.size(); i++) {
            cloudList.get(i).draw(canvas);
        }
        ground.draw(canvas, player);

        this.buildDrawingCache();

        player.draw(canvas, ground);
        if(player.mapX+10000>ground.generatedDistance) {
            ground.generateTerrain(getWidth(), getHeight(), player, canvas);
        }
        randomDraw = rand.nextInt(1000);
        if(randomDraw > 997) {
            cloudList.add(new Cloud(Util.getResizedBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.cloud1),rand.nextInt(300) + 300,rand.nextInt(100) + 100)));

        }

        if(times<1) {
            Cloud.canvas = canvas;
        }
        times++;
        canvas.drawRect(canvas.getWidth() - 80, 50, canvas.getWidth() - 50, 150, paint);
        canvas.drawRect(canvas.getWidth() - 140, 50, canvas.getWidth() - 110, 150, paint);
     if(pause == 1) {
         Util.drawTriangle(canvas.getWidth() / 3, canvas.getHeight() / 1000 * 115, canvas.getWidth() / 3 * 2, canvas.getHeight() / 2, canvas.getWidth() / 3, canvas.getHeight() / 1000 * 885, canvas);
         }
     }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchDown(event);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                touchDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(event);
                break;
            case MotionEvent.ACTION_UP:
                touchUp(event);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                touchUp(event);
                break;
            case MotionEvent.ACTION_CANCEL:
                touchUp(event);
                break;
            default:
        }
        return true;
    }

    void touchDown(MotionEvent event) {
        if(thread.running == true) {
            downY = event.getY();
            aFrame = frames;

            if (event.getX() > getWidth() / 9 * 5) {
                player.setRun(10);
            } else if (event.getX() < getWidth() / 9 * 4) {
                player.setRun(-10);
            }
            if (event.getY() < 150 && event.getX() > canvas.getWidth() - 140) {
                stop();
            }
        }
        else {
            if(Util.detectHitbox(point1, point2, point3, new Point((int)event.getX(), (int)event.getY()))) {
                thread.running = true;
                pause = 0;
            }
        }
    }
    void touchMove(MotionEvent event) {

    }
    void touchUp(MotionEvent event) {
        if(thread.running == true) {
            player.setRun(0);
            if (
                    downY - event.getY() > 100 &&
                            frames - aFrame < 30

                    ) {
                if(ground.oceanTrue == 0 && player.canJump != 0 ) {
                    player.jump();
                    player.canJump = 0;
                }
              if(ground.oceanTrue == 1 &&  player.y + player.getHeight() > ground.oceanArray.get(0).seaLevel) {
                player.swimUp();
              }
            }
            player.xSpeed = 0;
        }
    }

public void stop() {
    thread.running = false;
    point1 = new Point(canvas.getWidth()/3, canvas.getHeight()/1000*115);
    point2 = new Point(canvas.getWidth()/3*2, canvas.getHeight()/2);
    point3 = new Point(canvas.getWidth()/3, canvas.getHeight()/1000*885);
    Util.drawTriangle(point1.x, point1.y, point2.x, point2.y, point3.x, point3.y, canvas);
    pause = 1;
}
}

