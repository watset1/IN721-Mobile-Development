package bit.watset1.eventjsonapp;

import android.app.Activity;
import android.content.res.AssetManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Emerson on 7/04/2017.
 */

public class ParsedEventsJSON
{
    final String FILENAME = "dunedin_events_2017.json";
    JSONObject eventsData;

    public ParsedEventsJSON(Activity activity)
    {
        readInFile(activity);
    }

    private void readInFile(Activity activity)
    {
        AssetManager am = activity.getAssets();
        InputStream inputStream = null;
        int fileSizeInBytes = 0;

        try {
            inputStream = am.open(FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fileSizeInBytes = inputStream.available();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] JSONBuffer = new byte[fileSizeInBytes];

        try {
            inputStream.read(JSONBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String JSONinput = new String(JSONBuffer);
        try {
            eventsData = new JSONObject(JSONinput);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
