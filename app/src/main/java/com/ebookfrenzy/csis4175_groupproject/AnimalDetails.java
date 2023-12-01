package com.ebookfrenzy.csis4175_groupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AnimalDetails extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_details);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("animal")) {
            String animalUrlFormatted = intent.getStringExtra("animal").replace(" ", "-");
            Toolbar toolbar = findViewById(R.id.webViewToolbar);
            setSupportActionBar(toolbar);

            // Set the title in the ActionBar
            getSupportActionBar().setTitle("Animal Details");

            // Enable the back button in the ActionBar
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            webView = findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.setWebViewClient(new WebViewClient());

            if (animalUrlFormatted.equals("Black-Bear")) {
                animalUrlFormatted = "american-black-bear";
            }
            webView.loadUrl("https://animalia.bio/" + animalUrlFormatted);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}