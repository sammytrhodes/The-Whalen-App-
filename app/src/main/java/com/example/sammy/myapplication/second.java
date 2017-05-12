package com.example.sammy.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
/**
 * A new page that shows the RSS feed that the events are parsed from.
 * @author Created by sammy on 3/7/2017.
 */

public class second extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_main);
        WebView w1=(WebView)findViewById(R.id.webView);
        w1.loadUrl("http://events.ithaca.edu/calendar.xml?event_types%5B%5D=27437");
    }
}
