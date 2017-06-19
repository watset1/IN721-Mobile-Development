package bit.watset1.requestingdata;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    TextView txtContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        txtContent = (TextView)findViewById(R.id.txtColouredText);
        ColorStateList color = txtContent.getTextColors();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("textColour", color);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
