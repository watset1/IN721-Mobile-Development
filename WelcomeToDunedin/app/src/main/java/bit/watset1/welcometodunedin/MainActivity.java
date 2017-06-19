package bit.watset1.welcometodunedin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ResourceBundle;

public class MainActivity extends AppCompatActivity
{
    Fragment listFragment;
    Fragment linkFragment;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horizontal_main);

        fm = getFragmentManager();
        listFragment = new NavigationFragment();
        linkFragment = new LinkFragment();
        FragmentTransaction ft = fm.beginTransaction();

        setContentView(R.layout.horizontal_main);
        ft.replace(R.id.fragment_container_left, listFragment);
        ft.replace(R.id.fragment_container_right, linkFragment);
        ft.commit();
    }
}
