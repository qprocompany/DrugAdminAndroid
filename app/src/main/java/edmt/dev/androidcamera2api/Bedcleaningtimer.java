package edmt.dev.androidcamera2api;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Bedcleaningtimer extends AppCompatActivity {
    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bedcleaningtimer);

        button = (Button) findViewById(R.id.next);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 10000) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    Toast.makeText(Bedcleaningtimer.this, "Bing!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetail();
            }
        });
    }

    public void startChronometer(View v) {
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
            String date = df.format(Calendar.getInstance().getTime());
            new Start(date,Bedcleaning.bedid).execute();
        }

    }

    //test

    public void pauseChronometer(View v) {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
            chronometer.setBase(SystemClock.elapsedRealtime());
            pauseOffset = 0;
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
            String date = df.format(Calendar.getInstance().getTime());
            new Finish(date,Bedcleaning.bedid).execute();
        }
    }

     /*public void resetChronometer(View v) {
       chronometer.setBase(SystemClock.elapsedRealtime());
       pauseOffset = 0;
     }*/

    class Start extends AsyncTask<String, String, String>
    {
        private String datetime, bedid;
        public Start(String datetime, String bedid) {
            this.datetime = datetime;
            this.bedid = bedid;
        }
        @Override
        protected String doInBackground(String... strings) {
            CallSoap cs = new CallSoap();
            String data = cs.StartCleaning(Login.username1,datetime,bedid);
            return data;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Bedcleaningtimer.this,s,Toast.LENGTH_SHORT).show();
        }
    }

    class Finish extends AsyncTask<String, String, String>
    {
        private String datetime, bedid;
        public Finish(String datetime, String bedid) {
            this.datetime = datetime;
            this.bedid = bedid;
        }
        @Override
        protected String doInBackground(String... strings) {
            CallSoap cs = new CallSoap();
            String data = cs.FinishCleaning(Login.username1 ,datetime,bedid);
            return data;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Bedcleaningtimer.this,s,Toast.LENGTH_SHORT).show();
        }
    }

    public void openDetail(){
        Intent intent = new Intent(this, DetailBedcleaning.class);
        startActivity(intent);
    }


}