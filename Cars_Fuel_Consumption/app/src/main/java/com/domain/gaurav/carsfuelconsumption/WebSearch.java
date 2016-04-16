package com.domain.gaurav.carsfuelconsumption;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

//Inspired from: https://developer.chrome.com/multidevice/webview/gettingstarted
//This class and it's activity uses url string and value passed in using intent to search for the images of the user
// selected car on web. Depending on the android version the website may load in browser on in the app.
public class WebSearch extends AppCompatActivity {

    private static String URL = "https://www.google.com/images?q="; //url used to get images from.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Inspired from: http://stackoverflow.com/questions/2198410/how-to-change-title-of-activity-in-android
        setTitle("Car Images");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_search);

        Intent intent = getIntent();
        String search = intent.getStringExtra("Search");

        //Inspired from: https://developer.chrome.com/multidevice/webview/gettingstarted
        WebView webView = (WebView)findViewById(R.id.webView);
        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);
        webView.loadUrl(URL + search);
    }
}
