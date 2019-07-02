package com.bhadra.dwarsh.yellowplateassignment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends Activity {
    private FirebaseUser user;
    int splashtime = 3000; // 3000 miliseconds = 3 seconds
    boolean active = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        user = FirebaseAuth.getInstance().getCurrentUser();
        //Thread to delay this activity for 3 sec
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
                        //User already Login goto MainActivity
                        finish();
                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    } else {
                        //New User goto to LoginPage
                        finish();
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                }

            }
        };
        splashThread.start();
    }
}
