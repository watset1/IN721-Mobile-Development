package bit.watset1.externalfileloadapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lvTeams = (ListView)findViewById(R.id.lvTeams);
        ArrayList<String> teamsList = LoadFile.LoadStringArray(this, "nba_teams.txt");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, teamsList);
        lvTeams.setAdapter(adapter);
    }

}