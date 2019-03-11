package com.example.freelancepracticemusic.views.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;

import com.example.freelancepracticemusic.R;
import com.example.freelancepracticemusic.views.home.HomeActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class SplashActivity extends AppCompatActivity {
    private CircleImageView mImageLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mImageLogo = findViewById(R.id.splash_logo);
        mImageLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotation_splash));

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent iHome = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(iHome);
                    finish();
                }
            }
        });

        thread.run();
    }
}
