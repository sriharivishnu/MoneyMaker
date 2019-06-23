package com.corp.srihari.increment;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.example.games.basegameutils.BaseGameUtils;
import com.google.android.gms.games.Games;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.games.leaderboard.Leaderboard;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.corp.srihari.increment.RecordAudio.AudioSavePathInDevice;
import static com.corp.srihari.increment.RecordAudio.play_recording;
import com.google.android.gms.games.GamesActivityResultCodes;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    private ImageButton _increase;
    private ImageButton _upgrades;
    private ImageButton _revenue;
    private ImageButton _menu;
    private ImageButton _settings;
    private ImageButton _help;
    private ImageButton _lock;
    private ImageButton _leaderboard;
    private ImageButton _microphone;
    private ImageView background;
    public static GoogleApiClient mGoogleApiClient;
    public static TextView _value;
    private Button upgrades_text;
    private Button revenue_text;
    private static TextView flow_show;
    public static long _counter = 0;
    public static int clickPerSec = 0;
    public static String _stringVal;
    public static int i = 1;
    public static int flow = 0;
    public static int inactivity_counter = 0;
    public static long highest_score = 0;
    public static boolean threadState = false;
    private boolean menuState = false;
    public boolean record_unlocked;
    private static boolean isBronzeUnlocked;

    private boolean runThread;
    public RecordAudio recordAudio;

    MusicService music;

    private AdView ad_banner;
    private RewardedVideoAd videoAd;

    private static int RC_SIGN_IN = 9001;

    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInFlow = true;
    public static boolean mSignInClicked = false;
    boolean mExplicitSignOut = false;
    boolean mInSignInFlow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent1 = new Intent(this,HomeActivity.class);
        startActivity(intent1);
        MusicService music = new MusicService(MainActivity.this);
        music.play_music(MainActivity.this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Games.API)
                .addScope(Games.SCOPE_GAMES)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();

        ad_banner = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        ad_banner.loadAd(adRequest);
        videoAd = MobileAds.getRewardedVideoAdInstance(MainActivity.this);
        loadRewardedVideoAd();

        highest_score = loadLong("highest");

        if (i>=50000) {
            background = (ImageView) findViewById(R.id.background_main);
            background.setVisibility(View.VISIBLE);
        }

        _increase = (ImageButton) findViewById(R.id.button1);
        flow_show = (TextView) findViewById(R.id.textView4);
        _value = (TextView) findViewById(R.id.textView);
        runThread = true;
        if (threadState == false) {
            (new Thread(new Runnable() {

                @Override
                public void run() {
                    while (!Thread.interrupted())
                        try {
                            Thread.sleep(1000);
                            runOnUiThread(new Runnable() // start actions in UI thread
                            {
                                @Override
                                public void run() {
                                    if (runThread) {
                                        inactivity_counter += 1;
                                        if (inactivity_counter >= 600) {
                                            if (inactivity_counter == 600) {
                                                CustomAlert inact = new CustomAlert(MainActivity.this, "") {
                                                    @Override
                                                    public void onYesClicked() {
                                                        inactivity_counter = 0;
                                                    }

                                                    @Override
                                                    public void onNoClicked() {
                                                        inactivity_counter = 0;
                                                    }
                                                };
                                                inact.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                                    @Override
                                                    public void onDismiss(DialogInterface dialog) {
                                                        inactivity_counter = 0;
                                                    }
                                                });
                                                inact.show();
                                                inact.customize("Inactivity", "Please continue by clicking OK");
                                                inact.center();
                                            }
                                        } else {
                                            _counter += flow;
                                            if (_counter >= 100000000) {
                                                if ( _counter >= 1000000000L) {
                                                    double a = (double) _counter/1000000000;
                                                    double roundOff = Math.round( a * 10000.00) / 10000.00;
                                                    _stringVal = "$" + roundOff + "B";
                                                    _value.setText(_stringVal);
                                                }
                                                else if (_counter >= 1000000000000L) {
                                                    double a = (double) _counter/1000000000000L;
                                                    double roundOff = Math.round(a*10000.00) / 10000.00;
                                                    _stringVal = "$" + roundOff + "T";
                                                    _value.setText(_stringVal);
                                                }
                                                else {
                                                    double a = (double) _counter / 1000000;
                                                    double roundOff = Math.round(a * 100.0) / 100.0;
                                                    _stringVal = "$" + roundOff + "M";
                                                    _value.setText(_stringVal);
                                                }
                                            } else {
                                                _stringVal = "$" + NumberFormat.getNumberInstance(Locale.US).format(_counter);
                                                _value.setText(_stringVal);
                                            }
                                            String flowShowVal="(+$" + NumberFormat.getNumberInstance(Locale.US).format((clickPerSec + flow)) + "/sec)";
                                            flow_show.setText(flowShowVal);
                                            clickPerSec = 0;
                                        }
                                    }
                                }
                            });
                        } catch (InterruptedException e) {
                            // ooops
                        }
                }
            })).start();
            threadState = true;
        }

        _increase.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(_increase, "scaleX", 0.9f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(_increase, "scaleY", 0.9f);
                        scaleDownX.setDuration(0);
                        scaleDownY.setDuration(0);

                        AnimatorSet scaleDown = new AnimatorSet();
                        scaleDown.play(scaleDownX).with(scaleDownY);

                        scaleDown.start();

                        _increase.setColorFilter(Color.argb(255, 255, 255, 1));

                        return true;
                    case MotionEvent.ACTION_UP:
                        _counter += i;
                        if (_counter > highest_score) {
                            highest_score = _counter;
                            saveLong("highest", highest_score);
                        }
                        if (_counter >= 100000000) {
                            if ( _counter >= 1000000000) {
                                double a = (double) _counter/1000000000;
                                double roundOff = Math.round( a * 10000.0) / 10000.0;
                                _stringVal = "$" + roundOff + "B";
                                _value.setText(_stringVal);
                            }
                            else if (_counter >= 1000000000000L) {
                                double a = (double) _counter/1000000000000L;
                                double roundOff = Math.round(a*10000.00) / 10000.00;
                                _stringVal = "$" + roundOff + "T";
                                _value.setText(_stringVal);
                            }
                            else {
                                double a = (double) _counter / 1000000;
                                double roundOff = Math.round(a * 100.0) / 100.0;
                                _stringVal = "$" + roundOff + "M";
                                _value.setText(_stringVal);
                            }
                        } else {
                            _stringVal = "$" + NumberFormat.getNumberInstance(Locale.US).format(_counter);
                            _value.setText(_stringVal);
                        }
                        clickPerSec += i;
                        inactivity_counter = 0;
                        if (Settings.loadBool("soundEffects",MainActivity.this)) {
                            if (AudioSavePathInDevice != null) {
                                    play_recording();
                            }
                            else {
                                //default
                                final MediaPlayer main_sound = MediaPlayer.create(MainActivity.this, R.raw.main_button_sound_v2);
                                main_sound.start();
                                main_sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mp) {
                                        main_sound.release();
                                    }

                                });
                            }
                        }
                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(_increase, "scaleX", 1f);
                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(_increase, "scaleY", 1f);
                        scaleDownX2.setDuration(0);
                        scaleDownY2.setDuration(0);

                        AnimatorSet scaleDown2 = new AnimatorSet();
                        scaleDown2.play(scaleDownX2).with(scaleDownY2);

                        scaleDown2.start();

                        _increase.clearColorFilter();

                        return true;
                }
                return true;
            }
        });

        String flow_value = "(+$" + Integer.toString(loadInt("flow")) + "/sec)";
        flow_show.setText(flow_value);

        _upgrades = (ImageButton) findViewById(R.id.imageButton);
        upgrades_text = (Button) findViewById(R.id.button4);
        _upgrades.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(_upgrades, "scaleX", 0.9f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(_upgrades, "scaleY", 0.9f);
                        ObjectAnimator scaleDownXtext = ObjectAnimator.ofFloat(upgrades_text, "scaleX", 0.9f);
                        ObjectAnimator scaleDownYtext = ObjectAnimator.ofFloat(upgrades_text, "scaleY", 0.9f);
                        scaleDownX.setDuration(0);
                        scaleDownY.setDuration(0);
                        scaleDownXtext.setDuration(0);
                        scaleDownYtext.setDuration(0);

                        AnimatorSet scaleDown = new AnimatorSet();
                        scaleDown.play(scaleDownX).with(scaleDownY);
                        scaleDown.play(scaleDownXtext).with(scaleDownYtext);

                        scaleDown.start();

                        _upgrades.setColorFilter(Color.argb(300, 300, 300, 1));

                        return true;
                    case MotionEvent.ACTION_UP:
                        inactivity_counter = 0;
                        if (Settings.loadBool("soundEffects",MainActivity.this)) {
                            final MediaPlayer bloop_sound = MediaPlayer.create(MainActivity.this, R.raw.main_button_sound);
                            bloop_sound.start();
                            bloop_sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    bloop_sound.release();
                                }

                            });
                        }
                        goToUpgrades(_upgrades);
                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(_upgrades, "scaleX", 1f);
                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(_upgrades, "scaleY", 1f);
                        ObjectAnimator scaleDownXtext2 = ObjectAnimator.ofFloat(upgrades_text, "scaleX", 1f);
                        ObjectAnimator scaleDownYtext2 = ObjectAnimator.ofFloat(upgrades_text, "scaleY", 1f);
                        scaleDownX2.setDuration(0);
                        scaleDownY2.setDuration(0);
                        scaleDownXtext2.setDuration(0);
                        scaleDownYtext2.setDuration(0);

                        AnimatorSet scaleDown2 = new AnimatorSet();
                        scaleDown2.play(scaleDownX2).with(scaleDownY2);
                        scaleDown2.play(scaleDownXtext2).with(scaleDownYtext2);

                        scaleDown2.start();

                        _upgrades.clearColorFilter();

                        return true;
                }
                return true;
            }
        });
        upgrades_text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(_upgrades, "scaleX", 0.9f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(_upgrades, "scaleY", 0.9f);
                        ObjectAnimator scaleDownXtext = ObjectAnimator.ofFloat(upgrades_text, "scaleX", 0.9f);
                        ObjectAnimator scaleDownYtext = ObjectAnimator.ofFloat(upgrades_text, "scaleY", 0.9f);
                        scaleDownX.setDuration(0);
                        scaleDownY.setDuration(0);
                        scaleDownXtext.setDuration(0);
                        scaleDownYtext.setDuration(0);

                        AnimatorSet scaleDown = new AnimatorSet();
                        scaleDown.play(scaleDownX).with(scaleDownY);
                        scaleDown.play(scaleDownXtext).with(scaleDownYtext);

                        scaleDown.start();

                        _upgrades.setColorFilter(Color.argb(300, 300, 300, 1));

                        return true;
                    case MotionEvent.ACTION_UP:
                        inactivity_counter = 0;
                        if (Settings.loadBool("soundEffects",MainActivity.this)) {
                            final MediaPlayer bloop_sound = MediaPlayer.create(MainActivity.this, R.raw.main_button_sound);
                            bloop_sound.start();
                            bloop_sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    bloop_sound.release();
                                }

                            });
                        }
                        goToUpgrades(_upgrades);
                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(_upgrades, "scaleX", 1f);
                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(_upgrades, "scaleY", 1f);
                        ObjectAnimator scaleDownXtext2 = ObjectAnimator.ofFloat(upgrades_text, "scaleX", 1f);
                        ObjectAnimator scaleDownYtext2 = ObjectAnimator.ofFloat(upgrades_text, "scaleY", 1f);
                        scaleDownX2.setDuration(0);
                        scaleDownY2.setDuration(0);
                        scaleDownXtext2.setDuration(0);
                        scaleDownYtext2.setDuration(0);

                        AnimatorSet scaleDown2 = new AnimatorSet();
                        scaleDown2.play(scaleDownX2).with(scaleDownY2);
                        scaleDown2.play(scaleDownXtext2).with(scaleDownYtext2);

                        scaleDown2.start();

                        _upgrades.clearColorFilter();

                        return true;
                }
                return true;
            }
        });
        _revenue = (ImageButton) findViewById(R.id.imageButton4);
        revenue_text = (Button) findViewById(R.id.button9);
        _revenue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(_revenue, "scaleX", 0.9f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(_revenue, "scaleY", 0.9f);
                        ObjectAnimator scaleDownXtext = ObjectAnimator.ofFloat(revenue_text, "scaleX", 0.9f);
                        ObjectAnimator scaleDownYtext = ObjectAnimator.ofFloat(revenue_text, "scaleY", 0.9f);
                        scaleDownX.setDuration(0);
                        scaleDownY.setDuration(0);
                        scaleDownXtext.setDuration(0);
                        scaleDownYtext.setDuration(0);

                        AnimatorSet scaleDown = new AnimatorSet();
                        scaleDown.play(scaleDownX).with(scaleDownY);
                        scaleDown.play(scaleDownXtext).with(scaleDownYtext);

                        scaleDown.start();

                        _revenue.setColorFilter(Color.argb(300, 300, 300, 1));

                        return true;
                    case MotionEvent.ACTION_UP:
                        inactivity_counter = 0;
                        if (Settings.loadBool("soundEffects",MainActivity.this)) {
                            final MediaPlayer bloop_sound = MediaPlayer.create(MainActivity.this, R.raw.main_button_sound);
                            bloop_sound.start();
                            bloop_sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    bloop_sound.release();
                                }

                            });
                        }
                        goToIncome(_revenue);
                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(_revenue, "scaleX", 1f);
                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(_revenue, "scaleY", 1f);
                        ObjectAnimator scaleDownXtext2 = ObjectAnimator.ofFloat(revenue_text, "scaleX", 1f);
                        ObjectAnimator scaleDownYtext2 = ObjectAnimator.ofFloat(revenue_text, "scaleY", 1f);
                        scaleDownX2.setDuration(0);
                        scaleDownY2.setDuration(0);
                        scaleDownXtext2.setDuration(0);
                        scaleDownYtext2.setDuration(0);

                        AnimatorSet scaleDown2 = new AnimatorSet();
                        scaleDown2.play(scaleDownX2).with(scaleDownY2);
                        scaleDown2.play(scaleDownXtext2).with(scaleDownYtext2);

                        scaleDown2.start();

                        _revenue.clearColorFilter();

                        return true;
                }
                return true;
            }
        });
        revenue_text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(_revenue, "scaleX", 0.9f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(_revenue, "scaleY", 0.9f);
                        ObjectAnimator scaleDownXtext = ObjectAnimator.ofFloat(revenue_text, "scaleX", 0.9f);
                        ObjectAnimator scaleDownYtext = ObjectAnimator.ofFloat(revenue_text, "scaleY", 0.9f);
                        scaleDownX.setDuration(0);
                        scaleDownY.setDuration(0);
                        scaleDownXtext.setDuration(0);
                        scaleDownYtext.setDuration(0);

                        AnimatorSet scaleDown = new AnimatorSet();
                        scaleDown.play(scaleDownX).with(scaleDownY);
                        scaleDown.play(scaleDownXtext).with(scaleDownYtext);

                        scaleDown.start();

                        _revenue.setColorFilter(Color.argb(300, 300, 300, 1));

                        return true;
                    case MotionEvent.ACTION_UP:
                        inactivity_counter = 0;
                        if (Settings.loadBool("soundEffects",MainActivity.this)) {
                            final MediaPlayer bloop_sound = MediaPlayer.create(MainActivity.this, R.raw.main_button_sound);
                            bloop_sound.start();
                            bloop_sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    bloop_sound.release();
                                }

                            });
                        }
                        goToIncome(revenue_text);
                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(_revenue, "scaleX", 1f);
                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(_revenue, "scaleY", 1f);
                        ObjectAnimator scaleDownXtext2 = ObjectAnimator.ofFloat(revenue_text, "scaleX", 1f);
                        ObjectAnimator scaleDownYtext2 = ObjectAnimator.ofFloat(revenue_text, "scaleY", 1f);
                        scaleDownX2.setDuration(0);
                        scaleDownY2.setDuration(0);
                        scaleDownXtext2.setDuration(0);
                        scaleDownYtext2.setDuration(0);

                        AnimatorSet scaleDown2 = new AnimatorSet();
                        scaleDown2.play(scaleDownX2).with(scaleDownY2);
                        scaleDown2.play(scaleDownXtext2).with(scaleDownYtext2);

                        scaleDown2.start();

                        _revenue.clearColorFilter();

                        return true;
                }
                return true;
            }
        });

        _menu = (ImageButton) findViewById(R.id.imageButton17);
        buttonAnimation(_menu);

        _leaderboard = (ImageButton) findViewById(R.id.leaderboard_button);
        buttonAnimation(_leaderboard);

        _settings = (ImageButton) findViewById(R.id.imageButton3);
        _settings.setClickable(false);
        _settings.setEnabled(false);

        _help = (ImageButton) findViewById(R.id.imageButton6);
        _help.setClickable(false);
        _help.setEnabled(false);

        _microphone = (ImageButton) findViewById(R.id.imageButton22);
        _microphone.setClickable(false);
        _microphone.setEnabled(false);

        _lock = (ImageButton) findViewById(R.id.imageButton24);
        _lock.setClickable(false);
        _lock.setEnabled(false);

        recordAudio  = new RecordAudio(getApplicationContext(),MainActivity.this, MainActivity.this, _microphone);

    }
    public void goToUpgrades (View view) {
        Intent intent = new Intent(this, Upgrades.class);
        startActivity(intent);
    }

    public void goToIncome(View view) {
        Intent intent = new Intent(this, Income.class);
        startActivity(intent);
    }

    public void goToHelp(View view) {
        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);
    }

    public void buttonAnimation(final ImageButton button) {
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

                        button.setColorFilter(Color.argb(300, 300, 300, 1));

                        return true;
                    case MotionEvent.ACTION_UP:
                        inactivity_counter = 0;
                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(button, "scaleX", 1f);
                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(button, "scaleY", 1f);
                        scaleDownX2.setDuration(0);
                        scaleDownY2.setDuration(0);

                        AnimatorSet scaleDown2 = new AnimatorSet();
                        scaleDown2.play(scaleDownX2).with(scaleDownY2);

                        scaleDown2.start();
                        if (Settings.loadBool("soundEffects",MainActivity.this)) {
                            final MediaPlayer mini_sound = MediaPlayer.create(MainActivity.this, R.raw.mini_button);
                            mini_sound.start();
                            mini_sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mini_sound.release();
                                }

                            });
                        }
                        if (button.equals(_menu)) {
                            if (menuState) {
                                hide_menu();
                                menuState = false;
                            } else {
                                show_menu();
                                menuState = true;
                            }
                        }
                        else if (button.equals(_settings)) {
                            show_settings();
                        }
                        else if (button.equals(_help)) {
                            goToHelp(_help);
                        }
                        else if (button.equals(_microphone)) {
                            recordAudio.main();
                        }
                        else if (button.equals(_leaderboard)) {
                            show_leaderboard();
                        }
                        else if (button.equals(_lock)) {
                            CustomAlert cud = new CustomAlert(MainActivity.this, "Custom2") {
                                @Override
                                public void onYesClicked() {
                                    if (_counter >= 100000) {
                                        _counter -= 100000;
                                        _stringVal = "$" + Long.toString(_counter);
                                        _value.setText(_stringVal);
                                        if (Settings.loadBool("soundEffects",MainActivity.this)) {
                                            final MediaPlayer upgrade_sound = MediaPlayer.create(MainActivity.this, R.raw.purchase_sound);
                                            upgrade_sound.start();
                                            upgrade_sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                @Override
                                                public void onCompletion(MediaPlayer mp) {
                                                    upgrade_sound.release();
                                                }

                                            });
                                        }
                                        saveBool("recordAbility", true, MainActivity.this);
                                        ((ViewManager) _lock.getParent()).removeView(_lock);

                                    } else {
                                        CustomAlert customAlert = new CustomAlert(MainActivity.this, "Abilities Insufficient");
                                        customAlert.show();
                                    }
                                }
                                @Override
                                public void onVideo() {
                                    //Show Video
                                    videoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
                                        @Override
                                        public void onRewardedVideoAdLoaded() {

                                        }

                                        @Override
                                        public void onRewardedVideoAdOpened() {

                                        }

                                        @Override
                                        public void onRewardedVideoStarted() {

                                        }

                                        @Override
                                        public void onRewardedVideoAdClosed() {
                                            loadRewardedVideoAd();

                                        }

                                        @Override
                                        public void onRewarded(RewardItem rewardItem) {
                                            //reward
                                            int vRemaining = loadInt2("VideosRemaining");
                                            vRemaining -= 1;
                                            saveInt("VideosRemaining", vRemaining);
                                            if (vRemaining == 0) {
                                                //No more videos required!
                                                MainActivity.saveBool("recordAbility", true, getContext());

                                                CustomAlert cdd = new CustomAlert(MainActivity.this, "sf") {
                                                    @Override
                                                    public void onYesClicked() {
                                                        final MediaPlayer victory = MediaPlayer.create(MainActivity.this, R.raw.ability_unlock);
                                                        victory.start();
                                                        victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                            @Override
                                                            public void onCompletion(MediaPlayer mp) {
                                                                victory.release();
                                                            }

                                                        });

                                                        AnimationSet animSet = new AnimationSet(true);
                                                        RotateAnimation ranim = new RotateAnimation(0f, 480f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                                        ranim.setDuration(1000);
                                                        ranim.setInterpolator(new DecelerateInterpolator());

                                                        animSet.addAnimation(ranim);

                                                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(_lock, "scaleX", 0.2f);
                                                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(_lock, "scaleY", 0.2f);
                                                        scaleDownX2.setDuration(1000);
                                                        scaleDownY2.setDuration(1000);

                                                        AnimatorSet scaleDown2 = new AnimatorSet();
                                                        scaleDown2.play(scaleDownX2).with(scaleDownY2);
                                                        scaleDown2.start();
                                                        _lock.startAnimation(animSet);
                                                        ((ViewManager)_lock.getParent()).removeView(_lock);

                                                    }

                                                    @Override
                                                    public void onNoClicked() {
                                                        final MediaPlayer victory = MediaPlayer.create(MainActivity.this, R.raw.ability_unlock);
                                                        victory.start();
                                                        victory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                            @Override
                                                            public void onCompletion(MediaPlayer mp) {
                                                                victory.release();
                                                            }

                                                        });

                                                        AnimationSet animSet = new AnimationSet(true);
                                                        RotateAnimation ranim = new RotateAnimation(0f, 480f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                                        ranim.setDuration(1000);
                                                        ranim.setInterpolator(new DecelerateInterpolator());

                                                        animSet.addAnimation(ranim);

                                                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(_lock, "scaleX", 0.2f);
                                                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(_lock, "scaleY", 0.2f);
                                                        scaleDownX2.setDuration(1000);
                                                        scaleDownY2.setDuration(1000);

                                                        AnimatorSet scaleDown2 = new AnimatorSet();
                                                        scaleDown2.play(scaleDownX2).with(scaleDownY2);
                                                        scaleDown2.start();
                                                        _lock.startAnimation(animSet);
                                                        ((ViewManager)_lock.getParent()).removeView(_lock);
                                                    }
                                                };
                                                cdd.show();
                                                cdd.customize("Ability Unlocked", "You have watched 10 videos! You have now unlocked the Record Ability!");
                                                _microphone.setClickable(true);
                                                _microphone.setEnabled(true);
                                                buttonAnimation(_microphone);

                                            }
                                        }

                                        @Override
                                        public void onRewardedVideoAdLeftApplication() {

                                        }

                                        @Override
                                        public void onRewardedVideoAdFailedToLoad(int i) {
                                            loadRewardedVideoAd();

                                        }
                                    });
                                    if (videoAd.isLoaded()) {
                                        videoAd.show();
                                    }
                                    loadRewardedVideoAd();
                                }
                            };
                            cud.show();
                        }

                        button.clearColorFilter();

                        return true;
                }
                return true;
            }
        });
    }
    public void show_menu() {
        _menu.setColorFilter(0xFF000000);
        _menu.setImageResource(R.drawable.arrow_down_white);
        _menu.setPadding(5,5,5,5);

        _settings.setVisibility(View.VISIBLE);
        _help.setVisibility(View.VISIBLE);
        _microphone.setVisibility(View.VISIBLE);

        _settings.setClickable(true);
        _settings.setEnabled(true);
        buttonAnimation(_settings);

        _help.setClickable(true);
        _help.setEnabled(true);
        buttonAnimation(_help);

        record_unlocked = loadBool("recordAbility", MainActivity.this);
        if (record_unlocked) {
            _microphone.setClickable(true);
            _microphone.setEnabled(true);
            buttonAnimation(_microphone);
            if (findViewById(R.id.imageButton24) != null) {
                ((ViewManager) _lock.getParent()).removeView(_lock);
            }
        } else {
            _lock.setVisibility(View.VISIBLE);
            _lock.setClickable(true);
            _lock.setEnabled(true);
            buttonAnimation(_lock);
        }
    }
    public void hide_menu() {
        _menu.clearColorFilter();
        _menu.setImageResource(R.drawable.menu_icon_v2);
        _menu.setPadding(0,0,0,0);

        _settings.setVisibility(View.INVISIBLE);
        _help.setVisibility(View.INVISIBLE);
        _microphone.setVisibility(View.INVISIBLE);
        if (_lock != null) {
            _lock.setVisibility(View.INVISIBLE);
            _lock.setClickable(false);
            _lock.setEnabled(false);
        }

        _settings.setClickable(false);
        _settings.setEnabled(false);

        _help.setClickable(false);
        _help.setEnabled(false);

        _microphone.setClickable(false);
        _microphone.setEnabled(false);

    }
    public void show_settings() {
        Settings cdd = new Settings(MainActivity.this);
        cdd.show();
    }
    public void show_leaderboard() {
        if (mGoogleApiClient.isConnected()) {
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,"CgkI_MqV-8cDEAIQAQ"), 100);
            mSignInClicked = true;
            mGoogleApiClient.connect();
        } else {
            CustomAlert isConnected = new CustomAlert(MainActivity.this, "Custom") {
                @Override
                public void onYesClicked() {
                    mSignInClicked = true;
                    mGoogleApiClient.connect();
                }
            };
            isConnected.show();
            isConnected.customize("Sign in?", "Sign into Google Play Games?");

        }

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                mGoogleApiClient.connect();
            } else {
                Log.d("tag", (Integer.toString(resultCode)));
                BaseGameUtils.showActivityResultError(this, requestCode, resultCode, R.string.sign_in_failed);
            }
        }
    }

    private void loadRewardedVideoAd() {
        videoAd.loadAd("ca-app-pub-3495964067534428/7160539760", new AdRequest.Builder().build());
    }

    public void saveInt(String name, final int counter) {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings_Money",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(name, counter);
        editor.commit();
    }
    public int loadInt(String name) {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings_Money",MODE_PRIVATE);
        if (name.equals("step")) {
            return sharedPreferences.getInt(name, 1);
        }
        else {
            return sharedPreferences.getInt(name, 0);
        }
    }
    public static void saveBool(String name, final boolean bool, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Settings_Money", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(name,bool);
        editor.commit();
    }
    public static boolean loadBool(String name, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Settings_Money", MODE_PRIVATE);
        return sharedPreferences.getBoolean(name, false);
    }

    private int loadInt2(String name) {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings_Money",MODE_PRIVATE);
        return sharedPreferences.getInt(name, 10);
    }

    private void saveLong(String name, long counter) {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings_Money", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(name, counter);
        editor.apply();
    }
    private long loadLong(String name) {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings_Money",MODE_PRIVATE);
        try {
            return sharedPreferences.getLong(name, 9900000000L);
        }
        catch (ClassCastException e) {
            saveLong(name, Long.parseLong(Integer.toString(loadInt(name))));
            return sharedPreferences.getLong(name, 0L);
        }
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (mResolvingConnectionFailure) {
            // already resolving
            return;
        }

        // if the sign-in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow
        if (mSignInClicked || mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;

            // Attempt to resolve the connection failure using BaseGameUtils.
            // The R.string.signin_other_error value should reference a generic
            // error string in your strings.xml file, such as "There was
            // an issue with sign-in, please try again later."
            if (!BaseGameUtils.resolveConnectionFailure(this, mGoogleApiClient, connectionResult, RC_SIGN_IN, R.string.sign_in_failed)) {
                mResolvingConnectionFailure = false;
            }
        }

        // Put code here to display the sign-in button
    }

    @Override
    public void onConnectionSuspended(int i) {
        // Attempt to reconnect
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        // The player is signed in. Hide the sign-in button and allow the
        // player to proceed.
    }

    @Override
    public void onPause() {
        super.onPause();
        music.pause_music();
        threadState = false;
        runThread = false;
        Log.d("yo2","Pause");
        saveLong("money", _counter);
        saveInt("step",i);
        saveInt("flow",flow);
        saveLong("highest", highest_score);
        if (mGoogleApiClient.isConnected()) {
            Games.Leaderboards.submitScore(mGoogleApiClient, "CgkI_MqV-8cDEAIQAQ", highest_score);
        }
    }
    @Override
    public void onResume() {

        super.onResume();
        Log.d("yo","Resume");
        runThread = true;
        threadState = false;
        music.play_music(MainActivity.this);
        _counter = loadLong("money");
        if (_counter >= 100000000) {
            if ( _counter >= 1000000000) {
                double a = (double) _counter/1000000000;
                double roundOff = Math.round( a * 10000.00) / 10000.00;
                _stringVal = "$" + roundOff + "B";
                _value.setText(_stringVal);
            }
            else if (_counter >= 1000000000000L) {
                double a = (double) _counter/1000000000000L;
                double roundOff = Math.round(a*10000.00) / 10000.00;
                _stringVal = "$" + roundOff + "T";
                _value.setText(_stringVal);
            }
            else {
                double a = (double) _counter / 1000000;
                double roundOff = Math.round(a * 100.0) / 100.0;
                _stringVal = "$" + roundOff + "M";
                _value.setText(_stringVal);
            }
        } else {
            _stringVal = "$" + NumberFormat.getNumberInstance(Locale.US).format(_counter);
            _value.setText(_stringVal);
        }
        flow_show.setText("(+$" + Integer.toString(loadInt("flow")) + "/sec)");
        i = loadInt("step");
        flow = loadInt("flow");
        highest_score = loadLong("highest");
        if (i>=50000) {
            background = (ImageView) findViewById(R.id.background_main);
            background.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        music.play_music(MainActivity.this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (!mInSignInFlow && !mExplicitSignOut) {
            // auto sign in
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
}
