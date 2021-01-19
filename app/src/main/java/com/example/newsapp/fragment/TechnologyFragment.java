package com.example.newsapp.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.Loader;

import com.example.newsapp.News;
import com.example.newsapp.NewsLoader;
import com.example.newsapp.NewsPreferences;
import com.example.newsapp.R;

import java.util.ArrayList;

public class TechnologyFragment extends BaseFragment {

    String LOG_TAG = TechnologyFragment.class.getSimpleName();

    @NonNull
    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, @Nullable Bundle args) {

        String section = getString(R.string.TECHNOLOGY);
        NewsPreferences newsPreferences = new NewsPreferences(getContext(), REQUEST_URL, section);
        String preferredUrl = newsPreferences.getPreferredUrl();
        Log.e(LOG_TAG, preferredUrl);

        // Create a new loader for the given URL
        return new NewsLoader(getActivity(), preferredUrl);
    }
}
