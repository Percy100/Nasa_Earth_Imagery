package com.example.cst2335_final_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NasaEarthFavourite extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    BaseAdapter myAdapter;
    ListView myList;
    String imgUrl;
    ImageView imageIcon;
    SQLiteDatabase db;

    private ProgressBar progressBar;

    private NasaEarthFragment dFragment;

    DrawerLayout drawer;
    NavigationView navigationView;

    EditText txtTitle;
    Button btnGoToOthers;
    Button btnGoToSearch;
    RelativeLayout nasaFavourite;


    private static final String ACTIVITY_NAME = "NasaEarthFavourite";

    ArrayList<NasaEarth> earthList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa_earth_favourite);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        txtTitle = findViewById(R.id.TitleText);
        btnGoToOthers = findViewById(R.id.DetailsButton);
        btnGoToSearch = findViewById(R.id.SearchButton);

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

        nasaFavourite = findViewById(R.id.nasaFavouriteLayout);

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


//        btnGoToDet.setOnClickListener((view)->{
//            Snackbar.make(nasaFavourite, "Loading Details Page", Snackbar.LENGTH_LONG).show();
////            Intent intent = new Intent(NasaEarthFavourite.this, NasaEarthDetailsMainActivity.class);
////            startActivity(intent);
//
//        });
//
//
//        btnGoToSearch.setOnClickListener((view)-> {
//            Toast.makeText(NasaEarthFavourite.this, "Loading Search Page", Toast.LENGTH_LONG).show();
//        });

        btnGoToOthers.setOnClickListener((view)-> {
            Toast.makeText(NasaEarthFavourite.this, "Click on menu to access other pages", Toast.LENGTH_LONG).show();
        });

        btnGoToSearch.setOnClickListener((view)-> {
            Snackbar snackbar = Snackbar.make(nasaFavourite, "Loading Home Page", Snackbar.LENGTH_LONG).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(NasaEarthFavourite.this, MainActivity.class));
                }
            });
            snackbar.show();
        });


        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

           //     Message item = messageList.get(position);

//                imgLat = editT1.getText().toString();
//                inputLongitude = editT2.getText().toString();
//                String date ="";
//                String url ="";
//                NasaEarth ne = new NasaEarth(inputLatitude, inputLongitude, date, url);

                NasaEarth nasaEarth = earthList.get(position);
                String lat = nasaEarth.getLatitude();
                String lon = nasaEarth.getLongitude();

                Bundle dataToPass = new Bundle();
                dataToPass.putString("Latitude", lat);
                dataToPass.putString("Longitude", lon);

                //Use a Bundle to pass the message string, and the database id of the selected item to the fragment in the FragmentTransaction
                // if(findViewById(R.id.fragmentLocation) != null) {

                    dFragment = new NasaEarthFragment();
                    dFragment.setArguments(dataToPass);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentLocation, dFragment) //Add the fragment in FrameLayout
                            .commit();

                    }

        });
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
            AlertDialog.Builder builder = new AlertDialog.Builder(NasaEarthFavourite.this);

            builder.setTitle("INSTRUCTIONS")
                    .setMessage("* Click on menu to access other Pages" + "\n" + "* Scroll to view complete favourite list " + "\n" + "* Click on an item on the favourite to view the details " + "\n" + "* Long-Click on an item on the favourite to delete item ")
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