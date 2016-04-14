package com.example.philipcanniff.canniff_philip_newsreader.DataFiles;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by philipcanniff on 9/22/15.
 */
public class DataManager {

    public static DataManager INSTANCE = null;
    private static final String FILENAME = "Articles.bin";
    Context context;
    ArrayList<Article> saveArray;


    private DataManager(Context c){

        this.context = c;
        saveArray = new ArrayList<Article>();

    }
    public static DataManager getInstance(Context c){

        if (INSTANCE == null){

            INSTANCE = new DataManager(c);

        }

        return INSTANCE;

    }
    public void appendPerson(Article _article){

        saveArray = getFavorites();

        Article person = _article;
        saveArray.add(_article);
        saveFavoriteArray();


    }
    public void saveFavoriteArray(){

        String filename = FILENAME;
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

    public void deleteFavorite(int _placement){

        saveArray = getFavorites();

        saveArray.remove(_placement);

        saveFavoriteArray();

    }
    public ArrayList<Article> getFavorites(){

        ArrayList<Article> storedArray;

        String filename = FILENAME;

        try {

            FileInputStream inputStream = context.openFileInput(filename);
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            saveArray = (ArrayList<Article>) ois.readObject();

            Log.i("Loaded Persons", "Persons Loaded Successfully");
            ois.close();

            return saveArray;



        } catch(Exception e ) {

            saveArray = new ArrayList<>();


            Log.i("Failed to Load", "Failed Load Person Array");
            e.printStackTrace();

            return saveArray;


        }



    }


}
