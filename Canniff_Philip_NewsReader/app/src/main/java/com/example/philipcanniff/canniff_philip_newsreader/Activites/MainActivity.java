package com.example.philipcanniff.canniff_philip_newsreader.Activites;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.philipcanniff.canniff_philip_newsreader.DataFiles.Article;
import com.example.philipcanniff.canniff_philip_newsreader.DataFiles.ConnectionPull;
import com.example.philipcanniff.canniff_philip_newsreader.DataFiles.DataManager;
import com.example.philipcanniff.canniff_philip_newsreader.Fragments.News_List_Fragment;
import com.example.philipcanniff.canniff_philip_newsreader.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends Activity implements ConnectionPull.sendToList, News_List_Fragment.sendToDetail {

    public static final String ARTICLE = "Article";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkConnectionPull();

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
        if (id == R.id.about) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setMessage("Philip Canniff - Reddit Technology News Reader");
            alert.setCancelable(false);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.show();
        } else if (id == R.id.FavoriteList){

            if (item.getTitle().equals("Favorites")){

                ArrayList<Article> favorites = DataManager.getInstance(this).getFavorites();

                if (favorites.size() == 0){

                    Toast.makeText(this, "No favorites have been saved.", Toast.LENGTH_LONG).show();

                } else {

                    item.setTitle("News Feed");
                    News_List_Fragment listFrag = News_List_Fragment.newInstance(favorites);
                    getFragmentManager().beginTransaction().replace(R.id.list_container, listFrag, News_List_Fragment.TAG).commit();

                }

            } else {

                item.setTitle("Favorites");
                checkConnectionPull();

            }

        }

        return super.onOptionsItemSelected(item);
    }

    public void checkConnectionPull(){

        ConnectivityManager mgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);


        if (mgr != null) {
            NetworkInfo info = mgr.getActiveNetworkInfo();

            if (info != null) {
                if (info.isConnected()) {
                    Log.i("Connection Status", "Network is connected");


                    String redditURL = "http://www.reddit.com/r/" + "Technology" + "/hot.json";
                    ConnectionPull task = new ConnectionPull(redditURL, this);
                    task.execute();

                } else {

                    Log.i("Connect Status", "There is no network connection.");

                }

            } else {

                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("No Connection");
                alert.setMessage("Please find internet!");
                alert.show();

            }

        } else if (mgr == null) {


            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("No Connection");
            alert.setMessage("Please find internet!");
            alert.show();

        }

    }

    @Override
    public void sendArticles(ArrayList<Article> _articles) {
        News_List_Fragment listFrag = News_List_Fragment.newInstance(_articles);
        getFragmentManager().beginTransaction().replace(R.id.list_container, listFrag, News_List_Fragment.TAG).commit();
    }

    @Override
    public void sendData(Article _article) {

        Intent intent = new Intent();
        intent.setClass(this, DetailActivity.class);
        intent.putExtra(ARTICLE, _article);
        startActivity(intent);

    }
}
