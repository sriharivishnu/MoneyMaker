package com.corp.srihari.increment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
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

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.Locale;

import static com.corp.srihari.increment.MainActivity._counter;
import static com.corp.srihari.increment.MainActivity.flow;
import static com.corp.srihari.increment.MainActivity.i;

public class Income2 extends AppCompatActivity {
    private Button current_button;
    private TextView flow_show;
    private ImageButton backButton;
    private Button[] buttonsArray;
    private TextView[] valuesArray;
    private ImageButton[] locksArray;

    private Button ibronze8;
    private Button ibronze9;
    private Button ibronze10;
    private Button ibronze11;
    private Button ibronze12;
    private Button ibronze13;
    private Button ibronze14;

    private TextView ibronze1;
    private TextView ibronze2;
    private TextView ibronze3;
    private TextView ibronze4;
    private TextView ibronze5;
    private TextView ibronze6;
    private TextView ibronze7;

    private TextView first;
    private TextView second;
    private TextView third;
    private TextView fourth;
    private TextView fifth;
    private TextView sixth;
    private TextView seventh;
    
    private int first_value = 0;
    private int second_value = 0;
    private int third_value = 0;
    private int fourth_value = 0;
    private int fifth_value = 0;
    private int sixth_value = 0;
    private int seventh_value = 0;

    private ImageButton ilock1;
    private ImageButton ilock2;
    private ImageButton ilock3;
    private ImageButton ilock4;
    private ImageButton ilock5;
    private ImageButton ilock6;
    
    private void purchaseIncome(TextView number,String name,int value, int money, int flow_increase) {
        if (_counter >= money) {
            _counter -= money;
            flow += flow_increase;
            int local_value = Integer.parseInt(number.getText().toString());
            local_value += 1;
            Log.d("TAG",Integer.toString(local_value));
            value = local_value;
            String str_value = Integer.toString(local_value);
            number.setText(str_value);
            if (Settings.loadBool("soundEffects",Income2.this)) {
                final MediaPlayer income_sound = MediaPlayer.create(Income2.this, R.raw.purchase_sound);
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
            flow_show.setText("Total: +$" + NumberFormat.getNumberInstance(Locale.US).format(loadInt("flow")) + "/sec");
            buttonsArray = new Button[] {ibronze8, ibronze9, ibronze10, ibronze11, ibronze12, ibronze13, ibronze14};
            locksArray = new ImageButton[] {ilock1,ilock2,ilock3,ilock4,ilock5, ilock6};

            if (local_value == 1) {
                current_button.setBackgroundResource(R.drawable.bronze_button);
                if (loadInt("bronzeIncome") + 1 < buttonsArray.length) {
                    int new_index = loadInt("bronzeIncome") + 1;
                    current_button = buttonsArray[new_index];
                    current_button.setClickable(true);
                    current_button.setEnabled(true);
                    current_button.setBackgroundResource(R.drawable.orange_button);
                    saveInt("bronzeIncome", new_index);
                    ImageButton l = locksArray[loadInt("bronzeIncome")-1];
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
                    save("uifinal", true);
                }
            }
        }
        else {
            CustomAlert customAlert = new CustomAlert(Income2.this, "Revenue Insufficient");
            customAlert.show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income2);

        backButton();

        ibronze1 = (TextView) findViewById(R.id.ibronze1);
        ibronze2 = (TextView) findViewById(R.id.ibronze2);
        ibronze3 = (TextView) findViewById(R.id.ibronze3);
        ibronze4 = (TextView) findViewById(R.id.ibronze4);
        ibronze5 = (TextView) findViewById(R.id.ibronze5);
        ibronze6 = (TextView) findViewById(R.id.ibronze6);
        ibronze7 = (TextView) findViewById(R.id.ibronze7);

        ibronze8 = (Button) findViewById(R.id.ibronze8);
        ibronze9 = (Button) findViewById(R.id.ibronze9);
        ibronze10 = (Button) findViewById(R.id.ibronze10);
        ibronze11 = (Button) findViewById(R.id.ibronze11);
        ibronze12 = (Button) findViewById(R.id.ibronze12);
        ibronze13 = (Button) findViewById(R.id.ibronze13);
        ibronze14 = (Button) findViewById(R.id.ibronze14);

        first = (TextView) findViewById(R.id.first);
        second = (TextView) findViewById(R.id.second);
        third = (TextView) findViewById(R.id.third);
        fourth = (TextView) findViewById(R.id.fourth);
        fifth = (TextView) findViewById(R.id.fifth);
        sixth = (TextView) findViewById(R.id.sixth);
        seventh = (TextView) findViewById(R.id.seventh);

        ilock1 = (ImageButton) findViewById(R.id.ilock1);
        ilock2 = (ImageButton) findViewById(R.id.ilock2);
        ilock3 = (ImageButton) findViewById(R.id.ilock3);
        ilock4 = (ImageButton) findViewById(R.id.ilock4);
        ilock5 = (ImageButton) findViewById(R.id.ilock5);
        ilock6 = (ImageButton) findViewById(R.id.ilock6);

        flow_show = (TextView) findViewById(R.id.current_value_i);
        flow_show.setText("Current Value = $" + Integer.toString(loadInt("step")) + "/click");

        buttonsArray = new Button[] {ibronze8, ibronze9, ibronze10, ibronze11, ibronze12, ibronze13, ibronze14};
        valuesArray = new TextView[] {first, second, third, fourth, fifth, sixth,seventh};
        locksArray = new ImageButton[] {ilock1,ilock2,ilock3,ilock4,ilock5, ilock6};

        current_button = buttonsArray[loadInt("bronzeIncome")];
        if (!current_button.equals(buttonsArray[buttonsArray.length - 1])) {
            current_button.setBackgroundResource(R.drawable.orange_button);
            current_button.setClickable(true);
            current_button.setEnabled(true);
        }
        for (int n = loadInt("bronzeIncome"); n<=buttonsArray.length - 1;n++) {
            if (buttonsArray[n] != current_button) {
                buttonsArray[n].setClickable(false);
                buttonsArray[n].setEnabled(false);
            }
        }

        buttonAnimationIncome(ibronze8,first, "UOne",first_value, 2042500,475);
        buttonAnimationIncome(ibronze9,second,"UTwo",second_value, 3150000,750);
        buttonAnimationIncome(ibronze10,third,"UThree",third_value, 6150000,1500);
        buttonAnimationIncome(ibronze11,fourth,"UFour",fourth_value, 10000000,2500);
        buttonAnimationIncome(ibronze12,fifth,"UFive",fifth_value, 19500000,5000);
        buttonAnimationIncome(ibronze13,sixth,"USix",sixth_value, 38000000,10000);
        buttonAnimationIncome(ibronze14,seventh,"USeven",seventh_value, 92500000,25000);

        onClickLock(ilock1);
        onClickLock(ilock2);
        onClickLock(ilock3);
        onClickLock(ilock4);
        onClickLock(ilock5);
        onClickLock(ilock6);

        for (int i=loadInt("bronzeIncome")-1;i>=0;i--) {
            ImageButton entry = locksArray[i];
            ((ViewManager) entry.getParent()).removeView(entry);

        }

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
    private void backButton() {
        backButton = (ImageButton) findViewById(R.id.ibacku_button);
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
                        if (Settings.loadBool("soundEffects",Income2.this)) {
                            final MediaPlayer mini_sound = MediaPlayer.create(Income2.this, R.raw.mini_button);
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
                        CustomAlert customAlert = new CustomAlert(Income2.this, "Previous Upgrade");
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
        saveInt("flow",flow);

    }
    @Override
    public void onResume() {
        super.onResume();
        MusicService.play_music(Income2.this);

        first_value = loadInt("UOne");
        first.setText(Integer.toString(first_value));

        second_value = loadInt("UTwo");
        second.setText(Integer.toString(second_value));

        third_value = loadInt("UThree");
        third.setText(Integer.toString(third_value));

        fourth_value = loadInt("UFour");
        fourth.setText(Integer.toString(fourth_value));

        fifth_value = loadInt("UFive");
        fifth.setText(Integer.toString(fifth_value));

        sixth_value = loadInt("USix");
        sixth.setText(Integer.toString(sixth_value));

        seventh_value = loadInt("USeven");
        seventh.setText(Integer.toString(seventh_value));

        flow_show.setText("Total: +$" + NumberFormat.getNumberInstance(Locale.US).format(loadInt("flow")) + "/sec");

        if (buttonsArray != null) {
            current_button = buttonsArray[loadInt("bronzeIncome")];
            if (current_button.equals(buttonsArray[buttonsArray.length-1])) {
                if (seventh_value == 0) {
                    current_button.setBackgroundResource(R.drawable.orange_button);
                }
            }
        }
    }
}
