package com.example.sammy.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

/**
 * Created by sammy on 4/5/2017.
 */

public class ConcertDetail extends AppCompatActivity{
    TextView nameTextView;
    ImageView photo;
    String details;
    String photoInfo;
    Button wish;
    String[] array;
    protected  DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.concert_view);
        wish =(Button)findViewById(R.id.wish);
        Intent intent = getIntent();
        mDBHelper = new DBHelper(this);

        photoInfo = intent.getStringExtra("photoInfo");
        details = intent.getStringExtra("details");
        array = intent.getStringArrayExtra("array");

        new DownloadImageFromInternet((ImageView) findViewById(R.id.cphoto))
                .execute(photoInfo);

        nameTextView = (TextView) findViewById(R.id.name);
        nameTextView.setText(intent.getStringExtra("details"));

        wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConcertDetail.this,"Added to Your Wishlist", Toast.LENGTH_LONG).show();
                mDBHelper.addConcert(array);

            }
        });


    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.my, menu);
//        return true;
//    }
    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
            //Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }

    }

}
