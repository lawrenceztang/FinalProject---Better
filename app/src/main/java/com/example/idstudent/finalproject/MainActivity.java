package com.example.idstudent.finalproject;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

GameView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            GameView view = new GameView(this);
            setContentView(view);
        }

    }
    protected void onResume() {
        super.onResume();
    }
    protected  void onRestart () {
        super.onRestart();
        view.thread.running = true;
    }
    protected void onPause() {
        super.onPause();
    }
    protected void onStop() {
        super.onStop();
        view.thread.running = false;
    }
    protected void onDestroy() {
        super.onDestroy();
    }
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String lifecycleDisplayTextViewContents = "hi";
        outState.putString("callbacks", lifecycleDisplayTextViewContents);
    }


}
