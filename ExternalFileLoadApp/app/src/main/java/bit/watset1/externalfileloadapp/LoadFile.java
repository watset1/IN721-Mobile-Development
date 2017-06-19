package bit.watset1.externalfileloadapp;

import android.app.Activity;
import android.content.res.AssetManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Emerson on 5/04/2017.
 */

public final class LoadFile
{

    public static ArrayList<String> LoadStringArray(Activity activity, String assetFileName)
    {
        ArrayList<String> stringHolder = new ArrayList<String>();

        AssetManager am = activity.getAssets();

        try
        {
            InputStream is = am.open(assetFileName);
            InputStreamReader ir = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(ir);

            String currentString;
            while((currentString = br.readLine()) != null)
            {
                stringHolder.add(currentString);
            }
            br.close();
        }
        catch (IOException e)
        {
            Toast.makeText(activity, "Could not load file", Toast.LENGTH_LONG).show();
        }

        return stringHolder;
    }
}
