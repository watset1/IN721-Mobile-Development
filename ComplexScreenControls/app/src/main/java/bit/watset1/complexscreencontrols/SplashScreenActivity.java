package bit.watset1.complexscreencontrols;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Create sleep handler
        Handler sleep = new Handler();
        Transition transition = new Transition(SplashScreenActivity.this);
        sleep.postDelayed(transition, 5000);
    }

    //Sleep class with transition code
    public class Transition implements Runnable
    {
        public Transition(Activity activity) {}

        @Override
        public void run() {
            //Code to chnage activity
            Intent enrollmentIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(enrollmentIntent);
            finish();
        }
    }
}
