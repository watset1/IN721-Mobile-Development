package bit.watset1.eventhandlers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button toastButton = (Button) findViewById(R.id.toastButton);
        EditText responsiveEditText = (EditText) findViewById(R.id.responsiveEditText);

        ButtonOnClickHandler OnClickHandler = new ButtonOnClickHandler();
        ButtonOnLongClickHandler OnLongClickHandler = new ButtonOnLongClickHandler();
        EditTextOnKeyHandler OnKeyHandler = new EditTextOnKeyHandler();

        toastButton.setOnClickListener(OnClickHandler);
        toastButton.setOnLongClickListener((OnLongClickHandler));
        responsiveEditText.setOnKeyListener(OnKeyHandler);
    }

    public class ButtonOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Toast.makeText(MainActivity.this, "Short Click", Toast.LENGTH_SHORT).show();
        }
    }

    public class ButtonOnLongClickHandler implements View.OnLongClickListener
    {
        @Override
        public boolean onLongClick(View view)
        {
            Toast.makeText(MainActivity.this, "Loooonnng Click", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public class EditTextOnKeyHandler implements View.OnKeyListener
    {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent)
        {
            if(i == KeyEvent.KEYCODE_AT)
                Toast.makeText(MainActivity.this, "Dont Type @", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
