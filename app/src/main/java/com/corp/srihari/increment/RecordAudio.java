package com.corp.srihari.increment;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.support.v4.app.ActivityCompat;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

public class RecordAudio extends AppCompatActivity {

    ImageButton record_button;
    public static String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder;
    Random random;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer ;
    Context context;
    Context application;
    Activity act;
    private boolean isDurationReached;

    public static boolean isRecording;

    public RecordAudio(Context application1, Activity a, Context context1, ImageButton imageButton) {
        this.record_button = imageButton;
        this.context = context1;
        this.act = a;
        this.application = application1;
        this.random = new Random();
    }

    public void main() {
            if (!isRecording) {

                if (checkPermission()) {
                    AudioSavePathInDevice = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "AudioRecording.3gp";
                    AudioSavePathInDevice = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "AudioRecording.3gp";

                    File file = new File(AudioSavePathInDevice);
                    if (file.length() != 0) {
                        file.delete();
                    }
                    MediaRecorderReady();

                    try {
                        MusicService.pause_music();
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                        record_button.setImageResource(R.drawable.stop_button);
                        record_button.setPadding(5,5,5,5);
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    Toast.makeText(act, "Recording started", Toast.LENGTH_SHORT).show();
                    isRecording = true;
                } else {
                    requestPermission();
                }
            } else {
                if (mediaRecorder != null) {
                    mediaRecorder.stop();
                    mediaRecorder.release();
                }
                isRecording = false;
                //Recording is finished
                MusicService.play_music(context);
                record_button.setImageResource(R.drawable.microphone_icon);
                record_button.setPadding(0,0,0,0);
                Toast.makeText(act, "Recording Completed", Toast.LENGTH_SHORT).show();
                CustomAlert customAlert = new CustomAlert(act, "Custom") {
                    @Override
                    public void onNoClicked() {
                        AudioSavePathInDevice = null;
                    }
                };
                if (!isDurationReached) {
                    customAlert.show();
                    customAlert.customize("Audio Recorded", "Set the audio recorded as main button sound? You can always reset to default sound effect in the settings menu.");

                    play_recording();
                }

                }

            }

    public static void play_recording() {
        final MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(AudioSavePathInDevice);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
            }
        });
    }

    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
        mediaRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
            @Override
            public void onInfo(MediaRecorder mr, int what, int extra) {
                isDurationReached = true;
                if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                    //MediaRecorder timed out
                    Log.d("AUDIOCAPTURE","Maximum Duration Reached");
                    mr.stop();
                    mr.release();
                    isRecording = false;
                    record_button.setImageResource(R.drawable.microphone_icon);
                    MusicService.play_music(context);
                    Toast.makeText(act, "Recording Completed", Toast.LENGTH_SHORT).show();
                    CustomAlert customAlert = new CustomAlert(act, "Custom") {
                        @Override
                        public void onNoClicked() {
                            AudioSavePathInDevice = null;
                        }
                    };
                    customAlert.show();
                    customAlert.customize("Audio Recorded", "Set the audio recorded as main button sound? You can always reset to default sound effect in the settings menu.");

                    play_recording();
                }
            }
        });

        mediaRecorder.setMaxDuration(3000);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(act, new String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(act, "Permission Granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(act,"Permission Denied",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(application, WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(application, RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    public static void reset_default() {
        AudioSavePathInDevice = null;
    }
}