package bit.watset1.complexscreencontrols;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Spinner monthSpinner;
    Button enrollButton;
    Resources resourceResolver;
    RadioGroup instrumentGroup;
    TextView feedbackTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feedbackTextView = (TextView) findViewById(R.id.txtFeedback);
        instrumentGroup = (RadioGroup) findViewById(R.id.instrumentGroup);

        //Button handling
        enrollButton = (Button) findViewById(R.id.btnEnroll);
        ButtonOnClickHandler OnClickHandler = new ButtonOnClickHandler();
        enrollButton.setOnClickListener(OnClickHandler);

        //binding spinner control
        monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        resourceResolver = getResources();
        String months[] = resourceResolver.getStringArray(R.array.Months);
        bindSpinnerAdapter(monthSpinner, months);
    }

    //Method for spinner adapter binding
    private void bindSpinnerAdapter(Spinner spinner, String[] array)
    {
        int layoutID = android.R.layout.simple_spinner_item;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, layoutID, array );
        spinner.setAdapter(adapter);
    }

    //Method to return checked instrument in instrument radio group
    private String getCheckedInstrument()
    {
        int checkedId = instrumentGroup.getCheckedRadioButtonId();
        RadioButton selectedRdo = (RadioButton)findViewById(checkedId);
        String instrument = (String) selectedRdo.getText();

        return instrument;
    }

    //Method for displaying feedback
    private void displayFeedback(String instrument, String month)
    {
        feedbackTextView.setText("You have enrolled for " + instrument + " lessons beginning in " + month);
    }

    public class ButtonOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            String instrument = getCheckedInstrument();
            String month = (String) monthSpinner.getSelectedItem();
            displayFeedback(instrument, month);
        }
    }
}


