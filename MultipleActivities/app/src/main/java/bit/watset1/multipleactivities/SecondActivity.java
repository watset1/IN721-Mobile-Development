package bit.watset1.multipleactivities;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    Resources resourceResolver;
    TextView txtTitle;
    Button activityButton;
    String activityInfo;
    String buttonText;
    Uri internetUri;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resourceResolver = getResources();
        txtTitle = (TextView) findViewById(R.id.txtActivityInfo);
        activityButton = (Button) findViewById(R.id.btnChangeActivity);
        activityInfo = resourceResolver.getString(R.string.second_activity_title);
        buttonText = resourceResolver.getString(R.string.third_activity_button_name);
        address = resourceResolver.getString(R.string.google);
        txtTitle.setText(activityInfo);
        activityButton.setText(buttonText);

        //Button handling
        Button enrollButton = (Button) findViewById(R.id.btnChangeActivity);
        SecondActivity.ButtonOnClickHandler OnClickHandler = new SecondActivity.ButtonOnClickHandler();
        enrollButton.setOnClickListener(OnClickHandler);
    }

    public class ButtonOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            internetUri = Uri.parse(address);
            Intent internetIntent = new Intent(Intent.ACTION_VIEW, internetUri);
            startActivity(internetIntent);
        }
    }

}
