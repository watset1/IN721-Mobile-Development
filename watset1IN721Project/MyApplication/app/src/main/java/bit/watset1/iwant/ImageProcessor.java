package bit.watset1.iwant;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.io.File;

/**
 * Created by Emerson on 5/06/2017.
 */

public final class ImageProcessor
{
    static final double SCALE = 0.2;
    static File photoFile;
    static Uri photoFileUri;

    public static Drawable ProcessImagePath(String filePath)
    {
        Drawable drawable = null;

        return drawable;
    }

    public static Uri PrepareCapture()
    {
        photoFile = FileManager.CreateImageFile();
        photoFileUri = Uri.fromFile(photoFile);

        return photoFileUri;
    }

    public static Bitmap ProcessPhotoBitmap(String imageFile)
    {
        Bitmap photoBitmap = BitmapFactory.decodeFile(imageFile);
        Bitmap scaledBitmap = scaleBitmap(photoBitmap, SCALE);

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
