package com.example.philipcanniff.canniff_philip_newsreader.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.philipcanniff.canniff_philip_newsreader.DataFiles.Article;
import com.example.philipcanniff.canniff_philip_newsreader.R;

/**
 * Created by philipcanniff on 9/22/15.
 */
public class Detail_Fragment extends Fragment{

    public static final String TAG = "DetailFragment.tag";
    public static final String ARTICLE = "Article";


    public static Detail_Fragment newInstance(Article _article){

        Detail_Fragment frag = new Detail_Fragment();

        Bundle args = new Bundle();
        args.putSerializable(ARTICLE, _article);
        frag.setArguments(args);

        return frag;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_layout, container, false);

        Article article = (Article) getArguments().getSerializable(ARTICLE);

        TextView title = (TextView) view.findViewById(R.id.titleView);
        TextView author = (TextView) view.findViewById(R.id.authorView);

        title.setText(article.getTitle());
        author.setText(article.getAuthor());


        return view;
    }
}
