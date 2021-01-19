package com.example.newsapp.utils;

import android.util.Log;

import androidx.loader.app.LoaderManager;

import com.example.newsapp.News;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Helper class to fetch data from REQUEST_URL
 */
public class QueryUtils {

    /** Tag for log messages */
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils(){

    }

    /**
     * Extracts news from API and stores them in newsArrayList as {@link News} objects
     * @param stringUrl Url to fetch data from
     * @return ArrayList of news objects
     */
    public static ArrayList<News> extractNews(String stringUrl){

        // Create URL Object
        URL url = createUrl(stringUrl);
        String jsonResponse = null;
        // Perform HTTP request to get jsonResponse back
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in making HTTP requests ", e);
        }

        // Extract info from jsonResponse
        ArrayList<News> newsArrayList = extractFeaturesFromJson(jsonResponse);

        return newsArrayList;
    }

    /**
     * Parses JSON string to create {@link News} objects and store in newsArrayList
     * @param newsJson JSON string
     * @return newsArrayList
     */
    private static ArrayList<News> extractFeaturesFromJson(String newsJson){

        if(newsJson==null || newsJson.isEmpty()) return null;

        // Create an empty ArrayList
        ArrayList<News> newsArrayList = new ArrayList<>();

        try{

            JSONObject root = new JSONObject(newsJson);
            JSONObject responseJson = root.getJSONObject("response");

            Integer pageSize = responseJson.getInt("pageSize");
            JSONArray resultsArray = responseJson.getJSONArray("results");

            for(int i=0; i<resultsArray.length(); i++){
                JSONObject newsObject = (JSONObject) resultsArray.get(i);

                String section = newsObject.getString("sectionName");
                String date = newsObject.getString("webPublicationDate");

                JSONObject fieldsObject = newsObject.getJSONObject("fields");
                String title = fieldsObject.getString("headline");
                String trailText = fieldsObject.getString("trailText");
                String imageUrl = fieldsObject.getString("thumbnail");
                String webUrl = fieldsObject.getString("shortUrl");

                JSONArray tagsArray = newsObject.getJSONArray("tags");

                // Handles the case when no contributors
                String author="";
                if(tagsArray.length()>0) {
                    JSONObject contributor1 = (JSONObject) tagsArray.get(0);
                    author = contributor1.getString("webTitle");
                }

                newsArrayList.add(new News(title, trailText, section, author, date, imageUrl, webUrl));
            }
        }catch (Exception e){
            Log.e(LOG_TAG, "Problem in parsing news JSON results", e);
        }

        return newsArrayList;
    }

    /**
     * Converts String URL to URL object
     * @param stringUrl
     * @return
     */
    private static URL createUrl(String stringUrl){

        if(stringUrl==null || stringUrl.isEmpty()) return null;

        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error while creating URL ", e);
        }

        return  url;
    }

    /**
     * Establishes HTTP connection and stores fetched data in form of string
     * @param url
     * @return jsonResponse
     * @throws IOException
     */
    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        // If url is null return early
        if(url==null) return jsonResponse;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            // If the request was successful, response code==200
            if(httpURLConnection.getResponseCode()==200){
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e(LOG_TAG, "Error Response Code: "+httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem is receiving news JSON data ", e);
        }finally {
            if(httpURLConnection!=null){
                httpURLConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }
        
        return jsonResponse;
    }

    /**
     * Reads data from given {@link InputStream } using {@link InputStreamReader} and
     * {@link BufferedReader}
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line!=null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}
