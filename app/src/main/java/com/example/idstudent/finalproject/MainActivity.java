package com.example.idstudent.finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

GameView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences  mPrefs = getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        if(mPrefs.getString("Objects", null) != null) {
                GameView view = new GameView(this);
                setContentView(view);
        }
        else {
            String json = mPrefs.getString("Objects", null);
            GameView view = gson.fromJson(json, GameView.class);
            setContentView(view);
        }


    }
    protected void onResume() {
        super.onResume();
    }
    protected  void onRestart () {
        super.onRestart();
    }
    protected void onPause() {
        super.onPause();


    }
    protected void onStop() {

        super.onStop();

    }
    protected void onDestroy() {
        super.onDestroy();
    }
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);



    }


}
