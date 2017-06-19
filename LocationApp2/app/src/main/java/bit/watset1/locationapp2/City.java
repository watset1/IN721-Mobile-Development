package bit.watset1.locationapp2;

import android.graphics.Bitmap;

/**
 * Created by Emerson on 5/05/2017.
 */

public class City
{
    String Name;
    String Region;
    String Country;
    String Latitude;
    String Longitude;
    Bitmap Image;

    public City(String Name, String Region, String Country, String Latitude, String Longitude)
    {
        this.Name = Name;
        this.Region = Region;
        this.Country = Country;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        Image = null;
    }
}
