package com.example.philipcanniff.canniff_philip_newsreader.DataFiles;

import java.io.Serializable;

/**
 * Created by philipcanniff on 9/22/15.
 */
public class Article implements Serializable {

    public String title;
    public String author;
    public String url;

    public Article(String title, String author, String url) {
        this.title = title;
        this.author = author;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return title;
    }
}
