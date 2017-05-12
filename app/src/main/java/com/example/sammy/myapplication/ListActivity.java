package com.example.sammy.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

/**
 * Created by sammy on 3/29/2017.
 *
 */

public class ListActivity extends Activity {
    ListView listView;
    List<ConcertDetail> concertList;

    /**
     * Basic listview connection to the activity_main.xml file.
     * @param saveInstanceState
     */
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
    }

}
