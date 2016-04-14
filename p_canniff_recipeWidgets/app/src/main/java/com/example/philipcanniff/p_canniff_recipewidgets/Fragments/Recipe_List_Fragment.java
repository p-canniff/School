package com.example.philipcanniff.p_canniff_recipewidgets.Fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.philipcanniff.p_canniff_recipewidgets.Activites.DetailActivity;
import com.example.philipcanniff.p_canniff_recipewidgets.DataFiles.Globals;
import com.example.philipcanniff.p_canniff_recipewidgets.DataFiles.RecipeObject;

import java.util.ArrayList;

/**
 * Created by philipcanniff on 10/19/15.
 */
public class Recipe_List_Fragment extends ListFragment {

    public static final String TAG = "ListFragment.TAG";


    public static Recipe_List_Fragment newInstance(ArrayList<RecipeObject> recipes){

        Bundle args = new Bundle();
        args.putSerializable("Recipes", recipes);
        Recipe_List_Fragment fragment = new Recipe_List_Fragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<RecipeObject> recipeObjects = (ArrayList<RecipeObject>) getArguments().getSerializable("Recipes");

        ArrayAdapter<RecipeObject> adapter = new ArrayAdapter<RecipeObject>(getActivity(), android.R.layout.simple_list_item_1, recipeObjects);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ArrayList<RecipeObject> recipeObjects = (ArrayList<RecipeObject>) getArguments().getSerializable("Recipes");

        Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
        detailIntent.putExtra(Globals.SINGLE_RECIPE, recipeObjects.get(position));
        getActivity().startActivity(detailIntent);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
