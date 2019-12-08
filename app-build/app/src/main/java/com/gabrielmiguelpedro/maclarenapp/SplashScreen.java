package com.gabrielmiguelpedro.maclarenapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {

    /** Duração do splash screen **/
    private final int SPLASH_SCREEN_DELAY = 1500;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /** Após occorrer o tempo da constante, a activity splash screen irá fechar e irá abrir a activity de login**/

                Intent i = new Intent(SplashScreen.this,SignInActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_SCREEN_DELAY);
    }
}