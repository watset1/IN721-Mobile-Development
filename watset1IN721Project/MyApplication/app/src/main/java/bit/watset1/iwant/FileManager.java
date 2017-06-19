package bit.watset1.iwant;

import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Emerson on 6/06/2017.
 */
//Method
public final class FileManager
{
    final static String FILE_CAMERA = "camera";
    final static String PREFIX = "IMG_";
    final static String SUFFIX = ".jpg";
    final static String DATE_FORMAT = "yyyyMMdd_HHmmss";

    static String photoFileName;

    public static File CreateImageFile()
    {
        File imageRootPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File imageStorageDirectory = new File(imageRootPath, FILE_CAMERA);
        if(!imageStorageDirectory.exists())
            imageStorageDirectory.mkdirs();

        String timeStamp = getTimeStamp();

        photoFileName = PREFIX + timeStamp + SUFFIX;

        return new File(imageStorageDirectory.getPath() + File.separator + photoFileName);
    }

    private static String getTimeStamp()
    {
        SimpleDateFormat timeStampFormat = new SimpleDateFormat(DATE_FORMAT);
        Date currentTime = new Date();
        String timeStamp = timeStampFormat.format(currentTime);

        return timeStamp;
    }
}
