package com.example.sammy.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * created by Samantha Rhodes
 */
public class MainActivity extends AppCompatActivity {
    EditText title,link,description;
    ListView info;
    Button b1,b2;
    private String finalUrl="http://events.ithaca.edu/calendar.xml?event_types%5B%5D=27437";
    private HandleXML obj;
    private ArrayList<String[]> events = new ArrayList<String[]>();
    private ArrayList<String[]> events2 = new ArrayList<String[]>();
    private ArrayAdapter<String[]> musicEvents;
    private ArrayAdapter<String[]> musicEvents2;
    Menu menu;
    EditText detailInputView;
    protected DBHelper mDBHelper;
    private ArrayList<String[]> take2 = new ArrayList<String[]>();

    /**
     * This OnCreate function connects up with the activity_main.xml
     * It starts by creating a connection with the SQLite database and the RSS feed.
     * There are 2 button listeners for the clear search and to see the RSS feed
     * Edit text setOnKeyListener function that filters the listview for the entered string.
     * List view setItemOnClickListener which calls the ConcertDetail java file for a more detailed concert information.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = (ListView) findViewById(R.id.listView);
        mDBHelper = new DBHelper(this);
        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);

        obj = new HandleXML(finalUrl);
        events = obj.fetchXML();
        musicEvents = new UserAdapter(this, events);
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(musicEvents);

        detailInputView = (EditText) findViewById(R.id.etSearch);
        detailInputView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detailInputView.setFocusable(true);
                detailInputView.setFocusableInTouchMode(true);
                return false;
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"The Search have been cleared!", Toast.LENGTH_LONG).show();
                detailInputView.setText("");
                events2 = obj.fetchXML();
                musicEvents.clear();
                musicEvents.addAll(events2);
                musicEvents.notifyDataSetChanged();
            }
        });

        detailInputView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                    && (i == KeyEvent.KEYCODE_ENTER)){
                    String searchVal = detailInputView.getText().toString().toLowerCase();
                    ArrayList<String[]> newList = new ArrayList<String[]>();
                    for (i = 0; i < events.size(); i++){
                        String[] vals = events.get(i);
                        if (vals[0].toLowerCase().contains(searchVal)){
                            newList.add(vals);
                        }
                        //Toast.makeText(MainActivity.this, "" + vals, Toast.LENGTH_LONG).show();
                    }
                    musicEvents.clear();
                    musicEvents.addAll(newList);
                    musicEvents.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                Intent intent = new Intent(getApplicationContext(), ConcertDetail.class);
                String[] values = musicEvents.getItem(i);
                String cinformation = values[0] + " \n \nLink: " + values[1] + "\n \nConcert Category: " + values[2];
                String pinfo = values[3];
                intent.putExtra("photoInfo", pinfo);
                intent.putExtra("details", cinformation);
                intent.putExtra("array", values);
                //Toast.makeText(MainActivity.this, "Hello" + values[0] , Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(MainActivity.this,second.class);
                startActivity(in);

            }
        });
    }

    /**
     * Inflates the menu at the top of the page.
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my, menu);
        return true;
    }

    /**
     *  When the Wish list field is selected on the menu it calls the WishList class.
     * @param item
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
//        Toast.makeText(this, "Hello!!! ", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), WishList.class);
        startActivity(intent);
        return true;
    }

}
