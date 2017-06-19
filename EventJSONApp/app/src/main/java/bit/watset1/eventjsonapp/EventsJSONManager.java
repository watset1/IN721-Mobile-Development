package bit.watset1.eventjsonapp;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Emerson on 7/04/2017.
 */

public class EventsJSONManager
{
    ArrayList<JSONObject> dunedinEvents;
    JSONObject eventsObject;

    public EventsJSONManager(Activity activity)
    {
        ParsedEventsJSON jsonData = new ParsedEventsJSON(activity);
        eventsObject = jsonData.eventsData;
        dunedinEvents = new ArrayList<JSONObject>();
    }

    public ArrayList<JSONObject> GetListOfEvents(String city) throws JSONException {
        ArrayList<JSONObject> events = new ArrayList<JSONObject>();
        JSONObject eventData = eventsObject.optJSONObject("events");
        JSONArray eventsArray = eventData.optJSONArray("event");

        int nEvents = eventsArray.length();
        for (int i = 0; i < nEvents; i++)
        {
            JSONObject currentEventObject = null;
            try {
                currentEventObject = eventsArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String currentEventCity = currentEventObject.getString("city_name");

            if(currentEventCity.equals(city))
            {
                events.add(currentEventObject);
            }
        }

        return events;
    }

    public ArrayList<String> GetEventNames(ArrayList<JSONObject> events) throws JSONException {
        ArrayList<String> eventNames = new ArrayList<String>();
        int nEvents = events.size();
        for (int i = 0; i < nEvents; i++)
        {
            String eventName = events.get(i).getString("title");
            eventNames.add(eventName);
        }
        return eventNames;
    }
}
