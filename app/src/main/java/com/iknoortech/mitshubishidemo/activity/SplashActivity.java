package com.iknoortech.mitshubishidemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.iknoortech.mitshubishidemo.R;
import com.iknoortech.mitshubishidemo.activity.feedback.FeedbackActivity;
import com.iknoortech.mitshubishidemo.activity.feedback.FeedbackDetailActivity;
import com.iknoortech.mitshubishidemo.utils.AppPrefference;

import java.util.Arrays;

@SuppressLint("NewApi")
public class SplashActivity extends AppCompatActivity {

    private ImageView imageView;
    private static final String TAG = "SplashActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseMessaging.getInstance().subscribeToTopic("ANDROID_NEWS");

        imageView = findViewById(R.id.img_splash);

         if(getIntent().hasExtra("pushnotification")){
             startActivity(new Intent(SplashActivity.this, FeedbackDetailActivity.class));
             finish();
         }else{
             Animation fadeIn = AnimationUtils
                     .loadAnimation(SplashActivity.this, R.anim.fade_in);
             imageView.startAnimation(fadeIn);

             new Handler().postDelayed(new Runnable() {
                 @Override
                 public void run() {
                     startActivity(new Intent(SplashActivity.this, MainActivity.class));
                     finish();
                 }
             }, 1800);
         }

    }

    private void createFeedLauncherShortcut() {
        ShortcutManager shortcutManagerFeedback = getSystemService(ShortcutManager.class);

        Intent feedbackIntent = new Intent(this, FeedbackActivity.class);
        feedbackIntent.setAction(Intent.ACTION_VIEW);


        ShortcutInfo shortcutInfoFeedback = new ShortcutInfo.Builder(this, "feedback")
                .setShortLabel("Feedback Activity")
                .setIcon(Icon.createWithResource(this, R.mipmap.feedback_icon))
                .setIntent(feedbackIntent)
                .build();

        shortcutManagerFeedback.setDynamicShortcuts(Arrays.asList(shortcutInfoFeedback));
    }

    private void createSurveyShortcut() {
        ShortcutManager shortcutManagerSurvey = getSystemService(ShortcutManager.class);

        Intent surveyIntent = new Intent(this, SurvayActivity.class);
        surveyIntent.setAction(Intent.ACTION_VIEW);


        ShortcutInfo shortcutInfoSurvey = new ShortcutInfo.Builder(this, "survey")
                .setShortLabel("SurveyData Activity")
                .setIcon(Icon.createWithResource(this, R.mipmap.survey_icon))
                .setIntent(surveyIntent)
                .build();

        shortcutManagerSurvey.setDynamicShortcuts(Arrays.asList(shortcutInfoSurvey));
    }
}
