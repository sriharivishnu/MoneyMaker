package com.corp.srihari.increment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.games.Games;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Srihari Vishnu on 2017-08-25.
 */

public class Settings extends Dialog implements android.view.View.OnClickListener {
    public Activity act;
    public Dialog dialog;
    public ImageButton close;
    public Spinner bmusic_choice;
    public Switch music_on_off;
    public Switch sound_effects;
    public static boolean music_state;
    public static boolean sound_effects_state;
    private Button credits;
    private Button reset_default;
    private ImageButton sign_out;

    public Settings(Activity a) {
        super(a);
        this.act = a;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.settings_popup);
        close = (ImageButton) findViewById(R.id.imageButton18);
        close.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(close, "scaleX", 0.9f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(close, "scaleY", 0.9f);
                        scaleDownX.setDuration(0);
                        scaleDownY.setDuration(0);

                        AnimatorSet scaleDown = new AnimatorSet();
                        scaleDown.play(scaleDownX).with(scaleDownY);

                        scaleDown.start();

                        close.setColorFilter(Color.argb(300, 300, 300, 1));

                        return true;
                    case MotionEvent.ACTION_UP:
                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(close, "scaleX", 1f);
                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(close, "scaleY", 1f);
                        scaleDownX2.setDuration(0);
                        scaleDownY2.setDuration(0);

                        AnimatorSet scaleDown2 = new AnimatorSet();
                        scaleDown2.play(scaleDownX2).with(scaleDownY2);

                        scaleDown2.start();

                        close.clearColorFilter();
                        dismiss();

                        return true;
                }
                return true;
            }
        });

        music_on_off = (Switch) findViewById(R.id.switch2);
        sound_effects = (Switch) findViewById(R.id.switch1);

        music_state = loadBool("music", act);
        sound_effects_state = loadBool("soundEffects", act);

        music_on_off.setChecked(music_state);
        sound_effects.setChecked(sound_effects_state);
        bmusic_choice = (Spinner) findViewById(R.id.spinner);
        if (!music_state) {
            bmusic_choice.setEnabled(false);
            bmusic_choice.setClickable(false);
        }

        music_on_off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // music is on
                    music_state = true;
                    saveBool("music", music_state, act);
                    MusicService.play_music(getContext());
                    bmusic_choice.setEnabled(true);
                    bmusic_choice.setClickable(true);
                }
                else {
                    //music is off
                    music_state = false;
                    saveBool("music", music_state, act);
                    MusicService.pause_music();
                    bmusic_choice.setEnabled(false);
                    bmusic_choice.setClickable(false);

                }
            }
        });

        sound_effects.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // music is on
                    sound_effects_state = true;
                    saveBool("soundEffects", sound_effects_state, act);
                }
                else {
                    //music is off
                    sound_effects_state = false;
                    saveBool("soundEffects", sound_effects_state, act);
                }
            }
        });

        credits = (Button) findViewById(R.id.button3);
        credits.setOnClickListener(this);

        CustomAlert c = new CustomAlert(act, "Custom");

        reset_default = (Button) findViewById(R.id.button18);
        reset_default.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.music_choices, android.R.layout.simple_spinner_item);
        bmusic_choice.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bmusic_choice.setAdapter(adapter);
        bmusic_choice.setSelection(adapter.getPosition(MusicService.music_string));
        bmusic_choice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MusicService.change_music(getContext(), parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO AUTOGENERATED METHOD STUB
            }
        });
        sign_out = (ImageButton) findViewById(R.id.sign_in_button);
        sign_out.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(sign_out, "scaleX", 0.9f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(sign_out, "scaleY", 0.9f);
                        scaleDownX.setDuration(0);
                        scaleDownY.setDuration(0);

                        AnimatorSet scaleDown = new AnimatorSet();
                        scaleDown.play(scaleDownX).with(scaleDownY);

                        scaleDown.start();

                        sign_out.setColorFilter(Color.argb(300, 300, 300, 1));

                        return true;
                    case MotionEvent.ACTION_UP:
                        if (MainActivity.mGoogleApiClient.isConnected()) {
                            CustomAlert customAlert2 = new CustomAlert(act, "Custom") {
                                @Override
                                public void onYesClicked() {
                                    MainActivity.mSignInClicked = false;
                                    Games.signOut(MainActivity.mGoogleApiClient);
                                    MainActivity.mGoogleApiClient.disconnect();
                                    Toast.makeText(act, "Signed Out", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onNoClicked() {
                                    dismiss();
                                }
                            };
                            customAlert2.show();
                            customAlert2.customize("Sign Out?", "Do you wish to sign out of Google Play?");
                        } else {
                            CustomAlert customAlert2 = new CustomAlert(act, "Custom") {
                                @Override
                                public void onYesClicked() {
                                    MainActivity.mSignInClicked = true;
                                    MainActivity.mGoogleApiClient.connect();
                                }
                            };
                            customAlert2.show();
                            customAlert2.customize("Sign in", "Sign into Google Games to access leaderboards");
                        }
                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(sign_out, "scaleX", 1f);
                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(sign_out, "scaleY", 1f);
                        scaleDownX2.setDuration(0);
                        scaleDownY2.setDuration(0);

                        AnimatorSet scaleDown2 = new AnimatorSet();
                        scaleDown2.play(scaleDownX2).with(scaleDownY2);

                        scaleDown2.start();

                        sign_out.clearColorFilter();
                        dismiss();

                        return true;
                }
                return true;
            }
        });

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton18:
                dismiss();
                break;
            case R.id.button3:
                CustomAlert customAlert = new CustomAlert(act, "Credits");
                customAlert.onYesClicked();
                customAlert.show();
            case R.id.sign_in_button:
                dismiss();
            case R.id.button18:
                CustomAlert customAlert1 = new CustomAlert(act, "Custom") {
                    @Override
                    public void onYesClicked() {
                        RecordAudio.reset_default();
                        Toast.makeText(act, "Button Sound Reset", Toast.LENGTH_SHORT).show();
                    }
                };
                customAlert1.show();
                customAlert1.customize("Reset Button Sound", "Reset main button sound to default? Warning! This will erase your recorded audio!");
            default:
                break;
        }
        dismiss();
    }

    public static void saveBool(String name, final boolean state, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Settings_Money",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(name, state);
        editor.commit();
    }
    public static boolean loadBool(String name, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Settings_Money",MODE_PRIVATE);
        return sharedPreferences.getBoolean(name, true);
    }
}
