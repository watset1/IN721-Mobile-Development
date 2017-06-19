package bit.watset1.eventjsonapp;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    EventsJSONManager eventsJSONManager;
    Resources resources;
    ListView lvEvents;
    Button btnFill;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eventsJSONManager = new EventsJSONManager(this);
        resources = getResources();

        //Listview handling
        lvEvents = (ListView)findViewById(R.id.lvEventNames);
        lvEvents.setOnItemClickListener(new ListViewOnItemClickedHandler());

        //Button handling
        btnFill = (Button)findViewById(R.id.btnFillList);
        btnFill.setOnClickListener(new ButtonOnClickHandler());
    }

    //displays a list of dunedin event titles
    private void displayDunedinEvents()
    {
        String dunedin = resources.getString(R.string.main_event_city_dunedin);
        ArrayList<JSONObject> events = null;
        ArrayList<String> eventNames = null;
        try {
            events = eventsJSONManager.GetListOfEvents(dunedin);
            eventNames = eventsJSONManager.GetEventNames(events);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> eventListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, eventNames);
        lvEvents.setAdapter(eventListAdapter);
    }

    private void displayEventDescription(int position)
    {
        JSONObject event = null;
        String eventDescription = "";
        try {
            event = eventsJSONManager.GetListOfEvents(resources.getString(R.string.main_event_city_dunedin)).get(position);
            eventDescription = event.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(MainActivity.this, eventDescription , Toast.LENGTH_SHORT).show();
    }

    public class ButtonOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            displayDunedinEvents();
        }
    }

    public class ListViewOnItemClickedHandler implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
        {
            displayEventDescription(i);
        }
    }
}
