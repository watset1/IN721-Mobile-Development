package bit.watset1.topartistsapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Emerson on 15/04/2017.
 */

public final class JSONProcessor
{
    public static String PrepareJSONString(InputStream inputStream)
    {
        InputStreamReader sr = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(sr);

        String responseString;
        StringBuilder stringBuilder = new StringBuilder();

        try
        {
            while((responseString = br.readLine()) != null)
            {
                stringBuilder = stringBuilder.append(responseString);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public static ArrayList<String> GetTopArtists(String JSONString)
    {
        ArrayList<String> artists = new ArrayList<String>();

        try
        {
            JSONObject data = new JSONObject(JSONString);
            JSONObject artistsObj = data.optJSONObject("artists");
            JSONArray artistArray = artistsObj.optJSONArray("artist");
            int nArtists = artistArray.length();
            for (int i = 0; i < nArtists; i++)
            {
                JSONObject currentArtistObject = null;
                String artistString = "";
                currentArtistObject = artistArray.getJSONObject(i);
                String name = currentArtistObject.getString("name");
                String listeners = currentArtistObject.getString("listeners");
                artistString = name + "\t\t\t\t\t\t" + listeners;

                artists.add(artistString);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return artists;
    }

    public static ArrayList<String> GetSimilarArtists(String JSONString)
    {
        ArrayList<String> artists = new ArrayList<String>();

        try
        {
            JSONObject data = new JSONObject(JSONString);
            JSONObject artistsObj = data.optJSONObject("similarartists");
            JSONArray artistArray = artistsObj.optJSONArray("artist");
            int nArtists = artistArray.length();
            for (int i = 0; i < nArtists; i++)
            {
                JSONObject currentArtistObject = null;
                String artistString = "";
                currentArtistObject = artistArray.getJSONObject(i);
                String name = currentArtistObject.getString("name");

                artists.add(name);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return artists;
    }

    public static ArrayList<String> GetTopArtistImage(String JSONString)
    {
        ArrayList<String> artistInfo = new ArrayList<String>();

        try
        {
            JSONObject data = new JSONObject(JSONString);
            JSONObject artistsObj = data.optJSONObject("artists");
            JSONArray artistArray = artistsObj.optJSONArray("artist");
            JSONObject topArtist = artistArray.getJSONObject(0);
            artistInfo.add(topArtist.getString("name"));
            JSONArray artistImageArray = topArtist.getJSONArray("image");
            JSONObject mediumImage = artistImageArray.getJSONObject(ImageSize.XLARGE.ordinal());
            artistInfo.add(mediumImage.getString("#text"));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return artistInfo;
    }
}
