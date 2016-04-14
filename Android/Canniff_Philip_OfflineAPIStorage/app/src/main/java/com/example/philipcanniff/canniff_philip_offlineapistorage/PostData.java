package com.example.philipcanniff.canniff_philip_offlineapistorage;

import java.io.Serializable;

/**
 * Created by philipcanniff on 8/31/15.
 */
public class PostData implements Serializable{


    String articleTitle;
    String count;

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleTitle() {

        return articleTitle;
    }

    public PostData(String articleTitle, String count) {
        this.articleTitle = articleTitle;
        this.count = count;
    }

    public String getCount() {

        return count;
    }
}
