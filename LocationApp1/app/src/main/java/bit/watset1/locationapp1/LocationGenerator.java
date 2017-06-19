package bit.watset1.locationapp1;
import android.location.Location;
import java.util.Random;

/**
 * Created by Emerson on 4/05/2017.
 */

public final class LocationGenerator
{
    final static int PROB_RANGE = 100;
    final static int PROBABILITY = 50;
    final static int LATITUDE = 90;
    final static int LONGITUDE = 180;

    public static Location GenerateLocation(Random random)
    {
        double latitude = generateCoordinate(random, CoordinateType.LATITUDE);
        double longitude = generateCoordinate(random, CoordinateType.LONGITUDE);
        Location location = new Location("cityPosition");
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        return location;
    }

    private static double generateCoordinate(Random random, CoordinateType coordinateType)
    {
        int coordinate = 0;
        if(coordinateType == CoordinateType.LATITUDE)
            coordinate = LATITUDE;
        else
            coordinate = LONGITUDE;

        double randNum =  random.nextInt(coordinate);
        double randDouble = random.nextDouble();
        double latitude = randNum + randDouble;

        int probabilityCheck = random.nextInt(PROB_RANGE);
        if(probabilityCheck < PROBABILITY)
            latitude *= -1;

        return latitude;
    }

}
