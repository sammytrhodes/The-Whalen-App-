package com.example.sammy.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by sammy on 5/5/2017.
 */

public class DBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Concert_Wish_List";

    private static final String DATABASE_TABLE = "WishList";
    private static final String CONCERT_NAME = "Concert";
    private static final String CONCERT_URL= "Link";
    private static final String CONCERT_DESCRIPTION= "Description";
    private static final String CONCERT_PHOTO= "Photo";
    ArrayList<String[]> wishList = new ArrayList<String[]>();

    /**
     * Creates a database if there is none.
     * @param context
     */
    public DBHelper (Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates a table with a specific name and column values.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        String table = "CREATE TABLE " + DATABASE_TABLE + "("
                + CONCERT_NAME + " TEXT, "
                + CONCERT_URL + " TEXT, "
                + CONCERT_DESCRIPTION + " TEXT, "
                + CONCERT_PHOTO + " TEXT"
                + ")";

        db.execSQL(table);
    }

    /**
     * If the app is updated then delete the old table.
     * @param database
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // DROP OLDER TABLE IF EXISTS
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

        // CREATE TABLE AGAIN
        onCreate(database);
    }

    /**
     * This adds a new entry to the wish list database.
     * There are four values that each entry holds.
     * @param concertDetails
     */
    public void addConcert(String[] concertDetails){
        Log.i("Concert Details ", concertDetails[0]);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CONCERT_NAME, concertDetails[0]);
        values.put(CONCERT_URL, concertDetails[1].toString()); //
        values.put(CONCERT_DESCRIPTION, concertDetails[2]);
        values.put(CONCERT_PHOTO, concertDetails[3].toString());

        db.insert(DATABASE_TABLE, null, values);
        db.close();
    }

    /**
     * This is called when the user would like to view the database entries.
     * This returns all of the database values in an ArrayList<String[]>
     * @return wishList
     */
    public ArrayList<String[]> getData(){
        String query = "SELECT * FROM " + DATABASE_TABLE;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                String[] values = new String[4];
                values[0] = cursor.getString(0);
                values[1] = cursor.getString(1);
                values[2] = cursor.getString(2);
                values[3] = cursor.getString(3);

                wishList.add(values);
            }while(cursor.moveToNext());
        }
        return wishList;
    }

}
