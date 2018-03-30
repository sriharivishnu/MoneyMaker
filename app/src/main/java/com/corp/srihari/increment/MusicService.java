package com.corp.srihari.increment;
        import android.app.Activity;
        import android.app.ActivityManager;
        import android.app.Service;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.media.MediaPlayer;
        import android.os.Bundle;
        import android.os.IBinder;
        import android.support.v7.app.AppCompatActivity;

        import com.corp.srihari.increment.R;

        import static android.content.Context.MODE_PRIVATE;

public class MusicService {
    public static MediaPlayer bmusic;
    public Context c;
    public static String music_string;
    public MusicService(Context context) {
        this.c = context;
        this.bmusic = MediaPlayer.create(context, R.raw.background_music_v2_free_games);
        this.music_string = "MoneyMaker Main Theme";
    }
    public static void play_music(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        String packageName = am.getRunningTasks(1).get(0).topActivity.getPackageName();
        if (packageName.equals("com.corp.srihari.increment")) {
            if (Settings.loadBool("music",context)) {
                bmusic.setLooping(true);
                bmusic.start();
            }
        }
    }

    public static void change_music(Context context,String new_music) {
        if (Settings.loadBool("music", context)) {
            if (new_music.equals(music_string)) {
                //do nothing
            } else {
                if (new_music.equals("MoneyMaker Main Theme")) {
                    bmusic.stop();
                    bmusic.release();
                    bmusic = MediaPlayer.create(context, R.raw.background_music_v2_free_games);
                    bmusic.start();
                    music_string = "MoneyMaker Main Theme";
                } else if (new_music.equals("MoneyMaker 1")) {
                    bmusic.stop();
                    bmusic.release();
                    bmusic = MediaPlayer.create(context, R.raw.background_audio_v1);
                    bmusic.start();
                    music_string = "MoneyMaker 1";
                } else if (new_music.equals("MoneyMaker 2")) {
                    bmusic.stop();
                    bmusic.release();
                    bmusic = MediaPlayer.create(context, R.raw.bsound3);
                    bmusic.start();
                    music_string = "MoneyMaker 2";
                }
            }
        }
    }

    public static void pause_music() {
        if (bmusic.isPlaying()) {
            bmusic.pause();
        }
    }

    public static void stop_music() {
        if (bmusic.isPlaying()) {
            bmusic.stop();
        }
    }

    public static boolean isPlayingMusic() {
        return  bmusic.isPlaying();
    }
}