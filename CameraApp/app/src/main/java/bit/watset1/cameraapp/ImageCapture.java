package bit.watset1.cameraapp;

import android.net.Uri;

import java.io.File;

/**
 * Created by Emerson on 16/05/2017.
 */

public final class ImageCapture
{
    static File photoFile;
    static Uri photoFileUri;

    public static Uri PrepareCapture()
    {
        photoFile = CreateFile.CreateImageFile();
        photoFileUri = Uri.fromFile(photoFile);

        return photoFileUri;
    }
}
