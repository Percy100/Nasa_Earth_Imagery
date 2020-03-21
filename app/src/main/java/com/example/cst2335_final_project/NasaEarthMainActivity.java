package com.example.cst2335_final_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class NasaEarthMainActivity extends AppCompatActivity {

    Button snackbtn;
    EditText editT1;
    EditText editT2;
    Button toastbtn;
    Button submitbtn;
    LinearLayout nasaMain;

    private String inputLatitude;
    private String inputLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa_earth_main);

        snackbtn = (Button)findViewById(R.id.snackButton);
        editT1 = findViewById(R.id.editText1);
        editT2 = findViewById(R.id.editText2);
        toastbtn = findViewById(R.id.toastButton);
        submitbtn = findViewById(R.id.submitButton);

        nasaMain = findViewById(R.id.nasaMainLayout);


        snackbtn.setOnClickListener(v->{
            Snackbar.make(nasaMain, "Please Enter Details", Snackbar.LENGTH_LONG).show();
        });



        toastbtn.setOnClickListener((view)-> {
        Toast.makeText(NasaEarthMainActivity.this, "Latitude is " + editT1.getText().toString() + " "
                        + "while Longitude is " + editT2.getText().toString(), Toast.LENGTH_LONG).show();
    });

        submitbtn.setOnClickListener((view) -> {

            inputLatitude = editT1.getText().toString();
            inputLongitude = editT2.getText().toString();

            Intent input = new Intent(NasaEarthMainActivity.this, NasaEarthDetailsMainActivity.class);
            input.putExtra("inputLatitude", inputLatitude);
            input.putExtra("inputLongitude", inputLongitude);
            startActivity(input);


            AlertDialog.Builder builder = new AlertDialog.Builder(NasaEarthMainActivity.this);

            builder.setTitle("Search Details")
                    .setMessage("Latitude: " +  editT1.getText().toString() +"\n" + "Longitude: " + editT2.getText().toString())
                    .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                       public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(NasaEarthMainActivity.this, NasaEarthDetailsMainActivity.class);
                                startActivity(intent);
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






}
