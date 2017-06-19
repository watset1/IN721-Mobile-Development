package bit.watset1.locationapp1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Emerson on 4/05/2017.
 */

public final class JSONProcessor
{
    //Prepares a JSON stream and returns it as a string
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

    //Processes a JSON string to return a City object
    public static City GetClosestCity(String JSONString)
    {
        City closestCity = null;

        try
        {
            if(!JSONString.equals("[[]]"))
            {
                JSONObject data = new JSONObject(JSONString);
                String city = data.getString("geoplugin_place");
                String country = data.getString("geoplugin_countryCode");
                String region = data.getString("geoplugin_region");
                String latitude = data.getString("geoplugin_latitude");
                String longitude = data.getString("geoplugin_longitude");

                closestCity = new City(city, region, country, latitude, longitude);

                return closestCity;
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return closestCity;
    }

    //Returns a string containing an image url
    public static String GetCityImage(String JSONString)
    {
        String imageUrl = "No image available";

        try
        {
            JSONObject data = new JSONObject(JSONString);
            JSONObject photos = data.getJSONObject("photos");
            JSONArray photoArray = photos.getJSONArray("photo");

            Boolean available = false;
            int nPhotos = photoArray.length();
            int currentPhoto = 0;
            while(!available && currentPhoto < nPhotos)
            {
                JSONObject photo = photoArray.getJSONObject(currentPhoto);
                int isPublic = photo.getInt("ispublic");
                if(isPublic == 1)
                {
                    available = true;
                    int farmId = photo.getInt("farm");
                    String serverId = photo.getString("server");
                    String photoId = photo.getString("id");
                    String secret = photo.getString("secret");

                    imageUrl = "https://farm" + farmId + ".staticflickr.com/" + serverId + "/" + photoId + "_" + secret + "_m.jpg";
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return imageUrl;
    }
}
