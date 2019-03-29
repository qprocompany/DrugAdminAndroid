package edmt.dev.androidcamera2api;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ReaderActivity extends Activity {
    private Button scan_btn,nextBtn;
    public static String name;
    TextView toast1,toast2;
    private ImageButton scanbtn1,scanbtn2;
    private static final int MY_CAMERA_REQUEST_CODE = 100;

    public static String PasienQRData = "",ObatQRData = "";
//    TextView pasienInfo, obatInfo;

    TextView namaPasien, mrnPasien, alamatPasien, noTlpPasien, diagnosaPasien, prosedurPasien,obat, errorTxt, alergiId, infeksiId, antiId, masalahId;
    String temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_reader);
        nextBtn = (Button) findViewById(R.id.nextbtn);
        toast1 = (TextView) findViewById(R.id.toast1);
        toast2 = (TextView) findViewById(R.id.toast2) ;
        scanbtn1 = (ImageButton) findViewById(R.id.scanbtn1);
        scanbtn2 = (ImageButton) findViewById(R.id.scanbtn2);
        errorTxt = (TextView) findViewById(R.id.errorTxt);

//        pasienInfo = (TextView) findViewById(R.id.pasienInfo);
//        obatInfo = (TextView) findViewById(R.id.obatInfo);
//
//        pasienInfo.setText(PasienQR.tampungInfo);
//        obatInfo.setText(ObatQR.tampungInfo);

        namaPasien = (TextView) findViewById(R.id.namaPasien);
        mrnPasien = (TextView) findViewById(R.id.mrnPasien);
        alamatPasien = (TextView) findViewById(R.id.alamatPasien);
        noTlpPasien = (TextView) findViewById(R.id.noTlpPasien);
        diagnosaPasien = (TextView) findViewById(R.id.diagnosaPasien);
        prosedurPasien = (TextView) findViewById(R.id.prosedurPasien);
        obat = findViewById(R.id.obat);


//        namaPasien.setText(PasienQRData);
//        obat.setText(ObatQRData);

        temp = ObatQRData;
        if(temp.split("#")[0].equals(PasienQRData)){
            errorTxt.setText("Pasien dan Obat sesuai");
            errorTxt.setTextColor(getColor(R.color.greenA700));

            nextBtn.setText("OK");
            nextBtn.setBackgroundColor(getColor(R.color.blue500));

            namaPasien.setText("Derry");
            mrnPasien.setText(PasienQRData.split("/")[2]);
            alamatPasien.setText("Jl.Tss no.10");
            noTlpPasien.setText("+62000xxxx");
            diagnosaPasien.setText("Demam");
            prosedurPasien.setText("Di berikan obat");

            alergiId.setText("Seafood");
            infeksiId.setText(" ");
            antiId.setText(" ");
            masalahId.setText(" ");



            obat.setText(
                    "Kode Obat: "+
                    ObatQRData.split("#")[1] +
                    ObatQRData.split("#")[2]+
                    ObatQRData.split("#")[3] +
                    "\n" + " Dosis: " + "\n" +
                    "- Paracetamol 500mg : 1 Butir"  );

            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goHome = new Intent(ReaderActivity.this, Menu.class);
                    startActivity(goHome);
                }
            });
        }
        else{
            errorTxt.setText("Pasien dan Obat tidak sesuai");
            errorTxt.setTextColor(getColor(R.color.colorGrapeFruitDark));

            nextBtn.setText("Retry");
            nextBtn.setBackgroundColor(getColor(R.color.colorGrapeFruit));

            namaPasien.setText(" ");
            mrnPasien.setText(" ");
            alamatPasien.setText(" ");
            noTlpPasien.setText(" ");
            diagnosaPasien.setText(" ");
            prosedurPasien.setText(" ");

            alergiId.setText(" ");
            infeksiId.setText(" ");
            antiId.setText(" ");
            masalahId.setText(" ");

            obat.setText(" ");

            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent retry = new Intent(ReaderActivity.this, PasienQR.class);
                    startActivity(retry);
                }
            });

        }




        final Activity activity = this;


//        scanbtn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            Intent btn1Intent = new Intent(ReaderActivity.this, PasienQR.class);
//            startActivity(btn1Intent);
//            }
//        });
//
//        scanbtn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent btn2Intent = new Intent(ReaderActivity.this, ObatQR.class);
//                startActivity(btn2Intent);
//            }
//        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                FancyToast.makeText(this,"You cancelled the scanning",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
            }
            else {
                name = result.getContents();
                //FancyToast.makeText(this, name,FancyToast.LENGTH_LONG,FancyToast.INFO,true).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void openreaderactivity(){
        Toast.makeText(ReaderActivity.this,"You must Scan First",Toast.LENGTH_SHORT);
        Intent intent = new Intent(this, ReaderActivity.class);
        startActivity(intent);
    }
}

