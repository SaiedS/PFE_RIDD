package com.example.demo_drive_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashEcran extends AppCompatActivity implements Animation.AnimationListener{

    ImageView imageView;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_ecran);

        imageView = findViewById(R.id.Splash_logo);
        animation = AnimationUtils.loadAnimation(SplashEcran.this,R.anim.splash_anim);
        animation.setAnimationListener(this);
        imageView.startAnimation(animation);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashEcran.this,WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}