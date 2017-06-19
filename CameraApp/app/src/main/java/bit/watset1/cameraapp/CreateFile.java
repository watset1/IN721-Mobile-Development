package bit.watset1.cameraapp;

import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Emerson on 16/05/2017.
 */

public final class CreateFile
{
    static String photoFileName;

    public static File CreateImageFile()
    {
        File imageRootPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File imageStorageDirectory = new File(imageRootPath, "camera");
        if(!imageStorageDirectory.exists())
            imageStorageDirectory.mkdirs();

        String timeStamp = getTimeStamp();

        photoFileName = "IMG_" + timeStamp + ".jpg";

        return new File(imageStorageDirectory.getPath() + File.separator + photoFileName);
    }

    private static String getTimeStamp()
    {
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date currentTime = new Date();
        String timeStamp = timeStampFormat.format(currentTime);

        return timeStamp;
    }
}
