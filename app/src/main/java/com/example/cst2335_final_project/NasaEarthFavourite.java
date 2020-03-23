package com.example.cst2335_final_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
    SQLiteDatabase db;

    private static final String ACTIVITY_NAME = "NasaEarthFavourite";

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

        MyOpener myOp = new MyOpener(this);
        db = myOp.getWritableDatabase();

        loadEarths();

        ContentValues cv = new ContentValues();
        cv.put(MyOpener.COL_LATITUDE, imgLat);
        cv.put(MyOpener.COL_LONGITUDE, imgLong);
        cv.put(MyOpener.COL_DATE, imgDate);
        cv.put(MyOpener.COL_URL, imgUrl);

        Long newId = db.insert(MyOpener.TABLE_NAME, null, cv);

        NasaEarth nasaEarth = new NasaEarth(newId, imgLat,imgLong,imgDate,imgUrl);
        earthList.add(nasaEarth);

        myAdapter.notifyDataSetChanged();


        myList.setOnItemLongClickListener((parent, view, pos, id) -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(NasaEarthFavourite.this);

            builder.setTitle("Do you want to delete this?")
                    .setMessage("The selected row is: " + pos + "\n" + "The database is: " + id)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?", new String[]{Long.toString(earthList.get(pos).getId())});
                            earthList.remove(pos);
                            myAdapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();

            return true;
        });

    }

    private void loadEarths() {
        String[] columns = {MyOpener.COL_ID, MyOpener.COL_LATITUDE, MyOpener.COL_LONGITUDE, MyOpener.COL_DATE, MyOpener.COL_URL};
        Cursor cursor = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);
        printCursor(cursor);

        int idColIndex = cursor.getColumnIndex(MyOpener.COL_ID);
        int latitudeColIndex = cursor.getColumnIndex(MyOpener.COL_LATITUDE);
        int longitudeColIndex = cursor.getColumnIndex(MyOpener.COL_LONGITUDE);
        int dateColIndex = cursor.getColumnIndex(MyOpener.COL_DATE);
        int urlColIndex = cursor.getColumnIndex(MyOpener.COL_URL);

        // if (cursor != null && cursor.moveToFirst())

        while (cursor.moveToNext()) {
            long id = cursor.getLong(idColIndex);
            String latitude = cursor.getString(latitudeColIndex);
            String longitude = cursor.getString(longitudeColIndex);
            String date = cursor.getString(dateColIndex);
            String url = cursor.getString(urlColIndex);

            NasaEarth ne = new NasaEarth(id, latitude, longitude, date, url);
            earthList.add(ne);
        }
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

                newRow = inflater.inflate(R.layout.activity_nasaearth_list, parent, false);


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


    public void printCursor(Cursor cursor) {
        int idColIndex = cursor.getColumnIndex(MyOpener.COL_ID);
        int latitudeColIndex = cursor.getColumnIndex(MyOpener.COL_LATITUDE);
        int longitudeColIndex = cursor.getColumnIndex(MyOpener.COL_LONGITUDE);
        int dateColIndex = cursor.getColumnIndex(MyOpener.COL_DATE);
        int urlColIndex = cursor.getColumnIndex(MyOpener.COL_URL);
        String strDatabaseVersion = "Database version number: " + MyOpener.VERSION_NUM;
        String strNumberOfColumns = "Number of columns = " + cursor.getColumnCount();
        String strNumberOfResults = "Number of results = " + cursor.getCount();
        String strColumnNames = "Name of the columns: "
                + cursor.getColumnName(idColIndex) + ", "
                + cursor.getColumnName(latitudeColIndex) + ", "
                + cursor.getColumnName(longitudeColIndex) + ", "
                + cursor.getColumnName(dateColIndex) + ", "
                + cursor.getColumnName(urlColIndex);
        StringBuffer buffer = new StringBuffer();

        while (cursor.moveToNext()) {
            buffer.append("id: " + cursor.getString(idColIndex) + " ");
            buffer.append("latitude: " + cursor.getString(latitudeColIndex) + " ");
            buffer.append("longitude: " + cursor.getString(longitudeColIndex) + " ");
            buffer.append("latitude: " + cursor.getString(dateColIndex) + " ");
            buffer.append("isSent: " + cursor.getString(urlColIndex) + " ");
        }
        ;

        cursor.moveToFirst();
        Log.d(ACTIVITY_NAME, strDatabaseVersion + "\n" + strNumberOfColumns + "\n" + strColumnNames
                + "\n" + strNumberOfResults + "\n" + buffer.toString());
    }

}