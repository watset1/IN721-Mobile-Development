package bit.watset1.locationapp2;

import android.app.Activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Emerson on 5/05/2017.
 */

public final class HttpManager
{
    //Returns a query string based on query type specified at runtime
    public static String CreateQueryString(Activity activity, String latitude, String longitude, QueryType queryType, City city)
    {
        String queryString = "";
        String rootUrl = "";

        String separator = "&";
        String fullLatitude = "lat=" + latitude + separator;
        String fullLongitude = "long=" + longitude + separator;
        String fullFormat = "format=json";

        switch(queryType)
        {
            case GEOPLUGIN:
                rootUrl = "http://www.geoplugin.net/extras/location.gp?";
                queryString = rootUrl + fullLatitude + fullLongitude + fullFormat;
                break;

            case FLICKR:
                String apiKey = "api_key=" + FileLoad.LoadStringArray(activity, "flickr_api_key.txt").get(0) + separator;
                String callback = separator + "nojsoncallback=1";
                String cityName = city.Name;
                cityName = cityName.replaceAll("\\s+","+");
                String text = separator + "tags=" + cityName;
                rootUrl = "https://api.flickr.com/services/rest/?method=flickr.photos.search&";
                queryString = rootUrl + apiKey + fullFormat + callback + text;
                break;
        }

        return queryString;
    }

    //Retrieves the api key from text file stored in assets folder
    private static String getApiKey(Activity activity, String ApiKeyType)
    {
        String assetFileName = ApiKeyType + "_api_key.txt";

        return FileLoad.LoadStringArray(activity, assetFileName).get(0);
    }

    //Returns an object dependant on the type of http request
    public static Object HttpQuery(Activity activity, String query, RequestType requestType)
    {

        URL urlObject = ConvertUrlString(activity, query);

        HttpURLConnection connection = CreateHttpUrlConnection(activity, urlObject);
        int responseCode = 0;
        InputStream inputStream = null;

        try
        {
            responseCode = connection.getResponseCode();
            inputStream = connection.getInputStream();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if(responseCode == 200)
        {
            switch (requestType)
            {
                case BITMAP:
                    return BitmapProcessor.ParseBitmapData(inputStream);

                case JSON:
                    return JSONProcessor.PrepareJSONString(inputStream);

                default:
                    return "No data collected";
            }

        }
        else
            return "No data collected";
    }

    //Converts the query string to a URL object
    private static URL ConvertUrlString(Activity activity, String query)
    {
        URL urlObject = null;
        try
        {
            urlObject = new URL(query);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        return urlObject;
    }

    //Returns a http url connection
    private static HttpURLConnection CreateHttpUrlConnection(Activity activity, URL urlObject)
    {
        HttpURLConnection connection = null;

        try
        {
            connection = (HttpURLConnection) urlObject.openConnection();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            connection.connect();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return connection;
    }
}
