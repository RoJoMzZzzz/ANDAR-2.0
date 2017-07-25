package com.research.andrade.andar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Delayed;

import io.fabric.sdk.android.Fabric;

public class SplashScreen extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "1h3yGRCRBld8xdNPenI8aDn21";
    private static final String TWITTER_SECRET = "nE6uINZxUicaHCmhwH3hmkP5dAsjlX9rMD917L7Lgi6R7YaSs6";
    private ImageView logo;
    private TextView one, two, three;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_splash_screen);

        logo = (ImageView) findViewById(R.id.imageView);
        one = (TextView) findViewById(R.id.txtAndroid);
        two = (TextView) findViewById(R.id.txtNatural);
        three = (TextView) findViewById(R.id.txtAlert);
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.grow_fade_in_from_bottom);
        Animation anim1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_left);
        logo.setAnimation(anim);
        one.setAnimation(anim1);
        two.setAnimation(anim1);
        three.setAnimation(anim1);


        Timer RunSplash = new Timer();
        TimerTask ShowSplash = new TimerTask() {
            @Override
            public void run() {
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        };

        RunSplash.schedule(ShowSplash, 3000);


       /* Thread myTh = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        myTh.start();*/
    }
}
