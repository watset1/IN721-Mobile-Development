package bit.watset1.dialogfragments;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    BackgroundChange backgroundChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnChange = (Button)findViewById(R.id.btnChange);
        ChangeImageButtonHandler handler = new ChangeImageButtonHandler();
        btnChange.setOnClickListener(handler);
    }

    public void ProcessData(int imageId)
    {
        backgroundChange.dismiss();
        getWindow().getDecorView().setBackgroundResource(imageId);
    }

    public class ChangeImageButtonHandler implements View.OnClickListener
    {

        @Override
        public void onClick(View view) {
            backgroundChange = new BackgroundChange();
            FragmentManager fm = getFragmentManager();
            backgroundChange.show(fm, "change");
        }
    }
}
