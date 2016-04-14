package com.example.philipcanniff.canniff_philip_longassignment;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements User_List_Fragment.sendToDetail, DetailFragment.DetailInterfacing, InputFragment.InputInterfacing {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button input = (Button)findViewById(R.id.inputUser);
        final LinearLayout image_layout = (LinearLayout) findViewById(R.id.image_layout);

        User_List_Fragment savedList =  User_List_Fragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.list_container, savedList, User_List_Fragment.TAG).commit();


        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                InputFragment inputPage = InputFragment.newInstance(false, null, 0);
                getFragmentManager().beginTransaction().replace(R.id.list_container, inputPage, InputFragment.TAG).commit();

                input.setVisibility(View.GONE);
                image_layout.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public void recallDetail(PersonClass _person, int _placement) {
        DetailFragment savedList =  DetailFragment.newInstance(_person, _placement);
        getFragmentManager().beginTransaction().replace(R.id.list_container, savedList, DetailFragment.TAG).commit();
    }
    @Override
    public void sendData(PersonClass _person, int _placement) {
        DetailFragment savedList =  DetailFragment.newInstance(_person, _placement);
        getFragmentManager().beginTransaction().replace(R.id.list_container, savedList, DetailFragment.TAG).commit();
    }
    @Override
    public void sendDataToInput(boolean editing, PersonClass _person, int _placement) {
        InputFragment updateForm = InputFragment.newInstance(editing, _person, _placement);
        getFragmentManager().beginTransaction().replace(R.id.list_container, updateForm, InputFragment.TAG).commit();
    }
    @Override
    public void recallListFromInput() {
        User_List_Fragment savedList =  User_List_Fragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.list_container, savedList, InputFragment.TAG).commit();
    }
    @Override
    public void recallList() {
        User_List_Fragment savedList =  User_List_Fragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.list_container, savedList, InputFragment.TAG).commit();
    }
}
