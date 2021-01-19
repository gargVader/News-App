package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.newsapp.adapter.CategoryFragmentPageAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    /** Tag for log messages */
    String LOG_TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ViewPager (ViewGroup) will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        // To insert child views that represent each page, you need to hook viewPager to PagerAdapter
        CategoryFragmentPageAdapter categoryFragmentPageAdapter =
                new CategoryFragmentPageAdapter(this, getSupportFragmentManager());
        // set the page adapter to the view pager
        viewPager.setAdapter(categoryFragmentPageAdapter);

        // A TabLayout provides a way to display tabs horizontally
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Used to display options menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.settingsMenuItem){
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}