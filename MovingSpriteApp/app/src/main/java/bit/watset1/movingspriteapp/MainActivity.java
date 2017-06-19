package bit.watset1.movingspriteapp;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;

import static android.hardware.Sensor.TYPE_ACCELEROMETER;
import static android.hardware.Sensor.TYPE_PROXIMITY;

public class MainActivity extends AppCompatActivity
{
    SensorManager sensorManager;
    Sensor accelerometer;
    SpriteManager spriteManager;
    ArrayList<ImageView> spriteHolders;
    ImageView ivSprite;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivSprite = (ImageView)findViewById(R.id.ivSprite);
        setSpriteHolders();

        //Sprite Management
        spriteManager = new SpriteManager(ivSprite, getBaseContext(), getResources(), spriteHolders);

        //Sensor Management
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(checkSensorAvailability(Sensor.TYPE_ACCELEROMETER))
        {
            AccelerationEventListener accelListener = new AccelerationEventListener();
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(accelListener, accelerometer, sensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private void setSpriteHolders()
    {
        spriteHolders = new ArrayList<ImageView>();
        //Set GuyBrush sprite display
        spriteHolders.add(Sprites.GUYBRUSH.ordinal(), ivSprite);
    }

    private boolean checkSensorAvailability(int sensor)
    {
        switch(sensor)
        {
            case TYPE_PROXIMITY:
                return (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) ;

            case TYPE_ACCELEROMETER:
                return (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) ;

            default:
                return false;
        }
    }

    public class AccelerationEventListener implements SensorEventListener
    {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float y = sensorEvent.values[0];
            float x = sensorEvent.values[1] * -1f;

            spriteManager.MoveSprite(x, y, Sprites.GUYBRUSH);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {}
    }
}
