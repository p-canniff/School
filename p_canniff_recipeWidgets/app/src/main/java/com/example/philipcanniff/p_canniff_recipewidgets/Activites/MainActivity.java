package com.example.philipcanniff.p_canniff_recipewidgets.Activites;

import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.philipcanniff.p_canniff_recipewidgets.DataFiles.DataManager;
import com.example.philipcanniff.p_canniff_recipewidgets.DataFiles.Globals;
import com.example.philipcanniff.p_canniff_recipewidgets.DataFiles.RecipeObject;
import com.example.philipcanniff.p_canniff_recipewidgets.DataFiles.RecipeServicePull;
import com.example.philipcanniff.p_canniff_recipewidgets.Fragments.Recipe_List_Fragment;
import com.example.philipcanniff.p_canniff_recipewidgets.R;
import com.example.philipcanniff.p_canniff_recipewidgets.Widget_One.WidgetOneProvider;

import java.util.ArrayList;

public class MainActivity extends Activity {

    UpdateReceiver mUpdateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<String> choices = new ArrayList<>();
        choices.add("Standard");
        choices.add("Favorites");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, choices);
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        ActionBar.OnNavigationListener navigationListener = new ActionBar.OnNavigationListener() {

            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {

                switch(itemPosition){

                    case 1:
                        ArrayList<RecipeObject> recipes = DataManager.getInstance(getApplicationContext()).getRecipes();
                        Recipe_List_Fragment list = new Recipe_List_Fragment().newInstance(recipes);
                        getFragmentManager().beginTransaction().replace(R.id.list_container, list, Recipe_List_Fragment.TAG).commit();
                        break;
                    case 0:
                        Toast.makeText(getApplicationContext(), "Loading Recipes...", Toast.LENGTH_SHORT).show();
                        Intent service = new Intent(getApplicationContext(), RecipeServicePull.class);
                        startService(service);

                        break;
                    default:
                        break;

                }

                return false;
            }
        };

        /** Setting dropdown items and item navigation listener for the actionbar */
        getActionBar().setListNavigationCallbacks(adapter, navigationListener);



        Intent service = new Intent(this, RecipeServicePull.class);
        startService(service);




    }

    @Override
    protected void onResume() {
        super.onResume();

        mUpdateReceiver = new UpdateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Globals.SEND_LIST);
        registerReceiver(mUpdateReceiver, filter);

    }

    @Override
    protected void onPause() {
        super.onPause();



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
//        if (id == R.id.action_refresh) {
//
//            Intent service = new Intent(this, RecipeServicePull.class);
//            startService(service);
//
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    private class UpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(Globals.SEND_LIST)) {

                ArrayList<RecipeObject> recipes = (ArrayList<RecipeObject>) intent.getExtras().getSerializable(Globals.RECIPE_ARRAY);

                Recipe_List_Fragment list = new Recipe_List_Fragment().newInstance(recipes);
                getFragmentManager().beginTransaction().replace(R.id.list_container, list, Recipe_List_Fragment.TAG).commit();



            }
        }
    }
}
