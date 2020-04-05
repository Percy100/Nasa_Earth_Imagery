package com.example.cst2335_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class NasaEarthMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String ACTIVITY_NAME = "NasaEarthMainActivity";

    private SharedPreferences sp;

    private Button snackbtn;
    private EditText editT1;
    private EditText editT2;
    private Button toastbtn;
    private Button submitbtn;
    private LinearLayout nasaMain;

    private String inputLatitude;
    private String inputLongitude;
    private ProgressBar progressBar;

    private NasaEarthFragment dFragment;

    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa_earth_main);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        sp = getSharedPreferences("FileName", Context.MODE_PRIVATE);

        snackbtn = (Button)findViewById(R.id.snackButton);
        editT1 = findViewById(R.id.editText1);
        editT2 = findViewById(R.id.editText2);
        toastbtn = findViewById(R.id.toastButton);
        submitbtn = findViewById(R.id.submitButton);

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

        editT1.setText(sp.getString("Latitude", ""));
//        editT2.setText(sp.getString("Longitude", ""));

        nasaMain = findViewById(R.id.nasaMainLayout);

        inputLatitude = editT1.getText().toString();
        inputLongitude = editT2.getText().toString();
        String date ="";
        String url ="";
        NasaEarth ne = new NasaEarth(inputLatitude, inputLongitude, date, url);

        Bundle dataToPass = new Bundle();
        dataToPass.putString("Latitude", "Default Latitude is " + ne.getLatitude());
        dataToPass.putString("Longitude", ne.getLongitude());

        //Use a Bundle to pass the latitude and latitude string to the fragment in the FragmentTransaction
            dFragment = new NasaEarthFragment();
            dFragment.setArguments(dataToPass);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentLocation, dFragment) //Add the fragment in FrameLayout
                    .commit();

        snackbtn.setOnClickListener((view)-> {
                    Snackbar snackbar = Snackbar.make(nasaMain, "Loading Home Page", Snackbar.LENGTH_LONG).setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(NasaEarthMainActivity.this, MainActivity.class));
                        }
                    });
                    snackbar.show();
                });

        toastbtn.setOnClickListener((view)-> {
        Toast.makeText(NasaEarthMainActivity.this, "Latitude is " + editT1.getText().toString() + " "
                        + "while Longitude is " + editT2.getText().toString(), Toast.LENGTH_LONG).show();
    });

//        progressBar.setVisibility(View.VISIBLE);
//        Log.i(ACTIVITY_NAME, "In onProgressUpdate");

        submitbtn.setOnClickListener((view) -> {

            inputLatitude = editT1.getText().toString();
            inputLongitude = editT2.getText().toString();

//            Intent input = new Intent(NasaEarthMainActivity.this, NasaEarthDetailsMainActivity.class);
//            input.putExtra("inputLatitude", inputLatitude);
//            input.putExtra("inputLongitude", inputLongitude);
        //    startActivity(input);


            AlertDialog.Builder builder = new AlertDialog.Builder(NasaEarthMainActivity.this);

            if (inputLatitude.isEmpty() || inputLongitude.isEmpty()) {

                builder.setTitle("Missing Field")
                        .setMessage("Latitude and Longitude are required ")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
            }
                else {
                builder.setTitle("Search Details")
                        .setMessage("Latitude: " + editT1.getText().toString() + "\n" + "Longitude: " + editT2.getText().toString())
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent input = new Intent(NasaEarthMainActivity.this, NasaEarthDetailsMainActivity.class);
                                input.putExtra("inputLatitude", inputLatitude);
                                input.putExtra("inputLongitude", inputLongitude);
                                startActivity(input);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
            }
            AlertDialog alert = builder.create();
            alert.show();
            //return false;
        });




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
            AlertDialog.Builder builder = new AlertDialog.Builder(NasaEarthMainActivity.this);

        builder.setTitle("INSTRUCTIONS")
                .setMessage("1. Enter latititude of required localtion" + "\n" + "2. Enter longitude of required localtion " + "\n" + "3. Review entries " + "\n" + "4. Click submit ")
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




    @Override
    protected void onStart(){
        super.onStart();
        Log.e(ACTIVITY_NAME,"onStart");
    }

    @Override
    protected void onPause() {
        EditText editText1 = findViewById(R.id.editText1);
        EditText editText2 = findViewById(R.id.editText2);
        super.onPause();
        Log.e(ACTIVITY_NAME,"onPause");
        saveSharedPrefs( editText1.getText().toString());
  //      saveSharedPrefs( editText2.getText().toString());
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME,"onResume");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "onDestroy");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(ACTIVITY_NAME, "onRestart");
    }

    private void saveSharedPrefs(String stringToSave) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Latitude", stringToSave);
 //       editor.putString("Longitude", stringToSave);
        editor.commit();

    }



}
