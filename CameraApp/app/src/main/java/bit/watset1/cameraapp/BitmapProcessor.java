package bit.watset1.cameraapp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.File;

/**
 * Created by Emerson on 16/05/2017.
 */

public final class BitmapProcessor
{

    public static Bitmap ProcessPhotoBitmap(String imageFile)
    {
        Bitmap photoBitmap = BitmapFactory.decodeFile(imageFile);
        Bitmap scaledBitmap = scaleBitmap(photoBitmap, 0.5);

        return scaledBitmap;
    }

    private static Bitmap scaleBitmap(Bitmap bitmap, double scale)
    {
        int width = (int)(bitmap.getWidth() * scale);
        int height = (int)(bitmap.getHeight() * scale);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);

        return scaledBitmap;
    }

    public static BitmapDrawable ConvertBitmapToDrawable(Resources resources, Bitmap bitmap)
    {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, bitmap);

        return bitmapDrawable;
    }
}
