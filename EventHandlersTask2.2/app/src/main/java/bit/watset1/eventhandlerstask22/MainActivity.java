package bit.watset1.eventhandlerstask22;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static bit.watset1.eventhandlerstask22.R.id.usernameText;

public class MainActivity extends AppCompatActivity
{
    EditText usernameText;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameText = (EditText) findViewById(R.id.usernameText);

        EditTextOnKeyHandler OnKeyHandler = new EditTextOnKeyHandler();

        usernameText.setOnKeyListener(OnKeyHandler);
    }

    public class EditTextOnKeyHandler implements View.OnKeyListener
    {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent)
        {
            boolean correct = false;
            if(i == KeyEvent.KEYCODE_ENTER)
            {
                if ( usernameText.length() == 8)
                {
                    Toast.makeText(MainActivity.this, "Thank you", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Usernames must be 8 characters in length", Toast.LENGTH_SHORT).show();
                    correct = true;
                }
            }
            return correct;
        }
    }
}
