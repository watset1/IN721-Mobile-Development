package bit.watset1.locationapp1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Emerson on 4/05/2017.
 */

public final class BitmapProcessor
{
    //Takes a stream containing bitmap data and returns a bitmap object
    public static Bitmap ParseBitmapData(InputStream inputStream)
    {
        return BitmapFactory.decodeStream(inputStream);
    }
}
