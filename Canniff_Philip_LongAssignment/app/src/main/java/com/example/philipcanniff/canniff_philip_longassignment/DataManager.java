package com.example.philipcanniff.canniff_philip_longassignment;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by philipcanniff on 9/20/15.
 */


public class DataManager  {

    public static DataManager INSTANCE = null;
    Context context;

    ArrayList<PersonClass> saveArray;

    private DataManager(Context c){

        this.context = c;
        saveArray = new ArrayList<PersonClass>();

    }

    public static DataManager getInstance(Context c){

        if (INSTANCE == null){

            INSTANCE = new DataManager(c);

        }

        return INSTANCE;

    }

    public void updatePerson(PersonClass _person, int _placement){

        saveArray = getPersons();
        saveArray.remove(_placement);
        saveArray.add(_placement, _person);
        savePersonArray(saveArray);


    }
    public void appendPerson(PersonClass _person){

        saveArray = getPersons();

        PersonClass person = _person;
        saveArray.add(_person);
        savePersonArray(saveArray);


    }
    public void savePersonArray(ArrayList<PersonClass> _person){

        String filename = "PersonData.bin";
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
    public void deletePersons(int _placement){

        saveArray = getPersons();

        saveArray.remove(_placement);

        savePersonArray(saveArray);

    }

    public ArrayList<PersonClass> getPersons(){

        ArrayList<PersonClass> storedArray;

        String filename = "PersonData.bin";

        try {

            FileInputStream inputStream = context.openFileInput(filename);
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            saveArray = (ArrayList<PersonClass>) ois.readObject();

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
