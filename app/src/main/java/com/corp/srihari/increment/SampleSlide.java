package com.corp.srihari.increment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Srihari Vishnu on 2017-08-23.
 */

public class SampleSlide extends Fragment {

    private static final String ARG_LAYOUT_RES_ID = "layoutResId";
    private int layoutResId;
    private ImageView finger;
    private ImageView finger2;
    private ImageView finger3;
    private ImageView phone_screenshot;

    public static SampleSlide newInstance1(int layoutResId) {
        SampleSlide sampleSlide = new SampleSlide();
        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        sampleSlide.setArguments(args);

        return sampleSlide;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID)) {
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(layoutResId, container, false);
        if (layoutResId == R.layout.slide_1) {
            //slide 1 programming on create
        }
        else if (layoutResId == R.layout.slide_2) {
            //slide 2 programming
            finger = (ImageView) view.findViewById(R.id.imageView7);
            phone_screenshot = (ImageView) view.findViewById(R.id.imageView5);
            final TranslateAnimation gesture = new TranslateAnimation(Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, 250.0f, Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, 250.0f);
            final TranslateAnimation gesture2 = new TranslateAnimation(Animation.ABSOLUTE, 250.0f, Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, 250.0f, Animation.ABSOLUTE, 0.0f);
            final ScaleAnimation scaleDown = new ScaleAnimation(1.0f, 0.7f,1.0f,0.7f);
            final ScaleAnimation scaleUp = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f);

            scaleDown.setDuration(1000);
            scaleUp.setDuration(1000);

            gesture.setDuration(500);
            gesture.setFillAfter(true);
            gesture2.setDuration(3000);
            gesture2.setFillAfter(true);
            //gesture2.setInterpolator(new DecelerateInterpolator());
            finger.setAnimation(gesture);
            gesture.start();
            gesture.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    finger.setAnimation(gesture2);
                    gesture2.start();

                    Resources resources = getResources();
                    phone_screenshot.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.screenshot_before_click, null));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            scaleDown.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    finger.setAnimation(scaleUp);
                    scaleUp.start();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            scaleUp.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    finger.setAnimation(gesture);
                    gesture.start();

                    phone_screenshot.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.screenshot_after_click, null));


                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            gesture2.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    finger.setAnimation(scaleDown);
                    scaleDown.start();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });


        }
        else if (layoutResId == R.layout.slide_3) {
            //slide 3 programming
            finger2 = (ImageView) view.findViewById(R.id.imageView10);

            final ScaleAnimation scaleDown = new ScaleAnimation(1.0f, 0.7f,1.0f,0.7f);
            final ScaleAnimation scaleUp = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f);

            scaleDown.setDuration(1500);
            scaleUp.setDuration(1000);

            finger2.setAnimation(scaleDown);
            scaleDown.start();

            scaleDown.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    finger2.setAnimation(scaleUp);
                    scaleUp.start();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            scaleUp.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    finger2.setAnimation(scaleDown);
                    scaleDown.start();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        else if (layoutResId == R.layout.slide_4) {
            finger3 = (ImageView) view.findViewById(R.id.imageView13);
            final ScaleAnimation scaleDown = new ScaleAnimation(1.0f, 0.7f,1.0f,0.7f);
            final ScaleAnimation scaleUp = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f);

            scaleDown.setDuration(1500);
            scaleUp.setDuration(1000);

            finger3.setAnimation(scaleDown);
            scaleDown.start();
            scaleDown.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    finger3.setAnimation(scaleUp);
                    scaleUp.start();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            scaleUp.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    finger3.setAnimation(scaleDown);
                    scaleDown.start();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }


        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        MusicService.pause_music();
    }

    @Override
    public void onResume() {
        super.onResume();
        MusicService.play_music(getContext());
    }
}