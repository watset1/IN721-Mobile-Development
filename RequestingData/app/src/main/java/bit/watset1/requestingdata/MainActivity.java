package bit.watset1.requestingdata;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Resources resourceResolver;
    Button btnChangeText;
    TextView txtChangedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resourceResolver = getResources();
        txtChangedText = (TextView)findViewById(R.id.txtChangedText);

        btnChangeText = (Button)findViewById(R.id.btnChange);
        ButtonOnClickHandler clickHandler = new ButtonOnClickHandler();
        btnChangeText.setOnClickListener(clickHandler);
    }

    public class ButtonOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Intent changeColourIntent = new Intent(MainActivity.this, SettingsActivity.class);

            startActivityForResult(changeColourIntent, 0);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 0)
        {
            ColorStateList newColour = (ColorStateList)data.getParcelableExtra("textColour");
            txtChangedText.setTextColor(newColour);
        }
    }
}
