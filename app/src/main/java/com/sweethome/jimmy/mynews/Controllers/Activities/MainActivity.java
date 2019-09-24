package com.sweethome.jimmy.mynews.Controllers.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sweethome.jimmy.mynews.R;
import com.sweethome.jimmy.mynews.Views.PageAdapter;

public class MainActivity extends AppCompatActivity {

    public PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureToolbar();
        this.configureViewPagerAndTabs();
    }

    // View pager
    private void configureViewPagerAndTabs() {
        ViewPager pager = findViewById(R.id.activity_main_viewpager);
        pagerAdapter = new PageAdapter(getSupportFragmentManager(), this.getApplicationContext());
        pager.setAdapter(pagerAdapter);

        TabLayout tabs = findViewById(R.id.activity_main_tabs);
        tabs.setupWithViewPager(pager);
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }

    // Toolbar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // Menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_notification:
                Intent intentNotification = new Intent(MainActivity.this , SearchAndNotificationActivity.class);
                intentNotification.putExtra("title" , "Notification");
                this.startActivity(intentNotification);
                return true;
            case R.id.menu_help:
                Intent intentHelp = new Intent(MainActivity.this , HelpActivity.class);
                this.startActivity(intentHelp);
                return true;
            case R.id.menu_about:
                Intent intentAbout = new Intent(MainActivity.this , AboutActivity.class);
                this.startActivity(intentAbout);
                return true;
            case R.id.menu_activity_main_search:
                Intent intentSearch = new Intent(MainActivity.this , SearchAndNotificationActivity.class);
                intentSearch.putExtra("title" , "Search Articles");
                this.startActivity(intentSearch);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
