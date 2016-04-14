package com.example.philipcanniff.canniff_philip_newsreader.DataFiles;

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
 * Created by philipcanniff on 9/22/15.
 */
public class ConnectionPull extends AsyncTask<String, Void, String> {

    String mUrl;
    Context mContext;
    ArrayList<String> myAuthors;
    ArrayList<Article> myArticles;

    sendToList listInterfacing;

    public interface sendToList{
        public void sendArticles(ArrayList<Article> _articles);
    }

    @Override
    protected String doInBackground(String... params) {

        if (mContext instanceof sendToList){

            listInterfacing = (sendToList)mContext;
        }

        myArticles = new ArrayList<>();

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

            Log.i("ENTRIES: ", dataArray.length() + "");


            for (int i = 0; i < dataArray.length(); i++) {

                myAuthors = new ArrayList<>();

                JSONObject postData = dataArray.getJSONObject(i).getJSONObject("data");

                String postAuthor = postData.getString("author");

                String postTitle = postData.getString("title");

                String postUrl = postData.getString("url");

                Log.i("ENTRIES: ", postTitle + "");

                Article book = new Article(postTitle, postAuthor, postUrl);

                myArticles.add(book);

            }

            listInterfacing.sendArticles(myArticles);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public ConnectionPull(String mUrl, Context mContext) {
        this.mUrl = mUrl;
        this.mContext = mContext;
    }

}
