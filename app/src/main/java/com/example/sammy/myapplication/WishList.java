package com.example.sammy.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by sammy on 5/5/2017.
 */

public class WishList extends AppCompatActivity {

    ArrayList<String[]> wishList = new ArrayList<String[]>();
    ArrayAdapter<String[]> wishadapter;

    protected DBHelper mDBHelper;

    /**
     * Creates a connection to the SQLite database.
     * This page is linked to the wish_list.xml file.
     * Prints the list grabbed from the database onto the XML listview.
     * When an item in the wish list is click it creates an Intent and passes information to the ConcertDetail class.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wish_list);
        Intent intent = getIntent();
        mDBHelper = new DBHelper(this);

        wishList = mDBHelper.getData();
        wishadapter= new UserAdapter(this, wishList);
        final ListView listView = (ListView) findViewById(R.id.wishlist);
        listView.setAdapter(wishadapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                Intent intent = new Intent(getApplicationContext(), ConcertDetail.class);
                String[] values = wishadapter.getItem(i);
                String cinformation = values[0] + " \n \nLink: " + values[1] + "\n \nConcert Category: " + values[2];
                String pinfo = values[3];
                intent.putExtra("photoInfo", pinfo);
                intent.putExtra("details", cinformation);
                intent.putExtra("array", values);
                startActivity(intent);
            }
        });
    }
}
