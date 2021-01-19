package com.example.newsapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.newsapp.utils.QueryUtils;

import java.util.ArrayList;
import java.util.List;

public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {

    /** Query URL */
    private String url;

    /** Tag for log messages */
    private static  final String LOG_TAG = NewsLoader.class.getSimpleName();

    /**
     *
     * @param context of the activity
     * @param url
     */
    public NewsLoader(@NonNull Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    /**
     * This is background thread
     * @return
     */
    @Nullable
    @Override
    public ArrayList<News> loadInBackground() {

        // If url is null or empty return null
        if(url==null || url.isEmpty()) return null;

        ArrayList<News> newsArrayList = QueryUtils.extractNews(url);
        return newsArrayList;
    }
}
