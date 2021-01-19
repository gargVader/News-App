package com.example.newsapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.newsapp.News;
import com.example.newsapp.NewsLoader;
import com.example.newsapp.NewsPreferences;
import com.example.newsapp.R;
import com.example.newsapp.SettingsActivity;
import com.example.newsapp.adapter.NewsAdapter;
import com.example.newsapp.utils.Constants;

import java.util.ArrayList;


/**
 * The BaseFragment is a {@link Fragment} subclass that implements the {@link LoaderManager.LoaderCallbacks}
 * interface in order for Fragment to be a client that interacts with the LoaderManager. It is the
 * base class that is responsible for displaying a set of articles, regardless of type
 */
public class BaseFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<ArrayList<News>> {

    /** Tag for log messages */
    private String LOG_TAG = BaseFragment.class.getSimpleName();

    /** Constant value for NEWS_LOADER_ID */
    private int NEWS_LOADER_ID = 1;

    /** Adapter to get View */
    NewsAdapter newsAdapter;

    /** Detects swipe gestures and triggers callbacks in the app */
    private SwipeRefreshLayout swipeRefreshLayout;

    /** Base Request Url to fetch data from api */
    String REQUEST_URL = Constants.REQUEST_URL;

    /**
     * Called to have the fragment instantiate its user interface view.
     * Its recommended to only inflate the layout in this method and move the logic to
     * onViewCreated
     * @param inflater : The LayoutInflator object that can be used to inflate any views in the fragment.
     * @param container : The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState
     * @return : View for the fragment's UI
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // View to be returned for display in fragment
        View rootView = inflater.inflate(R.layout.news_list_view, container, false);

        // Find reference to listView
        ListView listView = (ListView) rootView.findViewById(R.id.listView);

        // Create a new adaptor that takes an empty ArrayList<News> as input
        newsAdapter = new NewsAdapter(getActivity(), R.layout.list_item, new ArrayList<News>());
        // Associate listView with newsAdapter
        listView.setAdapter(newsAdapter);

        // Check for network connectivity and initialise loader
        initializeLoader(isConnected());

        // Find reference to SwipeRefreshLayout
        swipeRefreshLayout = rootView.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setRefreshing(true);

        // Setup onRefreshListener that is invoked when the user performs a swipe-to-refresh gesture
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e(LOG_TAG, "onRefreshListener invoked");
                // Create a new adaptor that takes an empty ArrayList<News> as input
                newsAdapter = new NewsAdapter(getActivity(), R.layout.list_item, new ArrayList<News>());
                // Associate listView with newsAdapter
                listView.setAdapter(newsAdapter);
                // restart the loader
                restartLoader(isConnected());
                Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    /** Checks for network connectivity */
    private boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean connected = activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
        return connected;
    }

    private void initializeLoader(boolean isConnected){
        if(isConnected){
            // Get a LoaderManager which interacts with activities to manage one or more
            // Loader Instance
            LoaderManager loaderManager = getLoaderManager();
            // Don't use
            //LoaderManager loaderManager = getActivity().getSupportLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this).forceLoad();

        }else{
            Log.e(LOG_TAG, "No internet");
            Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Creates and returns new loader instance
     * @param id
     * @param args
     * @return
     */
    @NonNull
    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, @Nullable Bundle args) {

        String section = null;
        NewsPreferences newsPreferences = new NewsPreferences(getContext(), REQUEST_URL, section);
        String preferredUrl = newsPreferences.getPreferredUrl();
        Log.e(LOG_TAG, "Url after appending "+preferredUrl);

        // Create a new loader for the given URL
        return new NewsLoader(getActivity(), preferredUrl);
    }

    /**
     * Loader has finished fetching the data. newsArrayList is filled. Pass newsArrayList to
     * newsAdapter and notifyDataSetChanged
     * @param loader
     * @param newsArrayList
     */
    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<News>> loader, ArrayList<News> newsArrayList) {
        if(newsArrayList==null || newsArrayList.isEmpty()) return;

        // Clear the adapter of previous news data
        if(newsAdapter !=null){
            newsAdapter.clear();
        }else return;

        if(newsArrayList!=null && !newsArrayList.isEmpty()) {
            newsAdapter.addAll(newsArrayList);
            newsAdapter.notifyDataSetChanged();
        }

        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * Resets the loader when not needed
     * @param loader
     */
    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<News>> loader) {
        newsAdapter.clear();
//        newsAdapter.notifyDataSetChanged();
    }

    private void restartLoader(boolean isConnected){
        if(isConnected){
            // Get a LoaderManager which interacts with activities to manage one or more
            // Loader Instance
            LoaderManager loaderManager = getLoaderManager();
            //LoaderManager loaderManager = getActivity().getSupportLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this).forceLoad();
        }else{
            Log.e(LOG_TAG, "No internet");
            Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setVisibility(View.GONE);
        }
    }
}
