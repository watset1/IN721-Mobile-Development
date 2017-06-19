package bit.watset1.locationapp2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

/**
 * Created by Emerson on 5/05/2017.
 */

public final class BitmapProcessor
{
    //Takes a stream containing bitmap data and returns a bitmap object
    public static Bitmap ParseBitmapData(InputStream inputStream)
    {
        return BitmapFactory.decodeStream(inputStream);
    }
}
