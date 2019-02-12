package edmt.dev.androidcamera2api;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class DetailBedcleaning extends AppCompatActivity {
    TextView bedid1,tgl,start,stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bedcleaning);

        bedid1 = findViewById(R.id.textView6);
        tgl = findViewById(R.id.textView9);
        start = findViewById(R.id.textView13);
        stop = findViewById(R.id.textView11);
        new Detail(Bedcleaning.bedid).execute();
    }

    class Detail extends AsyncTask<String, String, String>
    {
        private String bedid;
        public Detail(String bedid) {
            this.bedid = bedid;
        }
        @Override
        protected String doInBackground(String... strings) {
            CallSoap cs = new CallSoap();
            String data = cs.DetailCleaning(Login.username1 ,bedid);
            return data;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String data[] = s.split("_");
            bedid1.setText(Bedcleaning.bedid);
            String temp[] = data[1].split(" ");
            tgl.setText(data[1].substring(0,data[1].indexOf(" ")));
            start.setText(data[1].substring(data[1].indexOf(" ")));
            stop.setText(data[2].substring(data[1].indexOf(" ")));
        }
    }
}
