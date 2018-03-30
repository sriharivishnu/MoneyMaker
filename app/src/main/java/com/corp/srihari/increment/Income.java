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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.corp.srihari.increment.MainActivity;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.corp.srihari.increment.MainActivity._counter;
import static com.corp.srihari.increment.MainActivity.flow;
import static com.corp.srihari.increment.MainActivity.loadBool;
import static com.corp.srihari.increment.MainActivity.saveBool;

public class Income extends AppCompatActivity {
    private Button oneFlow;
    private TextView oneText;
    private int one_value = 0;
    private Button twoFlow;
    private TextView twoText;
    private int two_value = 0;
    private Button threeFlow;
    private TextView threeText;
    private int three_value = 0;
    private Button fourFlow;
    private TextView fourText;
    private int four_value = 0;
    private Button fiveFlow;
    private TextView fiveText;
    private int five_value = 0;
    private Button sixFlow;
    private TextView sixText;
    private int six_value = 0;
    private Button sevenFlow;
    private TextView sevenText;
    private int seven_value = 0;
    private TextView total_flow;
    private ImageButton lockOne;
    private ImageButton lockTwo;
    private ImageButton lockThree;
    private ImageButton lockFour;
    private ImageButton lockFive;
    private ImageButton lockSix;
    private ImageButton master_lock_i;
    private ImageButton bronze_unlocked_i;
    private ImageButton backButton;

    private boolean isIncomeBronze;
    public Button current_button;
    public Button[] buttonsArray;

    public String[] stringsArray = {"One","Two","Three","Four","Five","Six","Seven"};

    public ImageButton[] locksArray;

    private static int BRONZE_UNLOCK_PRICE = 5000000;

    private void purchaseIncome(TextView number,String name,int value, int money, int flow_increase) {
        if (_counter >= money) {
            _counter -= money;
            flow += flow_increase;
            int local_value = Integer.parseInt(number.getText().toString());
            local_value += 1;
            value = local_value;
            String str_value = Integer.toString(local_value);
            number.setText(str_value);
            if (Settings.loadBool("soundEffects",Income.this)) {
                final MediaPlayer income_sound = MediaPlayer.create(Income.this, R.raw.purchase_sound);
                income_sound.start();
                income_sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        income_sound.release();
                    }

                });
            }
            //save new main counter value, flow amount, and individual counter value
            saveLong("money",_counter);
            saveInt("flow",flow);
            saveInt(name,value);
            total_flow.setText("Total: +$" + NumberFormat.getNumberInstance(Locale.US).format(loadInt("flow")) + "/sec");
            Button[] buttonsArray = {oneFlow, twoFlow, threeFlow, fourFlow, fiveFlow, sixFlow, sevenFlow};
            ImageButton[] locksArray = {lockOne, lockTwo, lockThree, lockFour, lockFive, lockSix};


            if (loadInt(stringsArray[loadInt("ButtonArrayIndex")]) == 1) {
                current_button.setBackgroundResource(R.drawable.grey_button);
                if (loadInt("ButtonArrayIndex") + 1 < buttonsArray.length) {
                    int new_index = loadInt("ButtonArrayIndex") + 1;
                    current_button = buttonsArray[new_index];
                    current_button.setClickable(true);
                    current_button.setEnabled(true);
                    current_button.setBackgroundResource(R.drawable.orange_button);
                    saveInt("ButtonArrayIndex", new_index);
                    ImageButton l = locksArray[loadInt("ButtonArrayIndex")-1];
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
                    AnimationSet animSet = new AnimationSet(true);
                    RotateAnimation ranim = new RotateAnimation(0f, 480f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    ranim.setDuration(1000);
                    ranim.setInterpolator(new DecelerateInterpolator());

                    animSet.addAnimation(ranim);

                    ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(master_lock_i,"scaleX", 0.2f);
                    ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(master_lock_i, "scaleY", 0.2f);
                    scaleDownX2.setDuration(1000);
                    scaleDownY2.setDuration(1000);

                    AnimatorSet scaleDown2 = new AnimatorSet();
                    scaleDown2.play(scaleDownX2).with(scaleDownY2);

                    scaleDown2.start();
                    master_lock_i.startAnimation(animSet);
                    if (Settings.loadBool("soundEffects",Income.this)) {
                        final MediaPlayer master_sound = MediaPlayer.create(Income.this, R.raw.ability_unlock);
                        master_sound.start();
                        master_sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                master_sound.release();
                            }

                        });
                    }
                    if (findViewById(R.id.master_lock_i) != null) {
                        ((ViewManager) master_lock_i.getParent()).removeView(master_lock_i);
                    }
                    bronze_unlocked_i.setClickable(true);
                    bronze_unlocked_i.setEnabled(true);
                    saveBool("ifinal", true, Income.this);
                }
            }
        }
        else {
            CustomAlert customAlert = new CustomAlert(Income.this, "Revenue Insufficient");
            customAlert.show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        master_lock_i = (ImageButton) findViewById(R.id.master_lock_i);
        bronze_unlocked_i = (ImageButton) findViewById(R.id.bronze_unlock_i);
        isIncomeBronze = loadBool("BronzeI", Income.this);
        backButton = (ImageButton) findViewById(R.id.backButton);
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
                        if (Settings.loadBool("soundEffects",Income.this)) {
                            final MediaPlayer mini_sound = MediaPlayer.create(Income.this, R.raw.mini_button);
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

        oneFlow = (Button) findViewById(R.id.button13);
        oneText = (TextView) findViewById(R.id.textView2);
        buttonAnimationIncome(oneFlow,oneText,"One",one_value,5000,1);

        twoFlow = (Button) findViewById(R.id.button11);
        twoText = (TextView) findViewById(R.id.textView8);
        buttonAnimationIncome(twoFlow,twoText,"Two",two_value,24500,5);

        threeFlow = (Button) findViewById(R.id.button12);
        threeText = (TextView) findViewById(R.id.textView5);
        buttonAnimationIncome(threeFlow,threeText,"Three",three_value,48000,10);

        fourFlow = (Button) findViewById(R.id.button17);
        fourText = (TextView) findViewById(R.id.textView11);
        buttonAnimationIncome(fourFlow,fourText,"Four",four_value,117500,25);

        fiveFlow = (Button) findViewById(R.id.button16);
        fiveText = (TextView) findViewById(R.id.textView13);
        buttonAnimationIncome(fiveFlow,fiveText,"Five",five_value,230000,50);

        sixFlow = (Button) findViewById(R.id.button15);
        sixText = (TextView) findViewById(R.id.textView15);
        buttonAnimationIncome(sixFlow,sixText,"Six",six_value,450000,100);

        sevenFlow = (Button) findViewById(R.id.button14);
        sevenText = (TextView) findViewById(R.id.textView9);
        buttonAnimationIncome(sevenFlow,sevenText,"Seven",seven_value,1100000,250);

        Button[] buttonsArray = {oneFlow, twoFlow, threeFlow, fourFlow, fiveFlow, sixFlow, sevenFlow};

        current_button = buttonsArray[loadInt("ButtonArrayIndex")];

        if (!current_button.equals(buttonsArray[buttonsArray.length-1])) {
            current_button.setBackgroundResource(R.drawable.orange_button);
        }

        lockOne = (ImageButton) findViewById(R.id.imageButton5);
        onClickLock(lockOne);
        lockTwo = (ImageButton) findViewById(R.id.imageButton10);
        onClickLock(lockTwo);
        lockThree = (ImageButton) findViewById(R.id.imageButton9);
        onClickLock(lockThree);
        lockFour = (ImageButton) findViewById(R.id.imageButton2);
        onClickLock(lockFour);
        lockFive = (ImageButton) findViewById(R.id.imageButton8);
        onClickLock(lockFive);
        lockSix = (ImageButton) findViewById(R.id.imageButton7);
        onClickLock(lockSix);

        ImageButton[] locksArray = {lockOne, lockTwo, lockThree, lockFour, lockFive, lockSix};
        for (int i=loadInt("ButtonArrayIndex")-1;i>=0;i--) {
            ImageButton entry = locksArray[i];
            ((ViewManager)entry.getParent()).removeView(entry);
        }
        for (int i=loadInt("ButtonArrayIndex")+1;i<buttonsArray.length;i++) {
            buttonsArray[i].setEnabled(false);
            buttonsArray[i].setClickable(false);
        }

        total_flow = (TextView) findViewById(R.id.textView17);
        total_flow.setText("Total: +$" + NumberFormat.getNumberInstance(Locale.US).format(loadInt("flow")) + "/sec");

        if (loadBool("BronzeI",Income.this)) {
            bronze_unlocked_i.setImageResource(R.drawable.bronze_unlocked);
        }
        boolean removed = false;
        if (loadBool("ifinal", Income.this)) {
            ((ViewManager) master_lock_i.getParent()).removeView(master_lock_i);
            removed = true;
            bronze_unlocked_i.setClickable(true);
            bronze_unlocked_i.setEnabled(true);
        }
        if (loadInt("Seven") >= 1) {
            if (!removed) {
                ((ViewManager) master_lock_i.getParent()).removeView(master_lock_i);
            }
            bronze_unlocked_i.setClickable(true);
            bronze_unlocked_i.setEnabled(true);
        }
        else {
            bronze_unlocked_i.setClickable(false);
            bronze_unlocked_i.setEnabled(false);
        }
        onClickLock(master_lock_i);
        bronze_unlocked_i.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(bronze_unlocked_i, "scaleX", 0.9f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(bronze_unlocked_i, "scaleY", 0.9f);
                        scaleDownX.setDuration(0);
                        scaleDownY.setDuration(0);

                        AnimatorSet scaleDown = new AnimatorSet();
                        scaleDown.play(scaleDownX).with(scaleDownY);

                        scaleDown.start();

                        return true;
                    case MotionEvent.ACTION_UP:
                        if (isIncomeBronze) {
                            Intent intent = new Intent(Income.this, Income2.class);
                            startActivity(intent);
                            finish();
                        } else {
                            CustomAlert bronzeAlert = new CustomAlert(Income.this, "Custom") {
                                @Override
                                public void onYesClicked() {
                                    if (_counter >= BRONZE_UNLOCK_PRICE) {
                                        _counter -= BRONZE_UNLOCK_PRICE;
                                        saveLong("money", _counter);
                                        bronze_unlocked_i.setClickable(true);
                                        bronze_unlocked_i.setEnabled(true);
                                        isIncomeBronze = true;
                                        saveBool("BronzeI", isIncomeBronze, Income.this);
                                        bronze_unlocked_i.setImageResource(R.drawable.bronze_unlocked);
                                        Intent i = new Intent(Income.this, Income2.class);
                                        startActivity(i);
                                    } else {
                                        CustomAlert customAlert12 = new CustomAlert(Income.this, "");
                                        customAlert12.show();
                                        customAlert12.customize("Insufficient Funds", "You need $" + NumberFormat.getNumberInstance(Locale.US).format(BRONZE_UNLOCK_PRICE) + " to unlock" +
                                                " the bronze class of Upgrades.");
                                    }
                                }
                            };
                            bronzeAlert.show();
                            bronzeAlert.customize("Unlock Bronze Class", "Do you wish to unlock bronze class for $" + NumberFormat.getNumberInstance(Locale.US).format(BRONZE_UNLOCK_PRICE) + "?");
                            ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(bronze_unlocked_i, "scaleX", 1f);
                            ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(bronze_unlocked_i, "scaleY", 1f);
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
    public void saveInt(String name, final int counter) {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings_Money",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(name, counter);
        editor.commit();
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

    public void buttonAnimationIncome(final Button button, final TextView number,final String name, final int value, final int money, final int flow_increase) {
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
                        purchaseIncome(number, name, value, money, flow_increase);

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
                        if (lock.equals(master_lock_i)) {
                            CustomAlert cds = new CustomAlert(Income.this,"s");
                            cds.show();
                            cds.customize("Message", "Purchase one of each investment to unlock Bronze Class.");
                        } else {
                            CustomAlert customAlert = new CustomAlert(Income.this, "Previous Investment");
                            customAlert.show();
                        }
                        return true;
                }
                return true;
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        MusicService.pause_music();
        saveInt("flow",flow);
    }

    @Override
    protected void onResume() {
        super.onResume();

        MusicService.play_music(Income.this);
        one_value = loadInt("One");
        oneText.setText(Integer.toString(one_value));

        two_value = loadInt("Two");
        twoText.setText(Integer.toString(two_value));

        three_value = loadInt("Three");
        threeText.setText(Integer.toString(three_value));

        four_value = loadInt("Four");
        fourText.setText(Integer.toString(four_value));

        five_value = loadInt("Five");
        fiveText.setText(Integer.toString(five_value));

        six_value = loadInt("Six");
        sixText.setText(Integer.toString(six_value));

        seven_value = loadInt("Seven");
        sevenText.setText(Integer.toString(seven_value));

        total_flow.setText("Total: +$" + NumberFormat.getNumberInstance(Locale.US).format(loadInt("flow")) + "/sec");

        if (buttonsArray != null) {
            current_button = buttonsArray[loadInt("ButtonArrayIndex")];
        }
    }
    @Override
    public void onRestart() {
        super.onRestart();
        MusicService.play_music(Income.this);
    }
}
