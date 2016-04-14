package com.example.philipcanniff.p_canniff_recipewidgets.Fragments;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.philipcanniff.p_canniff_recipewidgets.DataFiles.Globals;
import com.example.philipcanniff.p_canniff_recipewidgets.DataFiles.RecipeObject;
import com.example.philipcanniff.p_canniff_recipewidgets.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by philipcanniff on 10/19/15.
 */
public class Recipe_Detail_Fragment extends Fragment {

    public static final String TAG = "DetailFragment.TAG";

    public static Recipe_Detail_Fragment newInstance(){

        Recipe_Detail_Fragment fragment = new Recipe_Detail_Fragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment, container, false);

        RecipeObject selectedRecipe = (RecipeObject) getActivity().getIntent().getSerializableExtra(Globals.SINGLE_RECIPE);

        TextView rName = (TextView) view.findViewById(R.id.recipeName);
        TextView rIngredients = (TextView) view.findViewById(R.id.ingredients);
        ImageView rImage = (ImageView) view.findViewById(R.id.recipeImage);

        rName.setText(selectedRecipe.getrName());
        rIngredients.setText(selectedRecipe.getrIngredients() + "");
        rImage.setImageBitmap(BitmapFactory.decodeByteArray(selectedRecipe.getrIconURL(), 0, selectedRecipe.getrIconURL().length));

        return view;
    }
}
