package com.example.idstudent.finalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    GameThread thread;
    Player player;
    Ground ground;
    long aFrame;
    float downY;
    long frames;
    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        setDrawingCacheEnabled(true);
        thread = new GameThread(getHolder(), this);


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        player = new Player(getWidth()/3, 0, getHeight(), getWidth(), ground);
        player.runBitmap = Util.getResizedBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.run_burned),100,100);
        player.standBitmap = Util.getResizedBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.stand ),100,100);

        ground = new Ground();
        ground.initializeDraw(getWidth(),getHeight());
        thread.running = true;
        thread.start();

    }
    @Override
    protected void onDraw(Canvas canvas) {
        frames++;
        canvas.drawColor(Color.rgb(155, 227, 255));
        ground.draw(canvas, player);

        this.buildDrawingCache();

        player.draw(canvas, ground);
        if(player.mapX+1000>ground.generatedDistance) {
            ground.generateTerrain(getWidth(), getHeight(), player);
        }
        //move stuff
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downY = event.getY();
            aFrame = frames;
            player.setRun(1);
            if (event.getX() > getWidth() / 9 * 5) {
                player.xSpeed = 10;
            }
            else if(event.getX() < getWidth() / 9 * 4){
                player.xSpeed = -10;
            }
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            player.setRun(0);
            if(downY - event.getY() > 100 && frames - aFrame < 10 && player.canJump != 0) {
                player.jump();
                player.canJump = 0;
            }
            player.xSpeed = 0;
        }

        return true;
    }
}

