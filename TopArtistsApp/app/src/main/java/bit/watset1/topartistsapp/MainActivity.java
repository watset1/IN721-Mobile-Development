package bit.watset1.topartistsapp;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    Resources resources;
    Button btnTopArtists;
    Button btnGo;
    Button btnImage;
    ListView lvMainList;
    EditText etArtist;
    ImageView ivArtistImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resources = getResources();

        btnTopArtists = (Button)findViewById(R.id.btnTopArtists);
        btnGo = (Button)findViewById(R.id.btnGo);
        btnImage = (Button)findViewById(R.id.btnTopArtistImage);
        btnTopArtists.setOnClickListener(new TopArtistsOnClickHandler());
        btnGo.setOnClickListener(new SimilarArtistsOnClickHandler());
        btnImage.setOnClickListener(new TopArtistImageOnClickHandler());

        lvMainList = (ListView)findViewById(R.id.lvArtistList);
        etArtist = (EditText)findViewById(R.id.edArtist);
        ivArtistImage = (ImageView)findViewById(R.id.ivArtistImage);
    }

    public class TopArtistsOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            AsyncApiDisplayTopArtists APIDisplayArtists = new AsyncApiDisplayTopArtists();
            APIDisplayArtists.execute();
        }
    }

    public class SimilarArtistsOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            AsyncApiDisplaySimilarArtists APIDisplayArtists = new AsyncApiDisplaySimilarArtists();
            APIDisplayArtists.execute();
        }
    }

    public class TopArtistImageOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            AsyncApiDisplayTopArtistImage APIDisplayTopArtistImage = new AsyncApiDisplayTopArtistImage();
            APIDisplayTopArtistImage.execute();
        }
    }

    private void bindListViewAdapter(ListView listView, ArrayList<String> data)
    {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, data);
        listView.setAdapter(arrayAdapter);
    }

    private String getEditTextValue(EditText editText)
    {
        return String.valueOf(editText.getText());
    }

    private void setArtistImage(Bitmap artistBitmap)
    {
        ivArtistImage.setImageBitmap(artistBitmap);
    }

    public class AsyncApiDisplayTopArtists extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... voids)
        {
            String topArtists = resources.getString(R.string.top_artists);
            String jsonFormat = resources.getString(R.string.format_json);
            String query = HttpManager.CreateLastFMQueryString(MainActivity.this, topArtists, jsonFormat, 20);
            String topArtistJsonString = (String)HttpManager.HttpQuery(MainActivity.this, query, QueryType.LASTFM);

            return topArtistJsonString;
        }

        @Override
        protected void onPostExecute(String s)
        {
            ArrayList<String> artists = JSONProcessor.GetTopArtists(s);
            bindListViewAdapter(lvMainList, artists);
        }
    }

    public class AsyncApiDisplaySimilarArtists extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... voids)
        {
            String artistInput = getEditTextValue(etArtist);
            String similarArtists = resources.getString(R.string.similar_artists);
            String jsonFormat = resources.getString(R.string.format_json);
            String query = HttpManager.CreateLastFMQueryString(MainActivity.this, similarArtists, artistInput, jsonFormat, 10);
            String similarArtistJsonString = (String)HttpManager.HttpQuery(MainActivity.this, query, QueryType.LASTFM);

            return similarArtistJsonString;
        }

        @Override
        protected void onPostExecute(String s)
        {
            ArrayList<String> artists = JSONProcessor.GetSimilarArtists(s);
            bindListViewAdapter(lvMainList, artists);
        }
    }

    public class AsyncApiDisplayTopArtistImage extends AsyncTask<Void, Void, ArtistImage>
    {
        @Override
        protected ArtistImage doInBackground(Void... voids)
        {
            //Top Artist http request and JSON processing
            String topArtists = resources.getString(R.string.top_artists);
            String jsonFormat = resources.getString(R.string.format_json);
            String query = HttpManager.CreateLastFMQueryString(MainActivity.this, topArtists, jsonFormat, 1);
            String topArtistJsonString = (String)HttpManager.HttpQuery(MainActivity.this, query, QueryType.LASTFM);
            ArrayList<String> topArtist = JSONProcessor.GetTopArtistImage(topArtistJsonString);
            String topArtistName = topArtist.get(0);

            //Bitmap http request and processing
            String bitmapUrl = topArtist.get(1);
            Bitmap topArtistImage = (Bitmap)HttpManager.HttpQuery(MainActivity.this, bitmapUrl, QueryType.BITMAPURL);

            return new ArtistImage(topArtistName, topArtistImage);
        }

        @Override
        protected void onPostExecute(ArtistImage topArtist)
        {
            ArrayList<String> artistName = new ArrayList<String>();
            artistName.add(topArtist.name);
            bindListViewAdapter(lvMainList, artistName);
            setArtistImage(topArtist.image);
        }
    }
}
