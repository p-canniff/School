package com.example.philipcanniff.p_canniff_recipewidgets.DataFiles;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.philipcanniff.p_canniff_recipewidgets.Widget_One.WidgetOneProvider;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by philipcanniff on 10/20/15.
 */
public class RecipeServicePull extends IntentService {

    Bitmap bitmap;
    byte[] savedBytes;

    public RecipeServicePull() {
        super("RecipeServicePull");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        ArrayList<RecipeObject> allRecipes = new ArrayList<>();

        try {

            Log.i("SERVICE STARTED", "STARTED SERVICE ::::::::::::::::::: STARTED SERVICE ");

            URL url = new URL("http://api.yummly.com/v1/api/recipes?_app_id=905ce292&_app_key=072a14bad8e84c22d2efdc8dae1edfbb&");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.connect();

            InputStream is = connection.getInputStream();

            String data = IOUtils.toString(is);

            is.close();

            connection.disconnect();

            JSONObject outObj = new JSONObject(data);

            JSONArray dataObject = outObj.getJSONArray("matches");

            for (int i = 0; i < dataObject.length(); i++){

                ArrayList<String> ingredients = new ArrayList<>();

                JSONObject object = (JSONObject) dataObject.get(i);

                JSONArray ingredientsArray = object.getJSONArray("ingredients");

                for (int z = 0; z < ingredientsArray.length(); z++) {

                    String oneIngredient = (String) ingredientsArray.get(z);

                    ingredients.add(oneIngredient);

                }

                String currentRecipe = object.getString("recipeName");

                JSONObject imageObject = object.getJSONObject("imageUrlsBySize");

                String recipeIconUrl = imageObject.getString("90");

                try {

                    URL imageUrl = new URL(recipeIconUrl);
                    HttpURLConnection connection2 = (HttpURLConnection) imageUrl.openConnection();
                    connection2.connect();

                    InputStream imageIS = connection2.getInputStream();
                    byte[] image = IOUtils.toByteArray(imageIS);
                    imageIS.close();

                    bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                    savedBytes = image;



                } catch (Exception e){

                    e.printStackTrace();

                }

                RecipeObject recipe = new RecipeObject(currentRecipe, savedBytes, ingredients);
                APIDataManager.getInstance(getApplicationContext()).appendRecipe(recipe);
                allRecipes.add(recipe);


            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent listData = new Intent(Globals.SEND_LIST);
        listData.putExtra(Globals.RECIPE_ARRAY, allRecipes);
        getApplicationContext().sendBroadcast(listData);



    }
}

