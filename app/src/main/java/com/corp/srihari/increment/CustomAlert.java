package com.corp.srihari.increment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Srihari Vishnu on 2017-08-29.
 */

public class CustomAlert extends Dialog implements android.view.View.OnClickListener {
    public Activity act;
    private TextView message;
    private TextView title;
    private ImageButton ok_button;
    private ImageButton close_button;
    private String key;
    private ImageButton video_button;
    private TextView videos_remaining;


    public CustomAlert(Activity a, String k) {
        super(a);
        this.act = a;
        this.key = k;
    }

    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (key.equals("Custom")) {
            setContentView(R.layout.alert_dialog_plus);
            title = (TextView) findViewById(R.id.textView32);
            message = (TextView) findViewById(R.id.textView34);

            ok_button = (ImageButton) findViewById(R.id.imageButton26);

            ok_button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(ok_button, "scaleX", 0.9f);
                            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(ok_button, "scaleY", 0.9f);
                            scaleDownX.setDuration(0);
                            scaleDownY.setDuration(0);

                            AnimatorSet scaleDown = new AnimatorSet();
                            scaleDown.play(scaleDownX).with(scaleDownY);

                            scaleDown.start();

                            ok_button.setColorFilter(Color.argb(300, 300, 300, 1));

                            return true;
                        case MotionEvent.ACTION_UP:
                            onYesClicked();
                            ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(ok_button, "scaleX", 1f);
                            ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(ok_button, "scaleY", 1f);
                            scaleDownX2.setDuration(0);
                            scaleDownY2.setDuration(0);

                            AnimatorSet scaleDown2 = new AnimatorSet();
                            scaleDown2.play(scaleDownX2).with(scaleDownY2);

                            scaleDown2.start();

                            ok_button.clearColorFilter();
                            dismiss();

                            return true;
                    }
                    return true;
                }
            });
            close_button = (ImageButton) findViewById(R.id.imageButton25);
            close_button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(close_button, "scaleX", 0.9f);
                            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(close_button, "scaleY", 0.9f);
                            scaleDownX.setDuration(0);
                            scaleDownY.setDuration(0);

                            AnimatorSet scaleDown = new AnimatorSet();
                            scaleDown.play(scaleDownX).with(scaleDownY);

                            scaleDown.start();

                            close_button.setColorFilter(Color.argb(300, 300, 300, 1));

                            return true;
                        case MotionEvent.ACTION_UP:
                            onNoClicked();
                            ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(close_button, "scaleX", 1f);
                            ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(close_button, "scaleY", 1f);
                            scaleDownX2.setDuration(0);
                            scaleDownY2.setDuration(0);

                            AnimatorSet scaleDown2 = new AnimatorSet();
                            scaleDown2.play(scaleDownX2).with(scaleDownY2);

                            scaleDown2.start();

                            close_button.clearColorFilter();
                            dismiss();

                            return true;
                    }
                    return true;
                }
            });
        }  else if (key.equals("Custom2")) {
            setContentView(R.layout.ability_dialog);
            title = (TextView) findViewById(R.id.textView35);
            message = (TextView) findViewById(R.id.textView36);
            videos_remaining = (TextView) findViewById(R.id.textView37);
            videos_remaining.setText("(Videos Remaining: " + loadInt("VideosRemaining") + ")");

            ok_button = (ImageButton) findViewById(R.id.imageButton35);
            ok_button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(ok_button, "scaleX", 0.9f);
                            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(ok_button, "scaleY", 0.9f);
                            scaleDownX.setDuration(0);
                            scaleDownY.setDuration(0);

                            AnimatorSet scaleDown = new AnimatorSet();
                            scaleDown.play(scaleDownX).with(scaleDownY);

                            scaleDown.start();

                            ok_button.setColorFilter(Color.argb(300, 300, 300, 1));

                            return true;
                        case MotionEvent.ACTION_UP:
                            onYesClicked();
                            ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(ok_button, "scaleX", 1f);
                            ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(ok_button, "scaleY", 1f);
                            scaleDownX2.setDuration(0);
                            scaleDownY2.setDuration(0);

                            AnimatorSet scaleDown2 = new AnimatorSet();
                            scaleDown2.play(scaleDownX2).with(scaleDownY2);

                            scaleDown2.start();

                            ok_button.clearColorFilter();
                            dismiss();

                            return true;
                    }
                    return true;
                }
            });
            video_button = (ImageButton) findViewById(R.id.imageButton44);
            video_button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(video_button, "scaleX", 0.9f);
                            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(video_button, "scaleY", 0.9f);
                            scaleDownX.setDuration(0);
                            scaleDownY.setDuration(0);

                            AnimatorSet scaleDown = new AnimatorSet();
                            scaleDown.play(scaleDownX).with(scaleDownY);

                            scaleDown.start();

                            video_button.setColorFilter(Color.argb(300, 300, 300, 1));

                            return true;
                        case MotionEvent.ACTION_UP:
                            onVideo();
                            ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(video_button, "scaleX", 1f);
                            ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(video_button, "scaleY", 1f);
                            scaleDownX2.setDuration(0);
                            scaleDownY2.setDuration(0);

                            AnimatorSet scaleDown2 = new AnimatorSet();
                            scaleDown2.play(scaleDownX2).with(scaleDownY2);

                            scaleDown2.start();

                            video_button.clearColorFilter();
                            dismiss();

                            return true;
                    }
                    return true;
                }
            });
            close_button = (ImageButton) findViewById(R.id.imageButton27);
            close_button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(close_button, "scaleX", 0.9f);
                            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(close_button, "scaleY", 0.9f);
                            scaleDownX.setDuration(0);
                            scaleDownY.setDuration(0);

                            AnimatorSet scaleDown = new AnimatorSet();
                            scaleDown.play(scaleDownX).with(scaleDownY);

                            scaleDown.start();

                            close_button.setColorFilter(Color.argb(300, 300, 300, 1));

                            return true;
                        case MotionEvent.ACTION_UP:
                            onNoClicked();
                            ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(close_button, "scaleX", 1f);
                            ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(close_button, "scaleY", 1f);
                            scaleDownX2.setDuration(0);
                            scaleDownY2.setDuration(0);

                            AnimatorSet scaleDown2 = new AnimatorSet();
                            scaleDown2.play(scaleDownX2).with(scaleDownY2);

                            scaleDown2.start();

                            close_button.clearColorFilter();
                            dismiss();

                            return true;
                    }
                    return true;
                }
            });
        }
        else {
            setContentView(R.layout.alert_dialog);
            title = (TextView) findViewById(R.id.textView28);
            message = (TextView) findViewById(R.id.textView29);

            if (key.equals("Upgrades Insufficient")) {
                title.setText("Insufficient Funds");
                message.setText("You do not have enough money to purchase this upgrade.");
            } else if (key.equals("Revenue Insufficient")) {
                title.setText("Insufficient Funds");
                message.setText("You do not have enough money to purchase this investment.");
            } else if (key.equals("Previous Investment")) {
                title.setText("Alert");
                message.setText("You must purchase at least one of the previous investment to unlock this one.");
            } else if (key.equals("Previous Upgrade")) {
                title.setText("Alert");
                message.setText("You must purchase the previous upgrade to unlock this one.");
            } else if (key.equals("Credits")) {
                title.setText("Credits");
                message.setText("Music: BenSound, SoundBible, and FindSounds \n Logo: LogoMakr.com");
            } else if (key.equals("Abilities Insufficient")) {
                title.setText("Insufficient Funds");
                message.setText("You do not have enough money to purchase this ability.");
            }

            ok_button = (ImageButton) findViewById(R.id.imageButton20);
            ok_button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(ok_button, "scaleX", 0.9f);
                            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(ok_button, "scaleY", 0.9f);
                            scaleDownX.setDuration(0);
                            scaleDownY.setDuration(0);

                            AnimatorSet scaleDown = new AnimatorSet();
                            scaleDown.play(scaleDownX).with(scaleDownY);

                            scaleDown.start();

                            ok_button.setColorFilter(Color.argb(300, 300, 300, 1));

                            return true;
                        case MotionEvent.ACTION_UP:
                            onYesClicked();
                            ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(ok_button, "scaleX", 1f);
                            ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(ok_button, "scaleY", 1f);
                            scaleDownX2.setDuration(0);
                            scaleDownY2.setDuration(0);

                            AnimatorSet scaleDown2 = new AnimatorSet();
                            scaleDown2.play(scaleDownX2).with(scaleDownY2);

                            scaleDown2.start();

                            ok_button.clearColorFilter();
                            dismiss();

                            return true;
                    }
                    return true;
                }
            });
            close_button = (ImageButton) findViewById(R.id.imageButton19);
            close_button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(close_button, "scaleX", 0.9f);
                            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(close_button, "scaleY", 0.9f);
                            scaleDownX.setDuration(0);
                            scaleDownY.setDuration(0);

                            AnimatorSet scaleDown = new AnimatorSet();
                            scaleDown.play(scaleDownX).with(scaleDownY);

                            scaleDown.start();

                            close_button.setColorFilter(Color.argb(300, 300, 300, 1));

                            return true;
                        case MotionEvent.ACTION_UP:
                            onNoClicked();
                            ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(close_button, "scaleX", 1f);
                            ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(close_button, "scaleY", 1f);
                            scaleDownX2.setDuration(0);
                            scaleDownY2.setDuration(0);

                            AnimatorSet scaleDown2 = new AnimatorSet();
                            scaleDown2.play(scaleDownX2).with(scaleDownY2);

                            scaleDown2.start();

                            close_button.clearColorFilter();
                            dismiss();

                            return true;
                    }
                    return true;
                }
            });
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton20:
                dismiss();
                break;
            case R.id.imageButton19:
                dismiss();
                break;
            case R.id.imageButton26:
                dismiss();
            case R.id.imageButton25:
                dismiss();
            default:
                break;
        }
        dismiss();
    }

    public void customize(String title1, String message1) {
        if (key.equals("Custom")) {
            title = (TextView) findViewById(R.id.textView32);
            message = (TextView) findViewById(R.id.textView34);
            title.setText(title1);
            message.setText(message1);
        } else {
            title = (TextView) findViewById(R.id.textView28);
            message = (TextView) findViewById(R.id.textView29);
            title.setText(title1);
            message.setText(message1);
        }
    }

    public void onYesClicked() {
        //On Yes Clicked
    }
    public void onVideo() {
        //On video icon clicked
    }

    public void onNoClicked() {
        //On No Clicked
        dismiss();
    }
    public void center() {
        message.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
    }

    private void saveInt(String name, final int counter) {
        SharedPreferences sharedPreferences = act.getSharedPreferences("Settings_Money",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(name, counter);
        editor.commit();
    }
    private int loadInt(String name) {
        SharedPreferences sharedPreferences = act.getSharedPreferences("Settings_Money",MODE_PRIVATE);
        return sharedPreferences.getInt(name, 10);
    }

}
