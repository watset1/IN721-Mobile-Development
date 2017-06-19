package bit.watset1.multipleactivities;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    Resources resourceResolver;
    TextView txtTitle;
    String activityInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resourceResolver = getResources();
        txtTitle = (TextView) findViewById(R.id.txtActivityInfo);
        activityInfo = resourceResolver.getString(R.string.main_activity_title);
        txtTitle.setText(activityInfo);

        //Button handling
        Button enrollButton = (Button) findViewById(R.id.btnChangeActivity);
        ButtonOnClickHandler OnClickHandler = new ButtonOnClickHandler();
        enrollButton.setOnClickListener(OnClickHandler);
    }

    public class ButtonOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Intent activityChangeIntent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(activityChangeIntent);
        }
    }
}
