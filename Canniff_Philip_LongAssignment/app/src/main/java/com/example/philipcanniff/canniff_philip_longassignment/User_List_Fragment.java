package com.example.philipcanniff.canniff_philip_longassignment;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

/**
 * Created by philipcanniff on 9/10/15.
 */
public class User_List_Fragment extends ListFragment {


    public static final String TAG = "UserListFragment.tag";


    public static User_List_Fragment newInstance(){

        User_List_Fragment frag = new User_List_Fragment();


        return frag;

    }

    sendToDetail detailView;

    public interface sendToDetail{
        public void sendData(PersonClass _person, int _placement);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof sendToDetail){

            detailView = (sendToDetail)activity;
        }

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<PersonClass> adapter = new ArrayAdapter<PersonClass>(getActivity(), android.R.layout.simple_list_item_1, DataManager.getInstance(getActivity()).getPersons());
        setListAdapter(adapter);

    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        getActivity().findViewById(R.id.inputUser).setVisibility(View.GONE);
        getActivity().findViewById(R.id.image_layout).setVisibility(View.GONE);

        PersonClass person = (PersonClass) getListAdapter().getItem(position);

        detailView.sendData(person, position);



    }




}
