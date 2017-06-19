package bit.watset1.locationapp1;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Resources resources;
    Random random;
    Location location;
    TextView tvLatValue;
    TextView tvLongValue;
    TextView tvClosestCityValue;
    ImageView ivCityImage;
    Button btnTeleport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialising activity components
        resources = getResources();
        random = new Random();
        tvLatValue = (TextView) findViewById(R.id.tvLatValue);
        tvLongValue = (TextView) findViewById(R.id.tvLongValue);
        tvClosestCityValue = (TextView) findViewById(R.id.tvCityValue);
        ivCityImage = (ImageView) findViewById(R.id.ivCityImage);

        //Button Handling
        btnTeleport = (Button) findViewById(R.id.btnTeleport);
        btnTeleport.setOnClickListener(new TeleportOnClickHandler());
    }

    //generates a random set of coordinates as a location object
    private void generateLocation() {
        location = LocationGenerator.GenerateLocation(random);
    }

    //Displays a cities location
    private void displayLocation() {
        DecimalFormat df = new DecimalFormat(resources.getString(R.string.main_display_format));
        String latitude = df.format(location.getLatitude());
        String longitude = df.format(location.getLongitude());
        tvLatValue.setText(latitude);
        tvLongValue.setText(longitude);
    }

    //displays a cities name, region and country
    private void displayClosestCity(City closestCity) {
        String space = " ";
        String seperator = resources.getString(R.string.main_city_seperator);
        String cityString = closestCity.Name + seperator + space + closestCity.Region + seperator + space + closestCity.Country;
        tvClosestCityValue.setText(cityString);
    }

    //Displays a image related to the city
    private void displayCityImage(Bitmap image)
    {
        if(image == null)
            Toast.makeText(MainActivity.this, "No image available", Toast.LENGTH_SHORT).show();
        else
            ivCityImage.setImageBitmap(image);
    }

    //Button on click event handling
    public class TeleportOnClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            AsyncApiRequestClosestCity closestCityRequest = new AsyncApiRequestClosestCity();
            closestCityRequest.execute();
        }
    }

    //Async task that processes multiple http requests and displays the result
    public class AsyncApiRequestClosestCity extends AsyncTask<Void, Void, City>
    {
        ProgressDialog progressDialog;

        //Runs a in progress dialog to give user feedback
        private void runProgressDialog()
        {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage(resources.getString(R.string.main_loading_message));
            progressDialog.show();
        }

        //Retrieves city information using geoplugin
        private City processCityLocation()
        {
            City closestCity = null;
            String emptyQuery = resources.getString(R.string.main_empty_query);
            String closestCityJSONString = emptyQuery;
            //Runs until a non empty string is returned from the http query
            while (closestCityJSONString.equals(emptyQuery))
            {
                generateLocation();
                DecimalFormat df = new DecimalFormat(resources.getString(R.string.main_parse_format));
                String latitude = df.format(location.getLatitude());
                String longitude = df.format(location.getLongitude());
                String query = HttpManager.CreateQueryString(MainActivity.this, latitude, longitude, QueryType.GEOPLUGIN, null);
                closestCityJSONString = (String) HttpManager.HttpQuery(MainActivity.this, query, RequestType.JSON);
            }

            closestCity = JSONProcessor.GetClosestCity(closestCityJSONString);

            return closestCity;
        }

        //Retrieves city information using flickr
        private City processCityImage(City closestCity)
        {
            String query = HttpManager.CreateQueryString(MainActivity.this, closestCity.Latitude, closestCity.Longitude, QueryType.FLICKR, closestCity);
            String flickrJSONString = (String) HttpManager.HttpQuery(MainActivity.this, query, RequestType.JSON);
            String flickrUrl = JSONProcessor.GetCityImage(flickrJSONString);
            Bitmap image = null;
            if(!flickrUrl.equals("No image available"))
                image = (Bitmap)HttpManager.HttpQuery(MainActivity.this, flickrUrl, RequestType.BITMAP);

            closestCity.Image = image;

            return closestCity;
        }

        @Override
        protected void onPreExecute()
        {
            runProgressDialog();
        }

        @Override
        protected City doInBackground(Void... voids)
        {
            City closestCity = processCityLocation();
            return processCityImage(closestCity);
        }

        @Override
        protected void onPostExecute(City closestCity)
        {
            progressDialog.dismiss();
            displayLocation();
            displayClosestCity(closestCity);
            displayCityImage(closestCity.Image);
        }
    }
}
