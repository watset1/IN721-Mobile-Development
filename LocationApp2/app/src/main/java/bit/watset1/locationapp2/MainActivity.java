package bit.watset1.locationapp2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity
{
    Resources resources;
    LocationManager locationManager;
    String providerName;
    Location location;
    TextView tvLatValue;
    TextView tvLongValue;
    TextView tvClosestCityValue;
    ImageView ivCityImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialising activity components
        resources = getResources();
        tvLatValue = (TextView) findViewById(R.id.tvLatValue);
        tvLongValue = (TextView) findViewById(R.id.tvLongValue);
        tvClosestCityValue = (TextView) findViewById(R.id.tvCityValue);
        ivCityImage = (ImageView) findViewById(R.id.ivCityImage);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        boolean locationPermissionsOk = checkLocationPermission();
        if(locationPermissionsOk)
        {
            //setLocationUpdates
            providerName = locationManager.getBestProvider(new Criteria(), false);
            locationManager.requestLocationUpdates(providerName, 500, 1, new CustomLocationListener());
            location = locationManager.getLastKnownLocation(providerName);
            runAsync();
        }
        else
        {
            requestLocationPermission();
        }
    }

    public boolean checkLocationPermission()
    {
        int fineLocationOk = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocationOk = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        if(fineLocationOk != PackageManager.PERMISSION_GRANTED || coarseLocationOk != PackageManager.PERMISSION_GRANTED)
            return false;
        else
            return true;
    }

    public void requestLocationPermission()
    {
        String[] permissionsIWant = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};

        ActivityCompat.requestPermissions(this, permissionsIWant, 10);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            checkLocationPermission();
            //setLocationUpdates();
            providerName = locationManager.getBestProvider(new Criteria(), false);
            locationManager.requestLocationUpdates(providerName, 500, 1, new CustomLocationListener());
        }
        else
        {

        }
    }


//    private void setLocationUpdates()
//    {
//        providerName = locationManager.getBestProvider(new Criteria(), false);
//        locationManager.requestLocationUpdates(providerName, 500, 1, new CustomLocationListener());
//    }

    //Displays a cities location
    private void displayLocation()
    {
        DecimalFormat df = new DecimalFormat(resources.getString(R.string.main_display_format));
        String latitude = df.format(location.getLatitude());
        String longitude = df.format(location.getLongitude());
        tvLatValue.setText(latitude);
        tvLongValue.setText(longitude);
    }

    //displays a cities name, region and country
    private void displayClosestCity(City closestCity)
    {
        String space = " ";
        String seperator = resources.getString(R.string.main_city_seperator);
        String cityString = closestCity.Name + seperator + space + closestCity.Region + seperator + space + closestCity.Country;
        tvClosestCityValue.setText(cityString);
    }

    private void displayNoCity()
    {
        tvClosestCityValue.setText(R.string.main_no_city);
    }

    //Displays a image related to the city
    private void displayCityImage(Bitmap image)
    {
        if (image == null)
            Toast.makeText(MainActivity.this, "No image available", Toast.LENGTH_SHORT).show();
        else
            ivCityImage.setImageBitmap(image);
    }

    private void runAsync()
    {
        AsyncApiRequestClosestCity cityRequest = new AsyncApiRequestClosestCity();
        cityRequest.execute();
    }


    private class CustomLocationListener implements LocationListener
    {
        @Override
        public void onLocationChanged(Location location)
        {
            MainActivity.this.location = location;
            runAsync();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

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
            DecimalFormat df = new DecimalFormat(resources.getString(R.string.main_parse_format));
            String latitude = df.format(location.getLatitude());
            String longitude = df.format(location.getLongitude());
            String query = HttpManager.CreateQueryString(MainActivity.this, latitude, longitude, QueryType.GEOPLUGIN, null);
            closestCityJSONString = (String) HttpManager.HttpQuery(MainActivity.this, query, RequestType.JSON);

            closestCity = JSONProcessor.GetClosestCity(closestCityJSONString);

            return closestCity;
        }

        //Retrieves city information using flickr
        private City processCityImage(City closestCity)
        {
            if(closestCity != null)
            {
                String query = HttpManager.CreateQueryString(MainActivity.this, closestCity.Latitude, closestCity.Longitude, QueryType.FLICKR, closestCity);
                String flickrJSONString = (String) HttpManager.HttpQuery(MainActivity.this, query, RequestType.JSON);
                String flickrUrl = JSONProcessor.GetCityImage(flickrJSONString);
                Bitmap image = null;
                if (!flickrUrl.equals("No image available"))
                    image = (Bitmap) HttpManager.HttpQuery(MainActivity.this, flickrUrl, RequestType.BITMAP);

                closestCity.Image = image;
            }

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
            if(closestCity != null)
            {
                displayClosestCity(closestCity);
                displayCityImage(closestCity.Image);
            }
            else
                displayNoCity();
        }
    }
}
