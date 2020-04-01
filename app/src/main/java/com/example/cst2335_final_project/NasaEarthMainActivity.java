package com.example.cst2335_final_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class NasaEarthMainActivity extends AppCompatActivity {

    static final String ACTIVITY_NAME = "NasaEarthMainActivity";

    SharedPreferences sp;

    Button snackbtn;
    EditText editT1;
    EditText editT2;
    Button toastbtn;
    Button submitbtn;
    LinearLayout nasaMain;

    private String inputLatitude;
    private String inputLongitude;
    private ProgressBar progressBar;

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

        editT1.setText(sp.getString("Latitude", ""));
//        editT2.setText(sp.getString("Longitude", ""));

        nasaMain = findViewById(R.id.nasaMainLayout);


        snackbtn.setOnClickListener((view)->{
            Snackbar.make(nasaMain, "Please Enter Details", Snackbar.LENGTH_LONG).show();
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

            builder.setTitle("Search Details")
                    .setMessage("Latitude: " +  editT1.getText().toString() +"\n" + "Longitude: " + editT2.getText().toString())
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

            AlertDialog alert = builder.create();
            alert.show();
            //return false;
        });
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
