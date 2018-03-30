package com.corp.srihari.increment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import static com.corp.srihari.increment.MainActivity._counter;
import static com.corp.srihari.increment.MainActivity.i;

public class Upgrades2 extends AppCompatActivity {
    private Button current_button;
    private TextView step_show;
    private ImageButton backButton;
    private Button[] buttonsArray;
    private CheckBox[] checksArray;
    private ImageButton[] locksArray;
    private ImageButton silverUnlock;
    private ImageButton isSilverUnlocked;
    
    private Button ubronze8;
    private Button ubronze9;
    private Button ubronze10;
    private Button ubronze11;
    private Button ubronze12;
    private Button ubronze13;
    private Button ubronze14;
    
    private CheckBox ubronze1;
    private CheckBox ubronze2;
    private CheckBox ubronze3;
    private CheckBox ubronze4;
    private CheckBox ubronze5;
    private CheckBox ubronze6;
    private CheckBox ubronze7;
    
    private ImageButton ulock1;
    private ImageButton ulock2;
    private ImageButton ulock3;
    private ImageButton ulock4;
    private ImageButton ulock5;
    private ImageButton ulock6;
    public void purchaseUpgrade(Button button, CheckBox checkbox, int money, int step) {
        if (_counter >= money) {
            //make checkbox checked
            checkbox.setChecked(true);
            checkbox.setEnabled(false);
            button.setEnabled(false);
            _counter -= money;
            i = step;
            saveInt("step", i);
            step_show = (TextView) findViewById(R.id.current_value);
            step_show.setText("Current Value = $" + step + "/click");
            Button[] buttonsArray = {ubronze8, ubronze9, ubronze10, ubronze11, ubronze12, ubronze13, ubronze14};
            ImageButton[] locksArray = {ulock1,ulock2,ulock3,ulock4,ulock5,ulock6};
            current_button.setBackgroundResource(R.drawable.bronze_button);
            if (Settings.loadBool("soundEffects",Upgrades2.this)) {
                final MediaPlayer upgrade_sound = MediaPlayer.create(Upgrades2.this, R.raw.purchase_sound);
                upgrade_sound.start();
                upgrade_sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        upgrade_sound.release();
                    }

                });
            }
            if (loadInt("bronzeUpgrades") + 1 < buttonsArray.length) {
                int new_index = loadInt("bronzeUpgrades") + 1;
                current_button = buttonsArray[new_index];
                current_button.setClickable(true);
                current_button.setEnabled(true);
                current_button.setBackgroundResource(R.drawable.orange_button);
                saveInt("bronzeUpgrades", new_index);
                ImageButton l = locksArray[loadInt("bronzeUpgrades")-1];
                AnimationSet animSet = new AnimationSet(true);
                RotateAnimation ranim = new RotateAnimation(0f, 480f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                ranim.setDuration(1000);
                ranim.setInterpolator(new DecelerateInterpolator());

                animSet.addAnimation(ranim);

                ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(l, "scaleX", 0.2f);
                ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(l, "scaleY", 0.2f);
                scaleDownX2.setDuration(1000);
                scaleDownY2.setDuration(1000);

                AnimatorSet scaleDown2 = new AnimatorSet();
                scaleDown2.play(scaleDownX2).with(scaleDownY2);

                scaleDown2.start();
                l.startAnimation(animSet);
                ((ViewManager)l.getParent()).removeView(l);

            } else {
                save("ufinal", true);
            }

            saveLong("money",_counter);
            saveInt("step",i);
        }
        else {

            CustomAlert customAlert = new CustomAlert(Upgrades2.this, "Upgrades Insufficient");
            customAlert.show();

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrades2);

        backButton();

        ubronze1 = (CheckBox) findViewById(R.id.ubronze1);
        ubronze2 = (CheckBox) findViewById(R.id.ubronze2);
        ubronze3 = (CheckBox) findViewById(R.id.ubronze3);
        ubronze4 = (CheckBox) findViewById(R.id.ubronze4);
        ubronze5 = (CheckBox) findViewById(R.id.ubronze5);
        ubronze6 = (CheckBox) findViewById(R.id.ubronze6);
        ubronze7 = (CheckBox) findViewById(R.id.ubronze7);

        ubronze8 = (Button) findViewById(R.id.ubronze8);
        ubronze9 = (Button) findViewById(R.id.ubronze9);
        ubronze10 = (Button) findViewById(R.id.ubronze10);
        ubronze11 = (Button) findViewById(R.id.ubronze11);
        ubronze12 = (Button) findViewById(R.id.ubronze12);
        ubronze13 = (Button) findViewById(R.id.ubronze13);
        ubronze14 = (Button) findViewById(R.id.ubronze14);

        ulock1 = (ImageButton) findViewById(R.id.ulock1);
        ulock2 = (ImageButton) findViewById(R.id.ulock2);
        ulock3 = (ImageButton) findViewById(R.id.ulock3);
        ulock4 = (ImageButton) findViewById(R.id.ulock4);
        ulock5 = (ImageButton) findViewById(R.id.ulock5);
        ulock6 = (ImageButton) findViewById(R.id.ulock6);

        step_show = (TextView) findViewById(R.id.current_value);
        step_show.setText("Current Value = $" + NumberFormat.getNumberInstance(Locale.US).format(loadInt("step")) + "/click");

        buttonsArray = new Button[] {ubronze8, ubronze9, ubronze10, ubronze11, ubronze12, ubronze13, ubronze14};
        checksArray = new CheckBox[] {ubronze1, ubronze2, ubronze3, ubronze4, ubronze5, ubronze6,ubronze7};
        locksArray = new ImageButton[] {ulock1,ulock2,ulock3,ulock4,ulock5, ulock6};

        current_button = buttonsArray[loadInt("bronzeUpgrades")];
        if (!current_button.equals(buttonsArray[buttonsArray.length - 1])) {
            current_button.setBackgroundResource(R.drawable.orange_button);
            current_button.setClickable(true);
            current_button.setEnabled(true);
        }
        if (load("ufinal")) {
            current_button.setEnabled(false);
            current_button.setClickable(false);
            ubronze7.setEnabled(false);
            ubronze7.setChecked(true);
        }
        for (int n = 0; n<=buttonsArray.length - 1;n++) {
            if (buttonsArray[n] != current_button) {
                buttonsArray[n].setClickable(false);
                buttonsArray[n].setEnabled(false);
            }
        }

        buttonAnimationUpgrades(ubronze8,ubronze1,325000,500);
        buttonAnimationUpgrades(ubronze9,ubronze2,700000,1000);
        buttonAnimationUpgrades(ubronze10,ubronze3,1500000,2500);
        buttonAnimationUpgrades(ubronze11,ubronze4,5000000,5000);
        buttonAnimationUpgrades(ubronze12,ubronze5,15000000,10000);
        buttonAnimationUpgrades(ubronze13,ubronze6,45000000,25000);
        buttonAnimationUpgrades(ubronze14,ubronze7,125000000,50000);

        onClickLock(ulock1);
        onClickLock(ulock2);
        onClickLock(ulock3);
        onClickLock(ulock4);
        onClickLock(ulock5);
        onClickLock(ulock6);

        for (int i = 0 ; i<=buttonsArray.length-1; i++) {
            if (buttonsArray[i].equals(current_button)) {
                break;
            } else {
                checksArray[i].setEnabled(false);
                checksArray[i].setChecked(true);
            }
        }
        for (int i=loadInt("bronzeUpgrades")-1;i>=0;i--) {
            ImageButton entry = locksArray[i];
            ((ViewManager) entry.getParent()).removeView(entry);

        }

    }
    public void buttonAnimationUpgrades(final Button button, final CheckBox checkbox, final int money, final int step) {
        checkbox.setClickable(false);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(button, "scaleX", 0.9f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(button, "scaleY", 0.9f);
                        scaleDownX.setDuration(0);
                        scaleDownY.setDuration(0);

                        AnimatorSet scaleDown = new AnimatorSet();
                        scaleDown.play(scaleDownX).with(scaleDownY);

                        scaleDown.start();

                        return true;
                    case MotionEvent.ACTION_UP:
                        purchaseUpgrade(button, checkbox, money, step);
                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(button, "scaleX", 1f);
                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(button, "scaleY", 1f);
                        scaleDownX2.setDuration(0);
                        scaleDownY2.setDuration(0);

                        AnimatorSet scaleDown2 = new AnimatorSet();
                        scaleDown2.play(scaleDownX2).with(scaleDownY2);

                        scaleDown2.start();

                        return true;
                }
                return true;
            }
        });
    }
    private void backButton() {
        backButton = (ImageButton) findViewById(R.id.backu_button);
        backButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(backButton, "scaleX", 0.9f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(backButton, "scaleY", 0.9f);
                        scaleDownX.setDuration(0);
                        scaleDownY.setDuration(0);

                        AnimatorSet scaleDown = new AnimatorSet();
                        scaleDown.play(scaleDownX).with(scaleDownY);

                        scaleDown.start();

                        backButton.setColorFilter(Color.argb(300, 300, 300, 1));

                        return true;
                    case MotionEvent.ACTION_UP:
                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(backButton, "scaleX", 1f);
                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(backButton, "scaleY", 1f);
                        scaleDownX2.setDuration(0);
                        scaleDownY2.setDuration(0);

                        AnimatorSet scaleDown2 = new AnimatorSet();
                        scaleDown2.play(scaleDownX2).with(scaleDownY2);

                        scaleDown2.start();
                        if (Settings.loadBool("soundEffects",Upgrades2.this)) {
                            final MediaPlayer mini_sound = MediaPlayer.create(Upgrades2.this, R.raw.mini_button);
                            mini_sound.start();
                            mini_sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mini_sound.release();
                                }

                            });
                        }

                        finish();
                        return true;
                }
                return true;
            }
        });
    }
    public void onClickLock(final ImageButton lock) {
        lock.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(lock, "scaleX", 0.9f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(lock, "scaleY", 0.9f);
                        scaleDownX.setDuration(0);
                        scaleDownY.setDuration(0);

                        AnimatorSet scaleDown = new AnimatorSet();
                        scaleDown.play(scaleDownX).with(scaleDownY);

                        scaleDown.start();
                        return true;
                    case MotionEvent.ACTION_UP:
                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(lock, "scaleX", 1f);
                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(lock, "scaleY", 1f);
                        scaleDownX2.setDuration(0);
                        scaleDownY2.setDuration(0);

                        AnimatorSet scaleDown2 = new AnimatorSet();
                        scaleDown2.play(scaleDownX2).with(scaleDownY2);

                        scaleDown2.start();
                        CustomAlert customAlert = new CustomAlert(Upgrades2.this, "Previous Upgrade");
                        customAlert.show();
                        return true;
                }
                return true;
            }
        });
    }
    private void save(String name, final boolean isChecked) {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings_Money",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(name, isChecked);
        editor.commit();
    }
    public void saveInt(String name, final int counter) {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings_Money",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(name, counter);
        editor.commit();
    }
    private boolean load(String name) {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings_Money",MODE_PRIVATE);
        return sharedPreferences.getBoolean(name,false);
    }
    public int loadInt(String name) {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings_Money",MODE_PRIVATE);
        return sharedPreferences.getInt(name, 0);
    }
    private void saveLong(String name, long counter) {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings_Money", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(name, counter);
        editor.apply();
    }
    private long loadLong(String name) {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings_Money",MODE_PRIVATE);
        return sharedPreferences.getLong(name, 0);
    }
    @Override
    public void onPause() {
        super.onPause();
        MusicService.pause_music();
    }
    @Override
    public void onResume() {
        super.onResume();
        MusicService.play_music(Upgrades2.this);
        if (buttonsArray != null) {
            current_button = buttonsArray[loadInt("bronzeUpgrades")];
            if (current_button.equals(buttonsArray[buttonsArray.length-1])) {
                if (!load("ufinal")) {
                    current_button.setBackgroundResource(R.drawable.orange_button);
                }
            }
        }

        step_show = (TextView) findViewById(R.id.current_value);
        step_show.setText("Current Value = $" + NumberFormat.getNumberInstance(Locale.US).format(loadInt("step")) + "/click");
    }
}
