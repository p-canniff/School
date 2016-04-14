package com.example.philipcanniff.p_canniff_recipewidgets.DataFiles;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by philipcanniff on 10/24/15.
 */
public class APIDataManager {

    public static APIDataManager INSTANCE = null;
    Context context;

    ArrayList<RecipeObject> saveArray;

    private APIDataManager(Context c){

        this.context = c;
        saveArray = new ArrayList<RecipeObject>();

    }

    public static APIDataManager getInstance(Context c){

        if (INSTANCE == null){

            INSTANCE = new APIDataManager(c);

        }

        return INSTANCE;

    }

    public void updateRecipe(RecipeObject _Recipe, int _placement){

        saveArray = getRecipes();
        saveArray.remove(_placement);
        saveArray.add(_placement, _Recipe);
        saveRecipeArray(saveArray);


    }
    public void appendRecipe(RecipeObject _Recipe){

        saveArray = getRecipes();
        RecipeObject Recipe = _Recipe;
        saveArray.add(_Recipe);
        saveRecipeArray(saveArray);


    }
    public void saveRecipeArray(ArrayList<RecipeObject> _Recipe){

        String filename = "recipeData.bin";
        FileOutputStream fos;

        try {

            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(saveArray);

            Log.i("Saved", "Successfully Saved to Storage");


        } catch(Exception e ){

            Log.i("Failed to Save", "Failed Saving to Storage");
            e.printStackTrace();

        }


    }
    public void deleteRecipes(int _placement){

        saveArray = getRecipes();

        saveArray.remove(_placement);

        saveRecipeArray(saveArray);

    }

    public ArrayList<RecipeObject> getRecipes(){

        ArrayList<RecipeObject> storedArray;

        String filename = "recipeData.bin";

        try {

            FileInputStream inputStream = context.openFileInput(filename);
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            saveArray = (ArrayList<RecipeObject>) ois.readObject();

            Log.i("Loaded Recipes", "Recipes Loaded Successfully");
            ois.close();

            return saveArray;



        } catch(Exception e ) {

            saveArray = new ArrayList<>();


            Log.i("Failed to Load", "Failed Load Recipe Array");
            e.printStackTrace();

            return saveArray;


        }


    }

}
