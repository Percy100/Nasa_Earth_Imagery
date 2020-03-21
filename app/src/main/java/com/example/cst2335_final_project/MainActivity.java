package com.example.cst2335_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bbcBtn = findViewById(R.id.button_bbs);
        Button GuardianBtn = findViewById(R.id.button_guardian);
        Button nasaImageBtn = findViewById(R.id.button_nasa_image);
        Button nasaEarthBtn = findViewById(R.id.button_nasa_earth);

        bbcBtn.setOnClickListener(v->{
            Intent intent = new Intent(getBaseContext(), BBCMainActivity.class);
            startActivity(intent);
        });
        GuardianBtn.setOnClickListener(v->{
            Intent intent = new Intent(getBaseContext(), BBCMainActivity.class);
            startActivity(intent);
        });
        nasaImageBtn.setOnClickListener(v->{
            Intent intent = new Intent(getBaseContext(), BBCMainActivity.class);
            startActivity(intent);
        });
        nasaEarthBtn.setOnClickListener(v->{
            Intent intent = new Intent(getBaseContext(), NasaEarthMainActivity.class);
            startActivity(intent);
        });
    }
}
