package bit.watset1.sharedpreferencesapp;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor preferenceEditor;
    Preferences preferences;
    Resources resourceResolver;
    Spinner languageSpinner;
    Spinner colorSpinner;
    TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resourceResolver = getResources();
        languageSpinner = (Spinner) findViewById(R.id.spinnerLanguage);
        colorSpinner = (Spinner) findViewById(R.id.spinnerColor);
        tvMessage = (TextView) findViewById(R.id.tvLanguageMessage);

        //SharedPreferences
        String prefName = resourceResolver.getString(R.string.preference_name);
        sharedPreferences = getSharedPreferences(prefName, MODE_PRIVATE);
        preferenceEditor = sharedPreferences.edit();

        //Get arrays for spinner
        String[] languages = resourceResolver.getStringArray(R.array.Languages);
        String[] colors = resourceResolver.getStringArray(R.array.Colors);

        //Bind spinners
        bindSpinnerAdapter(languageSpinner, languages);
        bindSpinnerAdapter(colorSpinner, colors);

        //Button handling
        Button btnSetPreferences  = (Button)findViewById(R.id.btnSavePreferences);
        btnSetPreferences.setOnClickListener(new ButtonOnClickHandler());
        //Set message text view
        updateTextView();
    }

    //Method for spinner adapter binding
    private void bindSpinnerAdapter(Spinner spinner, String[] array)
    {
        int layoutID = android.R.layout.simple_spinner_item;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, layoutID, array );
        spinner.setAdapter(adapter);
    }

    public class ButtonOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            String language = (String) languageSpinner.getSelectedItem();
            String color = (String) colorSpinner.getSelectedItem();
            setColor(color);
            setLanguage(language);
            updateTextView();
        }
    }

    //Should really use abstract factory pattern here
    private String getLanguageMessage(String chosenLanguage)
    {
        String message = "";

        switch (chosenLanguage)
        {
            case "English":
                message  = resourceResolver.getString(R.string.main_message_english);
                break;

            case "Spanish":
                message = resourceResolver.getString(R.string.main_message_spanish);
                break;

            case "German":
                message = resourceResolver.getString(R.string.main_message_german);
                break;

            case "French":
                message = resourceResolver.getString(R.string.main_message_french);
                break;

            case "Default":
                message = resourceResolver.getString(R.string.main_message_default);
                break;
        }

        return message;
    }

    private int getLanguageColor(String chosenColor)
    {
        int color = -1;

        switch(chosenColor)
        {
            case "Blue":
                color = resourceResolver.getColor(R.color.language_blue);
                break;

            case "Red":
                color = resourceResolver.getColor(R.color.language_red);
                break;

            case "Green":
                color = resourceResolver.getColor(R.color.language_green);
                break;

            case "Purple":
                color = resourceResolver.getColor(R.color.language_purple);
                break;

            case "Default":
                color = resourceResolver.getColor(R.color.language_black);
                break;
        }

        return color;
    }

    private void setLanguage(String language)
    {
        preferenceEditor.putString("language", language);
        preferenceEditor.commit();
    }

    private void setColor(String color)
    {
        preferenceEditor.putString("color", color);
        preferenceEditor.commit();
    }

    private ArrayList<String> getPreferences()
    {
        ArrayList<String> preferences = new ArrayList<String>();
        String language = sharedPreferences.getString("language", null);
        String color = sharedPreferences.getString("color", null);

        if(language == null)
            language = "Default";
        if(color == null)
            color = "Default";

        preferences.add(language);
        preferences.add(color);

        return preferences;
    }

    private void setMessage(String message, int color)
    {
        tvMessage.setTextColor(color);
        tvMessage.setText(message);
    }

    private void updateTextView()
    {
        ArrayList<String> preferences = getPreferences();
        String message = getLanguageMessage(preferences.get(EPreferences.LANGUAGE.ordinal()));
        int color = getLanguageColor(preferences.get(EPreferences.COLOR.ordinal()));
        setMessage(message, color);
    }
}
