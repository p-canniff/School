package com.example.philipcanniff.canniff_philip_newsreader.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.philipcanniff.canniff_philip_newsreader.DataFiles.Article;
import com.example.philipcanniff.canniff_philip_newsreader.DataFiles.DataManager;
import com.example.philipcanniff.canniff_philip_newsreader.R;

import java.util.ArrayList;

/**
 * Created by philipcanniff on 9/22/15.
 */
public class News_List_Fragment extends ListFragment {

    public static final String TAG = "NewsListFragment.tag";
    public static final String ARTICLES = "Articles";

    public static News_List_Fragment newInstance(ArrayList<Article> _articles){

        News_List_Fragment frag = new News_List_Fragment();

        Bundle args = new Bundle();
        args.putSerializable(ARTICLES, _articles);
        frag.setArguments(args);

        return frag;

    }
    sendToDetail detailView;

    public interface sendToDetail{
        public void sendData(Article _article);
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

        ArrayList<Article> articleArray = (ArrayList<Article>) getArguments().getSerializable(ARTICLES);

        ArrayAdapter<Article> adapter = new ArrayAdapter<Article>(getActivity(), android.R.layout.simple_list_item_1, articleArray);

        setListAdapter(adapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Article article = (Article) getListAdapter().getItem(position);

        detailView.sendData(article);


    }
}
