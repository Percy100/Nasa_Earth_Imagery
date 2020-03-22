package com.example.cst2335_final_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NasaEarthFavourite extends AppCompatActivity {

    BaseAdapter myAdapter;
    ListView myList;
    String imgUrl;
    ImageView imageIcon;

    ArrayList<NasaEarth> earthList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa_earth_favourite);

        myList = findViewById(R.id.favouriteList);

        myList.setAdapter(myAdapter = new ListAdapter());

        Intent goToFavPage = getIntent();
        String imgLat = goToFavPage.getStringExtra("inputLatF");
        String imgLong = goToFavPage.getStringExtra("inputLongF");
        String imgDate = goToFavPage.getStringExtra("dateF");
        imgUrl = goToFavPage.getStringExtra("urlF");

        NasaEarth nasaEarth = new NasaEarth(imgLat,imgLong,imgDate,imgUrl);
        earthList.add(nasaEarth);

        myAdapter.notifyDataSetChanged();

    }


    private class ListAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return earthList.size();
        }

        @Override
        public NasaEarth getItem(int position) {
            return earthList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int pos, View old, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View newRow = old;

            if (newRow == null)
                //   newRow = inflater.inflate(R.layout.activity_nasaearth_details_main, null);

                newRow = inflater.inflate(R.layout.activity_nasaearth_details_main, parent, false);


            TextView lat = newRow.findViewById(R.id.textLatitude);
            lat.setText(getItem(pos).getLatitude());

            String x = getItem(pos).getLatitude();

            TextView lo = newRow.findViewById(R.id.textLongitude);
            lo.setText(getItem(pos).getLongitude());

            String y = getItem(pos).getLongitude();

            TextView da = newRow.findViewById(R.id.textDate);
            da.setText(getItem(pos).getDate());

            ImageView imgView = newRow.findViewById(R.id.imageView);
            Picasso.with(NasaEarthFavourite.this).load(getItem(pos).getUrl())
                    .resize(14, 10)
                    .centerCrop()
                    .into(imgView);
            imgView = imageIcon;

            return newRow;


        }
    }
//        @Override
//        public View getView(int pos, View old, ViewGroup parent) {
//            LayoutInflater inflater = getLayoutInflater();
//            View newRow = old;
//
//           if (newRow == null)
//             //   newRow = inflater.inflate(R.layout.activity_nasaearth_details_main, null);
//
//            newRow = inflater.inflate(R.layout.activity_nasaearth_details_main, parent, false);
//
//                TextView itemText = newRow.findViewById(R.id.textLatitude);
//                itemText.setText(getItem(pos).getLatitude());
//
////            } else if (messageList.get(pos).getMsgReceived()) {
////                newRow = inflater.inflate(R.layout.receive_layout, null);
////                TextView itemText = newRow.findViewById(R.id.messageText);
////                itemText.setText(getItem(pos).getMessage());
////            }
//            return newRow;
//
//        }


    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

}