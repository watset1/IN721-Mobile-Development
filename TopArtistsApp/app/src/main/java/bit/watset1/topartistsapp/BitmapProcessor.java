package bit.watset1.topartistsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

/**
 * Created by Emerson on 15/04/2017.
 */

public final class BitmapProcessor
{
    public static Bitmap ParseBitmapData(InputStream inputStream)
    {
        return BitmapFactory.decodeStream(inputStream);
    }
}
