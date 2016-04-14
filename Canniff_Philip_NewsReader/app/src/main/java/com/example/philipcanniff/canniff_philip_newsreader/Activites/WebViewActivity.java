package com.example.philipcanniff.canniff_philip_newsreader.Activites;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.philipcanniff.canniff_philip_newsreader.R;

/**
 * Created by philipcanniff on 9/23/15.
 */
public class WebViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        WebView wv = (WebView)findViewById(R.id.webView);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl(this.getIntent().getDataString());



    }

}
