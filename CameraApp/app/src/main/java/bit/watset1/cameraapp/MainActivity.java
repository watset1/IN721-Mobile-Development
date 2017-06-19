package bit.watset1.cameraapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    ImageButton ibCamera;
    ImageView ivTopLeft;
    ImageView ivBottomLeft;
    ImageView ivTopRight;
    ImageView ivBottomRight;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ibCamera = (ImageButton)findViewById(R.id.ibCamera);
        ivTopLeft = (ImageView)findViewById(R.id.ivTopLeft);
        ivBottomLeft = (ImageView)findViewById(R.id.ivBottomLeft);
        ivTopRight = (ImageView)findViewById(R.id.ivTopRight);
        ivBottomRight = (ImageView)findViewById(R.id.ivBottomRight);

        ibCamera.setOnClickListener(new CameraButtonHandler());
    }

    private void displayImageMosaic(Drawable image)
    {
        ivTopLeft.setImageDrawable(image);
        ivBottomLeft.setImageDrawable(image);
        ivTopRight.setImageDrawable(image);
        ivBottomRight.setImageDrawable(image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                String realFilePath = ImageCapture.photoFile.getPath();
                Bitmap bitmap = BitmapProcessor.ProcessPhotoBitmap(realFilePath);
                BitmapDrawable bitmapDrawable = BitmapProcessor.ConvertBitmapToDrawable(getResources(), bitmap);
                displayImageMosaic(bitmapDrawable);
            }
            else
                Toast.makeText(this, "Error: Photo not saved.", Toast.LENGTH_SHORT).show();

        }
    }

    private class CameraButtonHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Uri photoFileUri = ImageCapture.PrepareCapture();
            Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFileUri);
            startActivityForResult(imageCaptureIntent, 1);
        }
    }


}
