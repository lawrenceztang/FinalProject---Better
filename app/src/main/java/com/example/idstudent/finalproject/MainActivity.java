package com.example.idstudent.finalproject;

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
        if(mPrefs.getString("MyObject", null) == null) {
                GameView view = new GameView(this);
                setContentView(view);
        }
        else {
            String json = mPrefs.getString("MyObject", null);
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
        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(view);
        prefsEditor.putString("objects", json);
        prefsEditor.commit();
    }
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);



    }


}
