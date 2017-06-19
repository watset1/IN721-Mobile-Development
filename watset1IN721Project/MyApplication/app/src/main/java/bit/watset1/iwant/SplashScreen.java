package bit.watset1.iwant;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Timer;

public class SplashScreen extends AppCompatActivity
{
    ProgressDialog pd;
    ImageView ivLogo;
    Delay delay;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        pd = new ProgressDialog(this);

        ivLogo = (ImageView)findViewById(R.id.ivLogo);
        ivLogo.setOnClickListener(new LogoOnClickHandler());

        delay = new Delay(getResources().getInteger(R.integer.splash_delay), getResources().getInteger(R.integer.splash_interval));
        delay.start();
    }

    //Start intro activity
    private void runMainIntent()
    {
        pd.setMessage(getResources().getString(R.string.transition_loading));
        pd.show();
        Intent mainIntent = new Intent(SplashScreen.this, IntroActivity.class);
        startActivity(mainIntent);
        delay.cancel();
        finish();
    }

    public class LogoOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            runMainIntent();
        }
    }

    public class Delay extends CountDownTimer
    {
        public Delay(long millisInFuture, long countDownInterval)
        {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {}

        @Override
        public void onFinish()
        {
            runMainIntent();
        }
    }
}
