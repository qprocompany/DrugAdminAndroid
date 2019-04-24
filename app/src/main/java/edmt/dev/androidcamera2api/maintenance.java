package edmt.dev.androidcamera2api;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;

public class maintenance extends AppCompatActivity {
    SurfaceView surfaceView;
    CameraSource cameraSource;
    TextView detailQR;
    BarcodeDetector barcodeDetector;
    Button buttonConfirm;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    String temp ="";
    public static String bedid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceCamera);
        detailQR = (TextView) findViewById(R.id.detailQR);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(500, 500)
                .setAutoFocusEnabled(true)
                .build();

        final boolean isEnabled = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(new String[]{Manifest.permission.CAMERA},
//                            MY_CAMERA_REQUEST_CODE);
                    return;
                }
                try {
                    cameraSource.start(holder);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();

                if (qrCodes.size() != 0) {
                    detailQR.post(new Runnable() {
                        @Override
                        public void run() {
                            //Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            //vibrator.vibrate(1000);

                            detailQR.setText(qrCodes.valueAt(0).displayValue);
                            temp = qrCodes.valueAt(0).displayValue;

                            //if (temp.indexOf("#") < 5) {
                                bedid = temp;
                                Intent confirmIntent = new Intent(maintenance.this, maintenceinfo.class);
                                startActivity(confirmIntent);
                            //}
                        }
                    });
                }
            }
        });
    }
}
