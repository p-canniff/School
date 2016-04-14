package com.example.philipcanniff.p_canniff_recipewidgets.Activites;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.philipcanniff.p_canniff_recipewidgets.DataFiles.DataManager;
import com.example.philipcanniff.p_canniff_recipewidgets.DataFiles.Globals;
import com.example.philipcanniff.p_canniff_recipewidgets.DataFiles.RecipeObject;
import com.example.philipcanniff.p_canniff_recipewidgets.Fragments.Recipe_Detail_Fragment;
import com.example.philipcanniff.p_canniff_recipewidgets.R;

/**
 * Created by philipcanniff on 10/19/15.
 */
public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Recipe_Detail_Fragment detailFrag = new Recipe_Detail_Fragment().newInstance();
        getFragmentManager().beginTransaction().replace(R.id.detail_container, detailFrag, Recipe_Detail_Fragment.TAG).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {

            RecipeObject recipe = (RecipeObject) getIntent().getSerializableExtra(Globals.SINGLE_RECIPE);

            DataManager.getInstance(this).appendRecipe(recipe);

            Toast.makeText(this, "Recipe Saved to Favorites", Toast.LENGTH_SHORT).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
