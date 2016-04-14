package com.example.philipcanniff.canniff_philip_offlineapistorage;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by philipcanniff on 8/31/15.
 */
public class ConnectionDataPull extends AsyncTask<String, Void, String> {

    String mUrl;
    ListView mListView;
    Context mContext;
    ArrayList<String> myAuthors;
    ArrayList<PostData> myPosts;
    MyAdapter baseAdapter;

    @Override
    protected String doInBackground(String... params) {

        myPosts = new ArrayList<>();


        try{

            URL url = new URL(mUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.connect();

            InputStream is = connection.getInputStream();

            String data = IOUtils.toString(is);

            is.close();

            connection.disconnect();

            return data;


        } catch(IOException e) {

            e.printStackTrace();

        }

        return null;

    }

    @Override
    protected void onPostExecute(String s) {

        Log.i("App Status", "Parse" +
                "\n Post Data");

        try {

            JSONObject outObj = new JSONObject(s);

            JSONArray dataArray = outObj.getJSONObject("data").getJSONArray("children");

            Log.i("ENTRIES: ",  dataArray.length() + "");


            for(int i = 0; i < dataArray.length(); i++){

                myAuthors = new ArrayList<>();

                JSONObject postData = dataArray.getJSONObject(i).getJSONObject("data");


                String postAuthor = postData.getString("author");

                String postTitle = postData.getString("title");



                Log.i("ENTRIES: ",  postTitle + "");




               PostData book = new PostData(postTitle, postAuthor);

                myPosts.add(book);





            }


            baseAdapter = new MyAdapter(mContext, myPosts);
            mListView.setAdapter(baseAdapter);

            File output = Environment.getExternalStorageDirectory();
            File postSaved = new File(output, "PostData.bin");


            try {

                FileOutputStream fos = new FileOutputStream(postSaved);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(myPosts);

                Log.i("Saved", "Successfully Saved to Storage");


            } catch(Exception e ){

                Log.i("Failed to Save", "Failed Saving to Storage");
                e.printStackTrace();
            }



        } catch(JSONException e ){

            e.printStackTrace();

        }

    }

    public ConnectionDataPull(String mUrl, ListView mListView, Context mContext) {
        this.mUrl = mUrl;
        this.mListView = mListView;
        this.mContext = mContext;
    }
}
