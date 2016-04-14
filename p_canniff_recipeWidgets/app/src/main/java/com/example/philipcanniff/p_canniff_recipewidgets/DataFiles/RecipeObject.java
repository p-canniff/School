package com.example.philipcanniff.p_canniff_recipewidgets.DataFiles;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by philipcanniff on 10/19/15.
 */
public class RecipeObject implements Serializable {

    String rName;
    byte[] rIconURL;
    ArrayList<String> rIngredients;

    public RecipeObject(String rName, byte[] rIconURL, ArrayList<String> rIngredients) {
        this.rName = rName;
        this.rIconURL = rIconURL;
        this.rIngredients = rIngredients;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public byte[] getrIconURL() {
        return rIconURL;
    }

    public void setrIconURL(byte[] rIconURL) {
        this.rIconURL = rIconURL;
    }

    public ArrayList<String> getrIngredients() {
        return rIngredients;
    }

    public void setrIngredients(ArrayList<String> rIngredients) {
        this.rIngredients = rIngredients;
    }

    @Override
    public String toString() {
        return rName;
    }
}
