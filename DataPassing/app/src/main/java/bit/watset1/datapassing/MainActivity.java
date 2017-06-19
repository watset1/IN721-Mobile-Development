package bit.watset1.datapassing;

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
    Button btnSettings;
    TextView txtUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resourceResolver = getResources();
        txtUsername = (TextView) findViewById(R.id.txtUsername);

        //Setting username
        setUsername();

        //Button handling
        btnSettings = (Button) findViewById(R.id.btnSettings);
        ButtonOnClickHandler OnClickHandler = new ButtonOnClickHandler();
        btnSettings.setOnClickListener(OnClickHandler);
    }

    public class ButtonOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Intent activityChangeIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(activityChangeIntent);
            finish();
        }
    }

    private void setUsername()
    {
        Intent settingsIntent = getIntent();
        String username = settingsIntent.getStringExtra("username");
        if(username != null)
            txtUsername.setText(username);
    }
}
