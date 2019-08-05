package com.sweethome.jimmy.mynews.Controllers.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.sweethome.jimmy.mynews.Controllers.Fragments.PageSearchArticlesFragment;
import com.sweethome.jimmy.mynews.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        configureToolbar();

        Bundle bundle = new Bundle();
        bundle.putString("ARTICLES_SEARCH", getIntent().getStringExtra("ARTICLES_SEARCH"));
        PageSearchArticlesFragment pageSearchArticlesFragment = new PageSearchArticlesFragment();
        pageSearchArticlesFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
            .add(R.id.activity_search_frameLayout, pageSearchArticlesFragment)
            .addToBackStack(null)
            .commit();
    }

    private void configureToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }
}
