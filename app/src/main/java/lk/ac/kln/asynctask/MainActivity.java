package lk.ac.kln.asynctask;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {
        private ProgressDialog progressDialog = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("plz wait..");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected Bitmap doInBackground(String[] strings) {
            Bitmap bitmap = null;
            try {
                //Download image
                URL imageURL = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) imageURL.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is, null, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = findViewById(R.id.image);
            if (bitmap != null) {
                progressDialog.hide();
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
