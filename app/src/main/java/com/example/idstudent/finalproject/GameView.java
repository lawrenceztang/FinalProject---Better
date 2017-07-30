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

import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    GameThread thread;
    Player player;
    Random rand;
    Ground ground;
    int randomDraw;
    long aFrame;
    float downY;
    long frames;
    ArrayList<Cloud> cloudList;
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
        rand = new Random();
        player = new Player(getWidth()/3, 0, getHeight(), getWidth(), ground);
        player.runBitmap = Util.getResizedBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.run_burned),100,150);
        player.standBitmap = Util.getResizedBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.stand ),100,150);
        ground = new Ground();
        ground.initializeDraw(getWidth(),getHeight());
        thread.running = true;
        thread.start();
        cloudList = new ArrayList<Cloud>();

    }
    @Override
    protected void onDraw(Canvas canvas) {
        int times = 0;
        frames++;
        canvas.drawColor(Color.rgb(155, 227, 255));
        for(int i = 0; i < cloudList.size(); i++) {
            cloudList.get(i).draw(canvas);
        }
        ground.draw(canvas, player);

        this.buildDrawingCache();

        player.draw(canvas, ground);
        if(player.mapX+1000>ground.generatedDistance) {
            ground.generateTerrain(getWidth(), getHeight(), player);
        }
        randomDraw = rand.nextInt(1000);
        if(randomDraw > 997) {
            cloudList.add(new Cloud(Util.getResizedBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.cloud1),rand.nextInt(300) + 300,rand.nextInt(100) + 100)));

        }

        if(times<1) {
            Cloud.canvas = canvas;
        }
        times++;

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
    void touchMove(MotionEvent event) {

    }
    void touchUp(MotionEvent event) {
        player.setRun(0);
        if(
                downY - event.getY() > 100 &&
                frames - aFrame      < 30  &&
                player.canJump       != 0
                ) {
            player.jump();
            player.canJump = 0;
        }
        player.xSpeed = 0;
    }

}

