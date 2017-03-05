package com.walletmix.www.saveimagetodatabasetest;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    // Shared preferences file name
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "ContactImagesUnniqueName";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.img);
        TextView imagePathTv = (TextView) findViewById(R.id.imagePath);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        String imagePath = saveBitmapToInternalStorage(largeIcon);
        imagePathTv.setText(imagePath);
        imageView.setImageBitmap(getBitmapFromInternalStorage(imagePath));
    }

    private String saveBitmapToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(MainActivity.this);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg.sid");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert fos != null;
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath()+"/profile.jpg.sid";
    }

    private Bitmap getBitmapFromInternalStorage(String imagePath){
	/*File imgFile = new  File(imagePath+"/profile.jpg.sid");
        if(imgFile.exists()){
            //Drawable d = new BitmapDrawable(getResources(), myBitmap);
            return BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        } else return null;*/

        try {
            File f=new File(imagePath/*, "profile.jpg.sid"*/);
            return BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
