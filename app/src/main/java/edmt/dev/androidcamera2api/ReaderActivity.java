package edmt.dev.androidcamera2api;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ReaderActivity extends Activity {

    TextView name,mrn,alamat,tlp,diagnosa,prosedure,alergi,infeksi,antialergi,problem;
    TableLayout obat;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_reader);
        name = (TextView) findViewById(R.id.namaPasien);
        mrn = (TextView) findViewById(R.id.mrnPasien);
        alamat = (TextView) findViewById(R.id.alamatPasien);
        tlp = (TextView) findViewById(R.id.noTlpPasien);
        diagnosa = (TextView) findViewById(R.id.diagnosaPasien);
        prosedure = (TextView) findViewById(R.id.prosedurPasien);

        alergi = (TextView) findViewById(R.id.alergiId);
        infeksi = (TextView) findViewById(R.id.infeksiId);
        antialergi = (TextView) findViewById(R.id.antiId);
        problem = (TextView) findViewById(R.id.Problem);

        obat = (TableLayout) findViewById(R.id.obat);

        btn = (Button) findViewById(R.id.nextbtn);

        name.setText(PasienQR.PasienQRData);
        mrn.setText(PasienQR.PasienQRData);
        alamat.setText(PasienQR.PasienQRData);
        tlp.setText(PasienQR.PasienQRData);
        diagnosa.setText(PasienQR.PasienQRData);
        prosedure.setText(PasienQR.PasienQRData);

        alergi.setText(ObatQR.ObatQRData);
        infeksi.setText(ObatQR.ObatQRData);
        antialergi.setText(ObatQR.ObatQRData);
        problem.setText(ObatQR.ObatQRData);

        String obat1 = ObatQR.ObatQRData;
        String data[] = obat1.split("#");
        for (int i = 0; i < data.length;i++)
        {
            if(i == 0)
            {
                TableRow row = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                TextView tv = new TextView(this);
                tv.setText("");
                tv.setTextSize(20);
                row.addView(tv);
                obat.addView(row, i);
            }
            else {
                TableRow row = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                TextView tv = new TextView(this);
                tv.setText(data[i]);
                tv.setTextSize(20);
                row.addView(tv);
                obat.addView(row, i);
            }
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent confirmIntent = new Intent(ReaderActivity.this, Menu.class);
                startActivity(confirmIntent);
            }
        });
    }
}

