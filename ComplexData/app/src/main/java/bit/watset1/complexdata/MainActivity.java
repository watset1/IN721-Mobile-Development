package bit.watset1.complexdata;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Resources resources;
    ArrayList<Team> atlantic;
    ArrayList<Team> central;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resources = getResources();
        String[] divisions = { "Atlantic", "Central" };
        createTeamArrays();

        ArrayAdapter<String> divisionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, divisions);
        ListView divisionList = (ListView)findViewById(R.id.lvDivisions);
        divisionList.setAdapter(divisionAdapter);
        ListViewHandler listHandler = new ListViewHandler();
        divisionList.setOnItemClickListener(listHandler);
    }

    public class ListViewHandler implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String clickedItemString = (String)adapterView.getItemAtPosition(i);

            Intent intent = new Intent(MainActivity.this, TeamsActivity.class);

            switch(clickedItemString)
            {
                case "Atlantic":
                    intent.putExtra("teams", atlantic);
                    break;

                case "Central":
                    intent.putExtra("teams", central);
                    break;
            }

            startActivity(intent);
        }
    }

    private void createTeamArrays()
    {
        atlantic = new ArrayList<Team>();
        atlantic.add(new Team(resources.getIdentifier("celtics", "drawable", this.getPackageName()), "Boston Celtics"));
        atlantic.add(new Team(resources.getIdentifier("nets", "drawable", this.getPackageName()), "Brooklyn Nets"));
        atlantic.add(new Team(resources.getIdentifier("knicks", "drawable", this.getPackageName()), "New York Knicks"));
        atlantic.add(new Team(resources.getIdentifier("seventysixers", "drawable", this.getPackageName()), "Philadelphia 76ers"));
        atlantic.add(new Team(resources.getIdentifier("raptors", "drawable", this.getPackageName()), "Toronto Raptors"));

        central = new ArrayList<Team>();
        central.add(new Team(resources.getIdentifier("bulls", "drawable", this.getPackageName()), "Chicago Bulls"));
        central.add(new Team(resources.getIdentifier("cavs", "drawable", this.getPackageName()), "Cleveland Cavaliers"));
        central.add(new Team(resources.getIdentifier("pistons", "drawable", this.getPackageName()), "Detroit Pistons"));
        central.add(new Team(resources.getIdentifier("pacers", "drawable", this.getPackageName()), "Indiana Pacers"));
        central.add(new Team(resources.getIdentifier("bucks", "drawable", this.getPackageName()), "Milwaukee Bucks"));
    }
}
