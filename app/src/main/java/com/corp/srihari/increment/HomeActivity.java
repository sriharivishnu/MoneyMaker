package com.corp.srihari.increment;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 4000;
    public boolean first_log_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        first_log_in = load("firstLogin");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (first_log_in) {
                    Intent homeIntent = new Intent(HomeActivity.this, IntroActivity.class);
                    startActivity(homeIntent);
                    finish();
                    save("firstLogin", false);
                }
                else {
                    finish();
                    save("firstLogin", false);
                    }
            }
        }, SPLASH_TIME_OUT);
    }

    private void save(String name, final boolean isLoggedIn) {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings_Money",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(name, isLoggedIn);
        editor.commit();
    }
    private boolean load(String name) {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings_Money",MODE_PRIVATE);
        return sharedPreferences.getBoolean(name,true);
    }

    @Override
    public void onPause() {
        super.onPause();
        MusicService.pause_music();
    }

    @Override
    public void onResume() {
        super.onResume();
        MusicService.play_music(HomeActivity.this);
    }
}
