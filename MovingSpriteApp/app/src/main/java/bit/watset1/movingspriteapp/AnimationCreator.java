package bit.watset1.movingspriteapp;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;

/**
 * Created by Emerson on 11/05/2017.
 */

public final class AnimationCreator
{
    public static AnimationDrawable CreateAnimation(Resources resources, int sheetId, int nFrames, int spriteWidth, int spriteHeight, int speed)
    {
        Bitmap bmp = BitmapFactory.decodeResource(resources, sheetId);
        bmp = Bitmap.createScaledBitmap(bmp, 882, 168, false);
        AnimationDrawable animation = new AnimationDrawable();

        for (int i = 0; i < nFrames; i++) {
            Bitmap frame = Bitmap.createBitmap(bmp, i * spriteWidth, 0, spriteWidth, spriteHeight);

            BitmapDrawable bFrame = new BitmapDrawable(resources, frame);
            animation.addFrame(bFrame, speed);
        }

        return animation;
    }
}
