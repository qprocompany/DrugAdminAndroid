package edmt.dev.androidcamera2api;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class maintenceinfo extends AppCompatActivity {

    EditText detail1;
    TextView textView;
    Button finish;
    ImageView img;
    String image1="";
    Uri file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenceinfo);
        img = findViewById(R.id.img);
        detail1 = findViewById(R.id.detail);
        textView = findViewById(R.id.textView);
        textView.setText(maintenance.bedid);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture(v);
            }
        });

        finish = findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = detail1.getText().toString();
                new Detail(maintenance.bedid,data,image1,Login.username1).execute();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    public void takePicture(View view) {
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = Uri.fromFile(getOutputMediaFile());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

                startActivityForResult(intent, 100);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                //img.setImageURI(file);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), file);
                }catch (Exception e){
                    Toast.makeText(maintenceinfo.this,e.toString(),Toast.LENGTH_SHORT).show();
                }
                //encode image to base64 string
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                image1 = imageString;
                img.setImageURI(file);
            }
        }
    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    class Detail extends AsyncTask<String, String, String>
    {
        private String bedid,problem,picture,name;

        public Detail(String bedid,String problem,String picture,String name) {
            this.bedid = bedid;
            this.problem = problem;
            this.picture = picture;
            this.name = name;
        }
        @Override
        protected String doInBackground(String... strings) {
            CallSoap cs = new CallSoap();
            String data = cs.Maintenance(bedid,problem,picture,name);
            return data;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("Finish"))
                OpenMainActivity();
            Toast.makeText(maintenceinfo.this,s,Toast.LENGTH_SHORT).show();
        }
    }

    public void OpenMainActivity(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}
