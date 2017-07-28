package com.example.idstudent.finalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread{
    public boolean running;
    GameView gameView;
    SurfaceHolder surfaceHolder;
    public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }
    @Override
    @SuppressLint("WrongCall")
    public void run() {
        Canvas canvas;
        while (running) {
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();

                synchronized (surfaceHolder) {
                    this.gameView.onDraw(canvas);
                }
            }
            finally {
                if(canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

            if(running == false) {

            }
        }
    }
}

