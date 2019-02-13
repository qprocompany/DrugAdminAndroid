package edmt.dev.androidcamera2api;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Bedcleaning extends AppCompatActivity {
    public static String bedid;
    Button nextbtn;
    ImageButton scanbtn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bedcleaning);

        nextbtn = (Button) findViewById(R.id.nextbtn);
        scanbtn1 = (ImageButton) findViewById(R.id.scanbtn1);


        final Activity activity = this;

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Bedcleaning.this, Bedcleaningtimer.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        scanbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                FancyToast.makeText(this,"You cancelled the scanning",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
            }
            else {
                bedid = result.getContents();
                FancyToast.makeText(this,bedid,FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}