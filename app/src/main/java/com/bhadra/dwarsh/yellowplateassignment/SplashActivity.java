package com.bhadra.dwarsh.yellowplateassignment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends Activity {
    private FirebaseUser user;
    int splashtime = 3000;
    boolean active = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        user = FirebaseAuth.getInstance().getCurrentUser();
        Thread splashThread = new Thread() {
            @Override
            public void run() {
                try {
                    int wait = 0;
                    while (active & wait < splashtime) {
                        sleep(80);
                        wait = wait + 100;
                    }
                } catch (Exception e) {
                    Log.e("Splash",e.getMessage());
                } finally {
                    if (user != null) {
                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    } else {
                        finish();
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                }

            }
        };
        splashThread.start();
    }
}
