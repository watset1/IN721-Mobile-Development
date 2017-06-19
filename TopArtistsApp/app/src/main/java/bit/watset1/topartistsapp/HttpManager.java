package bit.watset1.topartistsapp;

import android.app.Activity;
import android.graphics.Bitmap;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Emerson on 15/04/2017.
 */

public final class HttpManager
{
    public static String CreateLastFMQueryString(Activity activity, String method, String format, int limit)
    {
        String rootUrl = "http://ws.audioscrobbler.com/2.0/?";
        String separator = "&";
        String fullMethod = "method=" + method;
        String fullFormat = separator + "format=" + format;
        String api_key = separator + "api_key=" + getApiKey(activity, "last_FM");
        String fullLimit = "";
        if(limit != 0)
            fullLimit = separator + "limit=" + Integer.toString(limit);

        return rootUrl + fullMethod + api_key + fullLimit + fullFormat;
    }

    public static String CreateLastFMQueryString(Activity activity, String method, String artist, String format, int limit)
    {
        String rootUrl = "http://ws.audioscrobbler.com/2.0/?";
        String separator = "&";
        String fullMethod = "method=" + method;
        String fullFormat = separator + "format=" + format;
        String fullArtist = separator + "artist=" + artist;
        String api_key = separator + "api_key=" + getApiKey(activity, "last_FM");
        String fullLimit = "";
        if(limit != 0)
            fullLimit = separator + "limit=" + Integer.toString(limit);

        return rootUrl + fullMethod + fullArtist + api_key + fullLimit + fullFormat;
    }

    public static Object HttpQuery(Activity activity, String query, QueryType queryType)
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
            if(queryType.equals(QueryType.BITMAPURL))
                return BitmapProcessor.ParseBitmapData(inputStream);
            else
                return JSONProcessor.PrepareJSONString(inputStream);
        }
        else
            return "No data collected";
    }

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

    private static String getApiKey(Activity activity, String ApiKeyType)
    {
        String assetFileName = ApiKeyType + "_api_key.txt";

        return FileLoad.LoadStringArray(activity, assetFileName).get(0);
    }
}
