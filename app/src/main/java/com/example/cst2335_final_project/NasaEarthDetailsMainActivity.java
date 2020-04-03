package com.example.cst2335_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
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
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;


public class NasaEarthDetailsMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected static final String ACTIVITY_NAME = "NasaEarthDetailsMainActivity";
    private ProgressBar progressBar;
    private TextView txtLatitude;
    private TextView txtLongitude;
    private TextView txtDate;
 //   private ImageView imgEarthImage;
    private Image imgEarthImage;
    private ImageView imageIcon;
    private Button btnAddFavourite;
    private Button btnGoToFavourite;
    private Button btnGoToSearch;
    private EditText inputText;
    private LinearLayout nasaDetails;


    private NasaEarthFragment dFragment;

    DrawerLayout drawer;
    NavigationView navigationView;


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
      //  imgEarthImage= findViewById(R.id.imageView);
        imageIcon =  findViewById(R.id.imageView);


        btnAddFavourite = findViewById(R.id.addFavButton);
        btnGoToFavourite = findViewById(R.id.goToFav);
        btnGoToSearch = findViewById(R.id.goToSearch);
        inputText = findViewById(R.id.editTextInput);


        nasaDetails = findViewById(R.id.nasaDetailsLayout);

        //This gets the toolbar from the layout:
        Toolbar tBar = (Toolbar) findViewById(R.id.toolbar);

        //This loads the toolbar, which calls onCreateOptionsMenu below:
        setSupportActionBar(tBar);

        //For NavigationDrawer:
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, tBar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        EarthQuery earth = new EarthQuery();
        earth.execute();

        Log.i(ACTIVITY_NAME, "In onCreate()");


//        Bundle dataToPass = new Bundle();
//        dataToPass.putString("Message", item.getMessage());
//        dataToPass.putString("MessageID", String.valueOf(id));
//        dataToPass.putBoolean("isTablet", true);
//        //  bundle.putBoolean("isSend", item.getMsgSent());
//
//        //Use a Bundle to pass the message string, and the database id of the selected item to the fragment in the FragmentTransaction
//        // if(findViewById(R.id.fragmentLocation) != null) {
//            dFragment = new DetailFragment();
//            dFragment.setArguments(dataToPass);
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragmentLocation, dFragment) //Add the fragment in FrameLayout
//                    .commit();



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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nasaearth_toolbar, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.helpItem){
            // Toast.makeText(this, "Hello world", Toast.LENGTH_LONG).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(NasaEarthDetailsMainActivity.this);

            builder.setTitle("INSTRUCTIONS")
                    .setMessage("* Click 'ADD TO FAVORITE' to add to favorite list" + "\n" + "* Click 'VIEW FAVORITE' to view favorite list" + "\n" + "* Click 'HOME PAGE' to go to home page")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton(null, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
            //return false;
            Log.e(ACTIVITY_NAME, "in function onPause()");
        }

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.favActivity:
                startActivity(new Intent(this, NasaEarthFavourite.class));
                break;
            case R.id.detailsActivity:
                startActivity(new Intent(this, NasaEarthDetailsMainActivity.class));
                break;
            case R.id.searchActivity:
                startActivity(new Intent(this, NasaEarthMainActivity.class));
                break;
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return false;
    }









    private class EarthQuery extends AsyncTask<String, Integer, String> {

        String inputLat;
        String inputLong;
        String date;
        String imageName;
      //  Bitmap imageIcon;
        String imgurl;


        @Override
        protected String doInBackground(String... params) {

            Intent input = getIntent();
            inputLat = input.getStringExtra("inputLatitude");
            inputLong = input.getStringExtra("inputLongitude");

            String ret = null;
            String data;

//            inputLatitude = editT1.getText().toString();
//            inputLongitude = editT2.getText().toString();
//            String date ="";
//            String url ="";
            NasaEarth ne = new NasaEarth(inputLat, inputLong, date, imgurl);

            Bundle dataToPass = new Bundle();
            dataToPass.putString("Latitude", "Search Latitude is " + ne.getLatitude());
            dataToPass.putString("Longitude", "Search Longitude is " + ne.getLongitude());

            //Use a Bundle to pass the latitude and latitude string to the fragment in the FragmentTransaction
            dFragment = new NasaEarthFragment();
            dFragment.setArguments(dataToPass);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentLocation, dFragment) //Add the fragment in FrameLayout
                    .commit();

       //     String queryURL = "https://api.nasa.gov/planetary/earth/imagery/?lon=" + inputLong +"&lat=" + inputLat + "&date=2014-02-01&api_key=DEMO_KEY";

        //    String queryURL = "https://api.nasa.gov/planetary/earth/imagery/?lon=" + inputLong +"&lat=" + inputLat + "&date=2014-02-01&api_key=Q767GDDKS75D1mIx6UZjEtWmbppuBzpCLAC53ylJ";

            String queryURL = "http://dev.virtualearth.net/REST/V1/Imagery/Map/Birdseye/37,-122/20?dir=180&ms=500,500&key=%20Am4AaTUqExihH1ur1tkSwWH1FodthGyd8wlXp8V5ue-Kk24zaV2QWBTxnsza2LJl";

            try {

                URL url = new URL(queryURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null)

                {sb.append(line + "\n");}

                String result = sb.toString();
                JSONObject jObject = new JSONObject(result);

                date = jObject.getString("date");
                publishProgress(35);
             //   imgurl = jObject.getString("url");
                imgurl = "http://dev.virtualearth.net/REST/V1/Imagery/Map/Birdseye/37.802297,-122.405844/20?dir=180&ms=500,500&key=%20Am4AaTUqExihH1ur1tkSwWH1FodthGyd8wlXp8V5ue-Kk24zaV2QWBTxnsza2LJl";
                publishProgress(70);
                String id = jObject.getString("id");
                publishProgress(100);

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

            txtLatitude.setText("Lat:" +" "+inputLat);
            txtLongitude.setText("Long:" +" "+ inputLong);
            txtDate .setText("Date:" +" "+ date);

            Picasso.with(NasaEarthDetailsMainActivity.this).load(imgurl)
                    .resize(14,10)
                    .centerCrop()
                    .into(imageIcon);
            progressBar.setVisibility(View.INVISIBLE);

//            if (btnAddFavourite != null) {
//                btnAddFavourite.setOnClickListener(bt -> {
//
//                    String inputLatF = txtLatitude.getText().toString();
//
//                    String inputLongF = txtLongitude.getText().toString();
//                    String dateF = txtDate.getText().toString();
//                    String urlF = imgurl;
//
//                    Intent goToFavPage = new Intent(NasaEarthDetailsMainActivity.this, NasaEarthFavourite.class);
//                    goToFavPage.putExtra("inputLatF", inputLatF);
//                    goToFavPage.putExtra("inputLongF", inputLongF);
//                    goToFavPage.putExtra("dateF", dateF);
//                    goToFavPage.putExtra("urlF", urlF);
//                    startActivity(goToFavPage);
//
//                    Log.e(ACTIVITY_NAME, "in function onPause()");
//                });
//            }


            if (btnAddFavourite != null) {
                btnAddFavourite.setOnClickListener((view) -> {

                    String inputLatF = txtLatitude.getText().toString();

                    String inputLongF = txtLongitude.getText().toString();
                    String dateF = txtDate.getText().toString();
                    String urlF = imgurl;


                    AlertDialog.Builder builder = new AlertDialog.Builder(NasaEarthDetailsMainActivity.this);

                    builder.setTitle("Details")
                            .setMessage("Earth with" + "\n" + "Latitude: " + inputLatF + "\n" + "Longitude: " + inputLongF + "\n" + "Date: " + dateF +"\n" +"is being added to favourite list")
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent goToFavPage = new Intent(NasaEarthDetailsMainActivity.this, NasaEarthFavourite.class);
                                    goToFavPage.putExtra("inputLatF", inputLatF);
                                    goToFavPage.putExtra("inputLongF", inputLongF);
                                    goToFavPage.putExtra("dateF", dateF);
                                    goToFavPage.putExtra("urlF", urlF);
                                    startActivity(goToFavPage);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                    //return false;
                    Log.e(ACTIVITY_NAME, "in function onPause()");
                });


            }

//            btnGoToFavourite.setOnClickListener(v->{
//                Snackbar.make(nasaDetails, "Click on menu to access favorite", Snackbar.LENGTH_LONG).show();
//            });

            btnGoToFavourite.setOnClickListener((view)-> {
                Toast.makeText(NasaEarthDetailsMainActivity.this, "Click on menu to access favorite", Toast.LENGTH_LONG).show();
            });
            inputText.setOnClickListener((view)-> {
                Toast.makeText(NasaEarthDetailsMainActivity.this, inputText.getText().toString(), Toast.LENGTH_LONG).show();
            });

            btnGoToSearch.setOnClickListener((view)-> {
                Snackbar snackbar = Snackbar.make(nasaDetails, "Loading Home Page", Snackbar.LENGTH_LONG).setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(NasaEarthDetailsMainActivity.this, MainActivity.class));
                    }
                });
                snackbar.show();
            });


//            btnGoToSearch.setOnClickListener((view)-> {
//                Toast.makeText(NasaEarthDetailsMainActivity.this, "Loading Seacrh Page", Toast.LENGTH_LONG).show();
//            });












    }

    }
}