package com.example.newsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

public class NewsPreferences {

   String section;
   String pageSize;
   Context context;
   String REQUEST_URL;

   public NewsPreferences(Context context, String REQUEST_URL, String section){
       this.section = section;
       this.context = context;
       this.REQUEST_URL = REQUEST_URL;
   }

   public String getPreferredUrl(){

       // Fetching page-size from shared preferences
       SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
       pageSize = sharedPreferences.getString(
               getString(R.string.QUERY_PARAMETER_PAGE_SIZE_KEY),
               getString(R.string.QUERY_PARAMETER_PAGE_SIZE_VALUE)
       );

       return getPreferredUrlHelper(REQUEST_URL, section);
   }

    private String getPreferredUrlHelper(String REQUEST_URL, String section){
        Uri baseUri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter(getString(R.string.QUERY_PARAMETER_FORMAT_KEY), getString(R.string.QUERY_PARAMETER_FORMAT_VALUE));
        uriBuilder.appendQueryParameter(getString(R.string.QUERY_PARAMETER_SHOW_TAGS_KEY), getString(R.string.QUERY_PARAMETER_SHOW_TAGS_VALUE));
        uriBuilder.appendQueryParameter(getString(R.string.QUERY_PARAMETER_SHOW_FIELDS_KEY), getString(R.string.QUERY_PARAMETER_SHOW_FIELDS_VALUE));
        uriBuilder.appendQueryParameter(getString(R.string.QUERY_PARAMETER_API_KEY_KEY), getString(R.string.QUERY_PARAMETER_API_KEY_VALUE));
        uriBuilder.appendQueryParameter(getString(R.string.QUERY_PARAMETER_PAGE_SIZE_KEY), pageSize);
        if(section!=null) {
            uriBuilder.appendQueryParameter(getString(R.string.QUERY_PARAMETER_SECTION_KEY), section);
        }
        return uriBuilder.toString();
    }

    private String getString(int id) {
       return context.getString(id);
    }

}
