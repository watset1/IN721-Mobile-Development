package bit.watset1.resourcepractical;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    final String FEBFRIDAYINTRO = "February Fridays are on: ";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView febFridayText = (TextView)findViewById(R.id.textDisplay);

        Resources resourceResolver = getResources();
        int datesArray[] = resourceResolver.getIntArray(R.array.FebFridays);

        String displayString = FEBFRIDAYINTRO;
        for (int i = 0; i < datesArray.length; i++)
            displayString += datesArray[i] + " ";

        febFridayText.setText(displayString);
    }
}
