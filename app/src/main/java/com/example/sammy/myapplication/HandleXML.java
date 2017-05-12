package com.example.sammy.myapplication;

/**
 * Created by sammy on 3/7/2017.
 */

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HandleXML {
    private String title = "name";
    private String link = "link";
    private String description = "description";
    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public ArrayList<String[]> data = new ArrayList<String[]>();

    public volatile boolean parsingComplete = true;

    /**
     * sets the variable urlString to the parameter url
     * @param url
     */
    public HandleXML(String url){
        this.urlString = url;
    }

    /**
     * @return title
     */
    public String getTitle(){
        return title;
    }

    /**
     * @return link
     */
    public String getLink(){
        return link;
    }

    /**
     * @return decription
     */
    public String getDescription(){
        return description;
    }

    /**
     * @return data
     */
    public ArrayList<String[]> getData (){return data;}

    /**
     * Reads through the RSS feed and creates an ArrayList of String[] that contains the important event information.
     * @param myParser
     */
    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text=null;
        //String arrayText = "";
        String[] textArr = new String[4];

        try {
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                String name=myParser.getName();

                switch (event) {
                    case XmlPullParser.START_TAG:
                        if(name.equals("media:content")){
                            textArr[3] = myParser.getAttributeValue(null, "url");
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:

                        if (name.equals("title")) {
                            title = text;
                            textArr[0] = text;
                        } else if (name.equals("link")) {
                            link = text;
                            textArr[1] = text;
                        } else if (name.equals("category")) {
                            description = text;
                            textArr[2] = text;
                        } else if (name.equals("item")) {
                            data.add(textArr);
                            textArr = new String[4];
                        }else{

                        }
                        break;
                }
                event = myParser.next();
            }
            parsingComplete = false;
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * FetchXML connects to the RSS feed and creates a parser to read the file.
     * @return data
     */
    public ArrayList<String[]> fetchXML(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {

                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);

                    // Starts the query
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(stream, null);

                    parseXMLAndStoreIt(myparser);
                    stream.close();
                }

                catch (Exception e) {
                }
            }
        });
        thread.start();
        return data;
    }
}
