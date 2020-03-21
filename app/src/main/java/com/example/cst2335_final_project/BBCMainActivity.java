package com.example.cst2335_final_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import static com.example.cst2335_final_project.R.id.bbclistview;

public class BBCMainActivity extends AppCompatActivity {
    ListView bbcNewsView;
    ArrayList<BBCNewsArticle> articles ;
    MyListAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbcmain);
        articles = new ArrayList<>();
        mockData();
        bbcNewsView = findViewById(bbclistview);

        EditText putText = findViewById(R.id.adding_text);
        Button toastBtn = findViewById(R.id.toast_btn);
        Button snackBtn = findViewById(R.id.snackbar_btn);

        toastBtn.setOnClickListener(v->{
            Toast.makeText(getApplicationContext(),putText.getText().toString(),Toast.LENGTH_SHORT).show();

        });

        snackBtn.setOnClickListener(v->{

            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), putText.getText().toString(), Snackbar.LENGTH_LONG);
            snackbar.show();

        });




        myAdapter = new MyListAdapter();
        bbcNewsView.setAdapter(myAdapter);
        ProgressBar spinner;
        spinner = (ProgressBar)findViewById(R.id.detailed_progressbar);
       // spinner.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);


        bbcNewsView.setOnItemClickListener( (p,b,position,id)-> {
            BBCNewsArticle currentItems = articles.get(position);
        //intent
            Intent intent = new Intent(getBaseContext(), NewDetailActivity.class);
            intent.putExtra("title", currentItems.getTitle());
            intent.putExtra("date", currentItems.getDate());
            intent.putExtra("description", currentItems.getDescription());
            intent.putExtra("url", currentItems.getUrl());

            startActivity(intent);
        });

        bbcNewsView.setOnItemLongClickListener( (p,b,position,id)-> {
            BBCNewsArticle currentItems = articles.get(position);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Alert")
                    .setMessage("Do you ...")

                    .setPositiveButton("Yes", (click, arg) ->{
                        articles.remove(position);
                        myAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("No", (click, arg) ->{
                        myAdapter.notifyDataSetChanged();
                    })
                    .create().show();
            return true;
        });


    }

    private void mockData(){
        articles.add(new BBCNewsArticle(1,"title 1","sadasdas","asdsadsa","SAdasda"));
        articles.add(new BBCNewsArticle(2,"title 2","sadasdas","asdsadsa","SAdasda"));
        articles.add(new BBCNewsArticle(3,"title 3","sadasdas","asdsadsa","SAdasda"));
        articles.add(new BBCNewsArticle(4,"title 4","sadasdas","asdsadsa","SAdasda"));
        articles.add(new BBCNewsArticle(5,"title 5","sadasdas","asdsadsa","SAdasda"));
        articles.add(new BBCNewsArticle(6,"title 6","sadasdas","asdsadsa","SAdasda"));
        articles.add(new BBCNewsArticle(7,"title 7","sadasdas","asdsadsa","SAdasda"));
        articles.add(new BBCNewsArticle(8,"title 8","sadasdas","asdsadsa","SAdasda"));
        articles.add(new BBCNewsArticle(9,"title 9","sadasdas","asdsadsa","SAdasda"));
        articles.add(new BBCNewsArticle(10,"title 10","sadasdas","asdsadsa","SAdasda"));

    }

    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return articles.size();
        }

        @Override
        public Object getItem(int position) {

            BBCNewsArticle msg = articles.get(position);
            return msg;

        }

        @Override
        public long getItemId(int position) {
            BBCNewsArticle msg = articles.get(position);
            return msg.getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            BBCNewsArticle article = (BBCNewsArticle) getItem(position);
            View newView;
            TextView tView;
                newView =inflater.inflate(R.layout.newsbbclist, parent, false);

            tView = newView.findViewById(R.id.bbclist_row_text);
            tView.setText(article.getTitle());
            return newView;
        }
    }
}
