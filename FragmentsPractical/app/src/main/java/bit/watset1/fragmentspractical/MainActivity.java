package bit.watset1.fragmentspractical;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    Button btnImage;
    Button btnList;

    Fragment dynamicFragment;
    FragmentManager fm;
    int orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check orientation before setting layout - Quick work-around
        orientation = this.getResources().getConfiguration().orientation;
        fm = getFragmentManager();
        ButtonOnClickHandler OnClickHandler = new ButtonOnClickHandler();

        if(orientation == 1)
        {
            setContentView(R.layout.activity_main);
            btnImage = (Button) findViewById(R.id.btnImage);
            btnList = (Button) findViewById(R.id.btnList);
        }
        else
        {
            setContentView(R.layout.horizontal_main);
            btnImage = (Button) findViewById(R.id.btnTabletImage);
            btnList = (Button) findViewById(R.id.btnTabletList);
        }

        btnImage.setOnClickListener(OnClickHandler);
        btnList.setOnClickListener(OnClickHandler);
    }

    public class ButtonOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Button pressedButton = (Button)view;
            String btnClicked = (String)pressedButton.getText();
            FragmentTransaction ft = fm.beginTransaction();

            if(btnClicked.equalsIgnoreCase("Image"))
            {
                dynamicFragment = new ImageFragment();
                if (orientation != 1)
                    ft.replace(R.id.fragment_container_left, dynamicFragment);
                else
                    ft.replace(R.id.fragment_container, dynamicFragment);
            }
            else
            {
                dynamicFragment = new ListViewFragment();
                if (orientation != 1)
                    ft.replace(R.id.fragment_container_right, dynamicFragment);
                else
                    ft.replace(R.id.fragment_container, dynamicFragment);
            }

            ft.commit();
        }
    }
}
