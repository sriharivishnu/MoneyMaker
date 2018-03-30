package com.corp.srihari.increment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

import com.google.android.gms.ads.mediation.customevent.CustomEvent;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import static com.corp.srihari.increment.MainActivity._counter;
import static com.corp.srihari.increment.MainActivity.i;

public class Upgrades extends AppCompatActivity {
    private Button oneHundred;
    private CheckBox oneHundredCheck;
    private Button two;
    private CheckBox twoCheck;
    private Button three;
    private CheckBox threeCheck;
    private Button four;
    private CheckBox fourCheck;
    private Button five;
    private CheckBox fiveCheck;
    private Button six;
    private CheckBox sixCheck;
    private Button seven;
    private CheckBox sevenCheck;
    private ImageButton lockOne;
    private ImageButton lockTwo;
    private ImageButton lockThree;
    private ImageButton lockFour;
    private ImageButton lockFive;
    private ImageButton lockSix;
    private ImageButton masterLock;
    private ImageButton bronzeUnlock;
    private ImageButton backButton;
    private TextView step_show;

    private Boolean final_upgrade;
    private Boolean isBronzeUnlocked;

    public Button current_button;
    public Button[] buttonsArray;

    public ImageButton[] locksArray;

    private static int BRONZE_UNLOCK_PRICE = 2000000;

    public void purchaseUpgrade(Button button, CheckBox checkbox, int money, int step) {
        if (_counter >= money) {
            //make checkbox checked
            checkbox.setChecked(true);
            checkbox.setEnabled(false);
            button.setEnabled(false);
            _counter -= money;
            i = step;
            saveInt("step", i);
            step_show = (TextView) findViewById(R.id.textView22);
            step_show.setText("Current Value = $" + NumberFormat.getNumberInstance(Locale.US).format(loadInt("step")) + "/click");
            Button[] buttonsArray = {oneHundred, two, three, four, five, six, seven};
            ImageButton[] locksArray = {lockOne,lockTwo,lockThree,lockFour,lockFive,lockSix};
            current_button.setBackgroundResource(R.drawable.grey_button);
            if (Settings.loadBool("soundEffects",Upgrades.this)) {
                final MediaPlayer upgrade_sound = MediaPlayer.create(Upgrades.this, R.raw.purchase_sound);
                upgrade_sound.start();
                upgrade_sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        upgrade_sound.release();
                    }

                });
            }
            if (loadInt("ButtonArrayIndex2") + 1 < buttonsArray.length) {
                int new_index = loadInt("ButtonArrayIndex2") + 1;
                current_button = buttonsArray[new_index];
                current_button.setClickable(true);
                current_button.setEnabled(true);
                current_button.setBackgroundResource(R.drawable.orange_button);
                saveInt("ButtonArrayIndex2", new_index);
                ImageButton l = locksArray[loadInt("ButtonArrayIndex2")-1];
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
                final_upgrade = true;
                AnimationSet animSet = new AnimationSet(true);
                RotateAnimation ranim = new RotateAnimation(0f, 480f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                ranim.setDuration(1000);
                ranim.setInterpolator(new DecelerateInterpolator());

                animSet.addAnimation(ranim);

                ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(masterLock, "scaleX", 0.2f);
                ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(masterLock, "scaleY", 0.2f);
                scaleDownX2.setDuration(1000);
                scaleDownY2.setDuration(1000);

                AnimatorSet scaleDown2 = new AnimatorSet();
                scaleDown2.play(scaleDownX2).with(scaleDownY2);

                scaleDown2.start();
                masterLock.startAnimation(animSet);
                if (Settings.loadBool("soundEffects",Upgrades.this)) {
                    final MediaPlayer master_sound = MediaPlayer.create(Upgrades.this, R.raw.ability_unlock);
                    master_sound.start();
                    master_sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            master_sound.release();
                        }

                    });
                }
                ((ViewManager)masterLock.getParent()).removeView(masterLock);
                bronzeUnlock.setClickable(true);
                bronzeUnlock.setEnabled(true);
                final_upgrade = true;
                save("final", true);

            }

            saveLong("money",_counter);
            saveInt("step",i);
        }
        else {

            CustomAlert customAlert = new CustomAlert(Upgrades.this, "Upgrades Insufficient");
            customAlert.show();

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrades);
        masterLock = (ImageButton) findViewById(R.id.master_lock);
        bronzeUnlock = (ImageButton) findViewById(R.id.bronze_unlock);
        isBronzeUnlocked = load("Bronze");

        backButton = (ImageButton) findViewById(R.id.imageButton21);
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
                        if (Settings.loadBool("soundEffects",Upgrades.this)) {
                            final MediaPlayer mini_sound = MediaPlayer.create(Upgrades.this, R.raw.mini_button);
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

        oneHundred = (Button) findViewById(R.id.button2);
        oneHundred.setClickable(false);
        oneHundred.setEnabled(false);
        oneHundredCheck = (CheckBox) findViewById(R.id.checkBox3);
        oneHundredCheck.setClickable(false);
        buttonAnimationIncome(oneHundred,oneHundredCheck,100,2);

        two = (Button) findViewById(R.id.button);
        two.setClickable(false);
        two.setEnabled(false);
        twoCheck = (CheckBox) findViewById(R.id.checkBox);
        twoCheck.setClickable(false);
        two.setEnabled(false);
        twoCheck.setEnabled(false);
        buttonAnimationIncome(two,twoCheck,500,5);

        three = (Button) findViewById(R.id.button8);
        three.setClickable(false);
        three.setEnabled(false);
        threeCheck = (CheckBox) findViewById(R.id.checkBox10);
        threeCheck.setClickable(false);
        three.setEnabled(false);
        buttonAnimationIncome(three,threeCheck,1500,10);

        four = (Button) findViewById(R.id.button7);
        four.setClickable(false);
        four.setEnabled(false);
        fourCheck = (CheckBox) findViewById(R.id.checkBox8);
        fourCheck.setClickable(false);
        four.setEnabled(false);
        buttonAnimationIncome(four,fourCheck,4250,25);

        five = (Button) findViewById(R.id.button6);
        five.setClickable(false);
        five.setEnabled(false);
        fiveCheck = (CheckBox) findViewById(R.id.checkBox2);
        fiveCheck.setClickable(false);
        five.setEnabled(false);
        buttonAnimationIncome(five,fiveCheck,15000,50);

        six = (Button) findViewById(R.id.button5);
        six.setClickable(false);
        six.setEnabled(false);
        sixCheck = (CheckBox) findViewById(R.id.checkBox11);
        sixCheck.setClickable(false);
        six.setEnabled(false);
        buttonAnimationIncome(six,sixCheck,45000,100);

        seven = (Button) findViewById(R.id.button10);
        seven.setClickable(false);
        seven.setEnabled(false);
        sevenCheck = (CheckBox) findViewById(R.id.checkBox7);
        sevenCheck.setClickable(false);
        seven.setEnabled(false);
        buttonAnimationIncome(seven,sevenCheck,115000,250);

        Button[] buttonsArray = {oneHundred, two, three, four, five, six, seven};
        current_button = buttonsArray[loadInt("ButtonArrayIndex2")];
        if (!load("final")) {
            current_button.setClickable(true);
            current_button.setEnabled(true);
            current_button.setBackgroundResource(R.drawable.orange_button);
        } else {
            current_button.setClickable(false);
            current_button.setEnabled(false);

        }
        Log.d("Yeah",seven.getBackground().toString());
        lockOne = (ImageButton) findViewById(R.id.imageButton11);
        onClickLock(lockOne);
        lockTwo = (ImageButton) findViewById(R.id.imageButton16);
        onClickLock(lockTwo);
        lockThree = (ImageButton) findViewById(R.id.imageButton15);
        onClickLock(lockThree);
        lockFour = (ImageButton) findViewById(R.id.imageButton14);
        onClickLock(lockFour);
        lockFive = (ImageButton) findViewById(R.id.imageButton13);
        onClickLock(lockFive);
        lockSix = (ImageButton) findViewById(R.id.imageButton12);
        onClickLock(lockSix);

        ImageButton[] locksArray = {lockOne,lockTwo,lockThree,lockFour,lockFive,lockSix};

        for (int i=loadInt("ButtonArrayIndex2")-1;i>=0;i--) {
            ImageButton entry = locksArray[i];
            ((ViewManager) entry.getParent()).removeView(entry);

        }

        step_show = (TextView) findViewById(R.id.textView22);
        step_show.setText("Current Value = $" + NumberFormat.getNumberInstance(Locale.US).format(loadInt("step")) + "/click");
        if (load("Bronze")) {
            bronzeUnlock.setImageResource(R.drawable.bronze_unlocked);
        }
        if (load("final")) {
            ((ViewManager) masterLock.getParent()).removeView(masterLock);
        } else {
            bronzeUnlock.setClickable(false);
            bronzeUnlock.setEnabled(false);
        }
        onClickLock(masterLock);
        bronzeUnlock.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(bronzeUnlock, "scaleX", 0.9f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(bronzeUnlock, "scaleY", 0.9f);
                        scaleDownX.setDuration(0);
                        scaleDownY.setDuration(0);

                        AnimatorSet scaleDown = new AnimatorSet();
                        scaleDown.play(scaleDownX).with(scaleDownY);

                        scaleDown.start();

                        return true;
                    case MotionEvent.ACTION_UP:
                        if (isBronzeUnlocked) {
                            Intent intent = new Intent(Upgrades.this, Upgrades2.class);
                            startActivity(intent);
                            finish();
                        } else {
                            CustomAlert bronzeAlert = new CustomAlert(Upgrades.this, "Custom") {
                                @Override
                                public void onYesClicked() {
                                    if (_counter >= BRONZE_UNLOCK_PRICE) {
                                        _counter -= BRONZE_UNLOCK_PRICE;
                                        saveLong("money", _counter);
                                        bronzeUnlock.setClickable(true);
                                        bronzeUnlock.setEnabled(true);
                                        isBronzeUnlocked = true;
                                        save("Bronze", isBronzeUnlocked);
                                        bronzeUnlock.setImageResource(R.drawable.bronze_unlocked);
                                        Intent i = new Intent(Upgrades.this, Upgrades2.class);
                                        startActivity(i);
                                    } else {
                                        CustomAlert customAlert12 = new CustomAlert(Upgrades.this, "");
                                        customAlert12.show();
                                        customAlert12.customize("Insufficient Funds", "You need $" + NumberFormat.getNumberInstance(Locale.US).format(BRONZE_UNLOCK_PRICE) + " to unlock" +
                                                " the bronze class of Upgrades.");
                                    }
                                }
                            };
                            bronzeAlert.show();
                            bronzeAlert.customize("Unlock Bronze Class", "Do you wish to unlock bronze class for $" + NumberFormat.getNumberInstance(Locale.US).format(BRONZE_UNLOCK_PRICE) + "?");
                            ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(bronzeUnlock, "scaleX", 1f);
                            ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(bronzeUnlock, "scaleY", 1f);
                            scaleDownX2.setDuration(0);
                            scaleDownY2.setDuration(0);

                            AnimatorSet scaleDown2 = new AnimatorSet();
                            scaleDown2.play(scaleDownX2).with(scaleDownY2);

                            scaleDown2.start();

                            return true;
                        }
                }
                return true;
            }
        });

    }
    public void buttonAnimationIncome(final Button button, final CheckBox checkbox, final int money, final int step) {
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
                        if (lock.equals(masterLock)) {
                            CustomAlert customAlert2 = new CustomAlert(Upgrades.this, "s");
                            customAlert2.show();
                            customAlert2.customize("Message", "Purchase every upgrade to unlock Bronze Class.");

                        } else {
                            CustomAlert customAlert = new CustomAlert(Upgrades.this, "Previous Upgrade");
                            customAlert.show();
                        }
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
        save("one",oneHundredCheck.isChecked());
        save("two",twoCheck.isChecked());
        save("three",threeCheck.isChecked());
        save("four",fourCheck.isChecked());
        save("five",fiveCheck.isChecked());
        save("six",sixCheck.isChecked());
        save("seven",sevenCheck.isChecked());
        MusicService.pause_music();
    }
    @Override
    public void onResume() {
        MusicService.play_music(Upgrades.this);
        super.onResume();
        oneHundredCheck.setChecked(load("one"));
        oneHundredCheck.setEnabled(!(load("one")));

        twoCheck.setChecked(load("two"));
        twoCheck.setEnabled(!(load("two")));

        threeCheck.setChecked(load("three"));
        threeCheck.setEnabled(!(load("three")));

        fourCheck.setChecked(load("four"));
        fourCheck.setEnabled(!(load("four")));

        fiveCheck.setChecked(load("five"));
        fiveCheck.setEnabled(!(load("five")));

        sixCheck.setChecked(load("six"));
        sixCheck.setEnabled(!(load("six")));

        sevenCheck.setChecked(load("seven"));
        sevenCheck.setEnabled(!(load("seven")));

        if (buttonsArray != null) {
            current_button = buttonsArray[loadInt("ButtonArrayIndex2")];
            if (!current_button.equals(buttonsArray[buttonsArray.length-1])) {
                current_button.setEnabled(false);
                current_button.setClickable(false);
            } else {
                current_button.setEnabled(true);
                current_button.setClickable(true);
            }
        }
        step_show = (TextView) findViewById(R.id.textView22);
        step_show.setText("Current Value = $" + NumberFormat.getNumberInstance(Locale.US).format(loadInt("step")) + "/click");

    }
    @Override
    public void onRestart() {
        super.onRestart();
        MusicService.play_music(Upgrades.this);
    }
}





