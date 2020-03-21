package com.example.cst2335_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class NasaEarthDetailsMainActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "NasaEarthDetailsMainActivity";
    private ProgressBar progressBar;
    private TextView txtLatitude;
    private TextView txtLongitude;
    private TextView txtDate;
    private ImageView imgEarthImage;
    private Button btnAddFavourite;
    private Button btnGoToFavourite;



    NasaEarthMainActivity nasaEarthMain = new NasaEarthMainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasaearth_details_main);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        txtLatitude = findViewById(R.id.textLatitude);
        txtLongitude = findViewById(R.id.textLongitude);
        txtDate = findViewById(R.id.textDate);
        imgEarthImage= findViewById(R.id.imageView);

        btnAddFavourite = findViewById(R.id.addFavButton);
        btnGoToFavourite = findViewById(R.id.goToFav);

        EarthQuery earth = new EarthQuery();
        earth.execute();

        Log.i(ACTIVITY_NAME, "In onCreate()");
    }


    protected static Bitmap getImage(URL url) {

        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return BitmapFactory.decodeStream(connection.getInputStream());
            } else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean fileExistance(String fileName) {

        File file = getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }

    private class EarthQuery extends AsyncTask<String, Integer, String> {

        String inputLat;
        String inputLong;
        String date;
        String imageName;
        Bitmap imageIcon;
        String imgurl;


        @Override
        protected String doInBackground(String... params) {

            Intent input = getIntent();
            inputLat = input.getStringExtra("inputLatitude");
            inputLong = input.getStringExtra("inputLongitude");

            String ret = null;
            String data;

            String queryURL = "https://api.nasa.gov/planetary/earth/imagery/?lon=" + inputLong +"&lat=" + inputLat + "&date=2014-02-01&api_key=DEMO_KEY";

            try {

                URL url = new URL(queryURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStream c = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null)

                {sb.append(line + "\n");}

                String result = sb.toString();
                JSONObject jObject = new JSONObject(result);

                date = jObject.getString("date");
                publishProgress(35);
                imgurl = jObject.getString("url");
                publishProgress(70);
                String id = jObject.getString("id");
                publishProgress(100);











//                URL iconUrl = new URL(imgurl + imageName + ".png");
//                //     URL iconUrl = new URL("https://earthengine.googleapis.com/api/thumb?thumbid=31dbf5b72d114cc8c12cc7812add59b5&token=fe3be37f467aea639a4f97d7c5c28a71");
//                //      URL iconUrl = new URL("https://earthengine.googleapis.com/api/thumb?thumbid=c87c9101afae02a432e1f5cf3db6f55c&token=ae1542f81ee766b0531ae47375bef239"+imageName+".png");
//                String imageFile = imageName + ".png";
//
//                if (fileExistance(imageFile)) {
//                    FileInputStream fis = null;
//
//                    try {
//                        fis = openFileInput(imageFile);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                    imageIcon = BitmapFactory.decodeStream(fis);
//                    Log.i(ACTIVITY_NAME, "File imageFile Exists");
//                } else {
//                    imageIcon = getImage(iconUrl);
//                    FileOutputStream outputStream = openFileOutput(imageName + ".png",
//                            Context.MODE_PRIVATE);
//                    imageIcon.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
//                    outputStream.flush();
//                    outputStream.close();
//                    Log.i(ACTIVITY_NAME, "Added New Image");
//                }
//                Log.i(ACTIVITY_NAME, "filename=" + imageFile);
//                publishProgress(100);

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (JSONException ex) {
                ex.printStackTrace();
            }

          return ret;
       }

        @Override
        protected void onProgressUpdate(Integer... value) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
            Log.i(ACTIVITY_NAME, "In onProgressUpdate");
        }

        @Override
        protected void onPostExecute(String result) {

            // String degree = Character.toString((char) 0x00B0);
            txtLatitude.setText("Lat:" +" "+inputLat);
            txtLongitude.setText("Long:" +" "+ inputLong);
            txtDate .setText("Date:" +" "+ date);
       //     imgEarthImage.setImageBitmap(imageIcon);
        //    imgEarthImage = imgEarthImage;

            Picasso.with(NasaEarthDetailsMainActivity.this).load(imgurl)
                    .resize(14,10)
                    .centerCrop()
                    .into(imgEarthImage);
            progressBar.setVisibility(View.INVISIBLE);


        }


    }
}

