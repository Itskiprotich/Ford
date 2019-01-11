package com.fordperformanceclubofact.groupb.Terms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fordperformanceclubofact.groupb.R;

public class Conditions extends AppCompatActivity {


    private Toolbar toolbar;

    private WebView webView;

    String email,link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conditions);

        email = getIntent().getStringExtra("email_key");

        link = getIntent().getStringExtra("link_key");

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(email);

        webView = (WebView) findViewById(R.id.load);

        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(link);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
