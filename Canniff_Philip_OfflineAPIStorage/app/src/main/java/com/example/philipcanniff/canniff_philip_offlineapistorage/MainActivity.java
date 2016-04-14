package com.example.philipcanniff.canniff_philip_offlineapistorage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;


public class MainActivity extends Activity {

    Spinner mySpin;
    ListView myList;
    Context mContext;
    String spinnerVal;
    MyAdapter baseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String spinnerVal;
        myList = (ListView) findViewById(R.id.listView);
        mySpin = (Spinner) findViewById(R.id.spinner);
        mContext = this;

        mySpin.setOnItemSelectedListener(itemSelected);

    }
    AdapterView.OnItemSelectedListener itemSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            String string = mySpin.getSelectedItem().toString();
            checkConnectionPull(string);

            Log.i("Hit",  "HIT");



        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };



    public void checkConnectionPull(String string){

        ConnectivityManager mgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);


        if (mgr != null) {
            NetworkInfo info = mgr.getActiveNetworkInfo();

            if (info != null) {
                if (info.isConnected()) {
                    Log.i("Connection Status", "Network is connected");


                    String redditURL = "http://www.reddit.com/r/" + URLEncoder.encode(string) + "/hot.json";
                    ConnectionDataPull task = new ConnectionDataPull(redditURL, myList, mContext);
                    task.execute();

                } else {


                    Log.i("Connect Status", "There is no network connection.");

                }

            } else{
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle("No Connection");
                alert.setMessage("Please find internet!");
                alert.show();


                File input = Environment.getExternalStorageDirectory();
                File postSaved = new File(input, "PostData.bin");

                try {

                    FileInputStream fis = new FileInputStream(postSaved);
                    ObjectInputStream ois = new ObjectInputStream(fis);

                    ArrayList<PostData> obj2 = (ArrayList<PostData>) ois.readObject();
                    ois.close();

                    baseAdapter = new MyAdapter(mContext, obj2);
                    myList.setAdapter(baseAdapter);
                    Log.i("Failed to Save", "Failed Saving to Storage");

                } catch(Exception e ) {

                    AlertDialog.Builder alert2 = new AlertDialog.Builder(mContext);
                    alert.setTitle("No Data is Saved");
                    alert.setMessage("Please find internet!");
                    alert.show();

                    Log.i("Failed to Load", "Failed Loading from Storage");
                    e.printStackTrace();


                }

            }



        } else if (mgr == null) {





            AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
            alert.setTitle("No Connection");
            alert.setMessage("Please find internet!");
            alert.show();






        }






    }


    }



