package com.example.jason.newsportal.WebView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.jason.newsportal.R;

public class NewViewWebsite extends AppCompatActivity {

    private WebView mWebView;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_view_website);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("NewsActivity");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Bundle extras = getIntent().getExtras();
        String sourceURL = extras.getString("newsURL");

        mWebView = (WebView) findViewById(R.id.newSite);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                mProgressBar.setVisibility(view.VISIBLE);
                mWebView.setVisibility(view.GONE);
                int color = 0xFF4CAF50;
                mProgressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                mProgressBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onPageFinished(WebView view, String url) {

//                int progress = ((int) mProgressBar.getProgress());
//                mProgressBar.setProgress(progress);
                mProgressBar.setVisibility(view.GONE);
                mWebView.setVisibility(view.VISIBLE);


            }
        });

        System.out.println("Website" +sourceURL);
        mWebView.loadUrl(sourceURL);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.webcontent, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_close:
                finish();

                return true;

            case android.R.id.home:
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
