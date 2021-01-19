package com.example.newsapp.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.Loader;

import com.example.newsapp.News;
import com.example.newsapp.NewsLoader;
import com.example.newsapp.NewsPreferences;
import com.example.newsapp.R;

import java.util.ArrayList;

public class SocietyFragment extends BaseFragment{

    String LOG_TAG = SocietyFragment.class.getSimpleName();

    @NonNull
    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, @Nullable Bundle args) {

        String section = getString(R.string.SOCIETY);
        NewsPreferences newsPreferences = new NewsPreferences(getContext(), REQUEST_URL, section);
        String preferredUrl = newsPreferences.getPreferredUrl();
        Log.e(LOG_TAG, preferredUrl);

        // Create a new loader for the given URL
        return new NewsLoader(getActivity(), preferredUrl);
    }

}
