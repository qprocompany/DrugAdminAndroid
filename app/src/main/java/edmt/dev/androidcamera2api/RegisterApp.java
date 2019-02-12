package edmt.dev.androidcamera2api;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterApp extends Activity {

    EditText name,password, Name,div;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_app);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        name = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        Name = (EditText) findViewById(R.id.name);
        div = findViewById(R.id.div);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new registerapps(name.getText().toString(),password.getText().toString(),name.getText().toString(),div.getText().toString()).execute();
            }
        });
    }

    class registerapps extends AsyncTask<String, String, String>
    {
        private String username, password,name, div;

        public registerapps(String username, String password,String name,String div) {
            this.username = username;
            this.password = password;
            this.name = name;
            this.div = div;
        }

        @Override
        protected String doInBackground(String... strings) {

            CallSoap cs = new CallSoap();
            String data = cs.RegistrationApps(username,password,name,div);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            OpenMainActivity();
        }
    }

    public void OpenMainActivity(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
