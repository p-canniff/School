package com.example.philipcanniff.p_canniff_recipewidgets.Widget_One;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.philipcanniff.p_canniff_recipewidgets.DataFiles.APIDataManager;
import com.example.philipcanniff.p_canniff_recipewidgets.DataFiles.DataManager;
import com.example.philipcanniff.p_canniff_recipewidgets.DataFiles.RecipeObject;
import com.example.philipcanniff.p_canniff_recipewidgets.R;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by philipcanniff on 10/19/15.
 */
public class WidgetOneFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private ArrayList<RecipeObject> mRecipes;
    Bitmap bitmap;
    byte[] savedBytes;


    public WidgetOneFactory(Context context) {

        mContext = context;
        mRecipes = new ArrayList<>();

    }

    @Override
    public void onCreate() {

        mRecipes = APIDataManager.getInstance(mContext).getRecipes();
    }

    @Override
    public void onDataSetChanged() {

        mRecipes = APIDataManager.getInstance(mContext).getRecipes();

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {

        return mRecipes.size();

    }

    @Override
    public RemoteViews getViewAt(int position) {

        RecipeObject recipe = mRecipes.get(position);
        bitmap = BitmapFactory.decodeByteArray(recipe.getrIconURL(), 0 , recipe.getrIconURL().length);

        RemoteViews itemView = new RemoteViews(mContext.getPackageName(), R.layout.widget_one_item);
        itemView.setTextViewText(R.id.itemText, recipe.getrName());
        itemView.setImageViewBitmap(R.id.imageView, bitmap);

        bitmap = null;

        return itemView;
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
