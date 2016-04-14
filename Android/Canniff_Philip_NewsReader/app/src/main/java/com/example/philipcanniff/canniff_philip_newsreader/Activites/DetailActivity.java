package com.example.philipcanniff.canniff_philip_newsreader.Activites;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.philipcanniff.canniff_philip_newsreader.DataFiles.Article;
import com.example.philipcanniff.canniff_philip_newsreader.DataFiles.DataManager;
import com.example.philipcanniff.canniff_philip_newsreader.Fragments.Detail_Fragment;
import com.example.philipcanniff.canniff_philip_newsreader.Fragments.News_List_Fragment;
import com.example.philipcanniff.canniff_philip_newsreader.R;

import java.util.ArrayList;

/**
 * Created by philipcanniff on 9/22/15.
 */
public class DetailActivity extends Activity {

    boolean exists = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Article article = (Article) getIntent().getSerializableExtra(MainActivity.ARTICLE);

        Detail_Fragment detailFrag = new Detail_Fragment().newInstance(article);
        getFragmentManager().beginTransaction().replace(R.id.detail_container, detailFrag, News_List_Fragment.TAG).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Article article = (Article) getIntent().getSerializableExtra(MainActivity.ARTICLE);

        int id = item.getItemId();

        if (id == R.id.Fav) {

            ArrayList<Article> favorites = DataManager.getInstance(this).getFavorites();

            for (int i = 0; i < favorites.size(); i++){

                String title = favorites.get(i).title;

                if (title.equals(article.title)){

                    exists = true;
                    Toast.makeText(this, "Article already exists in Favorites", Toast.LENGTH_LONG).show();

                }

            }
            if (exists == false){

                DataManager.getInstance(this).appendPerson(article);
                DataManager.getInstance(this).saveFavoriteArray();
                Toast.makeText(this, "Saved to Favorites", Toast.LENGTH_LONG).show();

            }

        } else if (id == R.id.FullArticle){

            Intent intent = new Intent(this, WebViewActivity.class);
            intent.setData(Uri.parse(article.getUrl()));
            startActivity(intent);

        } else if (id == R.id.Share){

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.putExtra("article", article);
            startActivity(intent);


        }

        return super.onOptionsItemSelected(item);

    }

}
