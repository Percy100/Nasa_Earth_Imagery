package com.example.cst2335_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NewDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_detail);



        String title = getIntent().getStringExtra("title");
        String date = getIntent().getStringExtra("date");
        String description = getIntent().getStringExtra("description");
        String url = getIntent().getStringExtra("url");

        TextView titleText =findViewById(R.id.bbsnews_detailed_title);
        titleText.setText(title);

        TextView dateText =findViewById(R.id.bbsnews_detailed_date);
        dateText.setText(date);

        TextView descriptionText =findViewById(R.id.bbsnews_detailed_description);
        descriptionText.setText(description);

        TextView urlText =findViewById(R.id.bbsnews_detailed_url);
        urlText.setText(url);

    }
}
