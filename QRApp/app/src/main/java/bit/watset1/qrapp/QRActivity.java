package bit.watset1.qrapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class QRActivity extends AppCompatActivity
{
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    SurfaceView svCameraSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        //Surface Holder
        svCameraSurfaceView = (SurfaceView)findViewById(R.id.svCamera);
        SurfaceHolder cameraSurfaceHolder = svCameraSurfaceView.getHolder();
        cameraSurfaceHolder.addCallback(new SurfaceHolderSetUp());

        //Barcode detector
        BarcodeDetector.Builder detectorBuilder = new BarcodeDetector.Builder(this);
        detectorBuilder.setBarcodeFormats(Barcode.QR_CODE);
        barcodeDetector = detectorBuilder.build();
        barcodeDetector.setProcessor(new BarcodeDetectorProcessor());

        //Camera Source
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 640)
                .build();
    }

    public class BarcodeDetectorProcessor implements Detector.Processor<Barcode>
    {

        @Override
        public void release() {

        }

        @Override
        public void receiveDetections(Detector.Detections<Barcode> detections) {
            SparseArray<Barcode> barcodes = detections.getDetectedItems();

            if(barcodes.size() != 0)
            {
                String qrCodeMessage = barcodes.valueAt(0).displayValue;
                Intent returnIntent= new Intent();
                returnIntent.putExtra("qr_message", qrCodeMessage);
                setResult(1, returnIntent);
                finish();
            }
        }
    }

    public class SurfaceHolderSetUp implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            try {
                cameraSource.start(surfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            cameraSource.stop();
        }
    }
}
