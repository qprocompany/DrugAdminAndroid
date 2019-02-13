package edmt.dev.androidcamera2api;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailBedcleaning extends AppCompatActivity {
    TextView bedid1,tgl,start,stop;
    Button finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bedcleaning);

        bedid1 = findViewById(R.id.textView6);
        tgl = findViewById(R.id.textView9);
        start = findViewById(R.id.textView13);
        stop = findViewById(R.id.textView11);
        new Detail().execute();

        finish = findViewById(R.id.button);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenMainActivity();
            }
        });
    }

    class Detail extends AsyncTask<String, String, String>
    {
        protected String doInBackground(String... strings) {
            CallSoap cs = new CallSoap();
            String data = cs.DetailCleaning(Login.username1 ,Bedcleaning.bedid);
            return data;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(DetailBedcleaning.this,s,Toast.LENGTH_SHORT).show();
            bedid1.setText(Bedcleaning.bedid);
            String data[] = s.split("_");
            String temp[] = data[1].split(" ");
            tgl.setText(data[1].substring(1,data[1].indexOf(" ")));
            start.setText(data[1].substring(data[1].indexOf(" ")));
            stop.setText(data[2].substring(data[1].indexOf(" ")));
        }
    }

    public void OpenMainActivity(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}
