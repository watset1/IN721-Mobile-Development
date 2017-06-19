package bit.watset1.datapassing;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity
{
    Resources resourceResolver;
    Button btnReturn;
    EditText etUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        resourceResolver = getResources();
        etUsername = (EditText)findViewById(R.id.etUsername);
        btnReturn = (Button) findViewById(R.id.btnReturn);

        ButtonOnClickHandler OnClickHandler = new ButtonOnClickHandler();
        btnReturn.setOnClickListener(OnClickHandler);
    }

    public class ButtonOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Intent activityReturnIntent = new Intent(SettingsActivity.this, MainActivity.class);
            String username = etUsername.getText().toString();
            if(CorrectLength(username)) {
                activityReturnIntent.putExtra("username", username);
                startActivity(activityReturnIntent);
            }
            else
                Toast.makeText(SettingsActivity.this, resourceResolver.getString(R.string.incorrect_length), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean CorrectLength(String username)
    {
        if(username.length() >= 3)
            return true;
        else
            return false;
    }


}
