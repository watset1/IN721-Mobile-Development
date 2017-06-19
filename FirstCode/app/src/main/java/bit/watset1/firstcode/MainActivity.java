package bit.watset1.firstcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random random = new Random();

        TextView txtRandomString = (TextView)findViewById(R.id.dynamicText);

        String dogBreed = "";
        int breed = random.nextInt(4);

        switch (breed)
        {
            case 0:
                dogBreed = "Poodle";
                break;
            case 1:
                dogBreed = "Labrador";
                break;
            case 2:
                dogBreed = "Shar Pei";
                break;
            case 3:
                dogBreed = "Newfoundland";
                break;
        }

        txtRandomString.setText(dogBreed);
    }
}
