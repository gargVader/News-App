package com.example.newsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.newsapp.News;
import com.example.newsapp.R;
import com.example.newsapp.utils.AdapterUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class NewsAdapter extends ArrayAdapter<News> {

    /** Tag for log messages */
    String LOG_TAG = NewsAdapter.class.getSimpleName();
    /** Context of activity */
    private static Context context;
    /** list of news which is the data source of the adapter */
    ArrayList<News> newsArrayList;

    /**
     * Constructs a new {@link NewsAdapter}
     * @param context of the app
     * @param resource R.layout.______ to be used
     * @param newsArrayList list of news which is the data source of the adapter
     */
    public NewsAdapter(@NonNull Context context, int resource, ArrayList<News> newsArrayList) {
        super(context, resource, newsArrayList);
        this.context = context;
        this.newsArrayList = newsArrayList;
    }

    /**
     * Returns view for a particular news item in newsArrayList
     * @param position : Position of pointer in newsArrayList
     * @param convertView : Recycled view
     * @param parent : Parent ViewGroup
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        // Check if existing view is being reused, otherwise inflate the view
        if(listItem==null){
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Get the news item from data source
        News news = getItem(position);
        // Find all views
        TextView title = (TextView) listItem.findViewById(R.id.title);
        TextView trailText = (TextView) listItem.findViewById(R.id.trailText);
        TextView section = (TextView) listItem.findViewById(R.id.section);
        TextView author = (TextView) listItem.findViewById(R.id.author);
        TextView time = (TextView) listItem.findViewById(R.id.time);
        TextView date = (TextView) listItem.findViewById(R.id.date);
        ImageView thumbnail = (ImageView) listItem.findViewById(R.id.thumbnail);
        ImageView shareButton = (ImageView) listItem.findViewById(R.id.shareButton);
        ProgressBar progressBar = (ProgressBar) listItem.findViewById(R.id.progress_load_photo);

        // Set data
        title.setText(news.getTitle());
        trailText.setText(Html.fromHtml(Html.fromHtml(news.getTrailText()).toString()));
        section.setText(news.getSection());
        author.setText(news.getAuthor());
        time.setText(" •  "+ formatDateToTime(news.getDate()));
        date.setText(formatDateToDate(news.getDate()));
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent messageIntent = new Intent(Intent.ACTION_SEND);
                messageIntent.setType("text/plain");
                String message = news.getTitle()+"\n"+news.getWebUrl();
                messageIntent.putExtra(Intent.EXTRA_TEXT, message);
                getContext().startActivity(messageIntent);
            }
        });

        // Setting up Glide
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(AdapterUtils.getRandomDrawbleColor());
        requestOptions.error(AdapterUtils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context.getApplicationContext())
                .load(news.getImageUrl())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(thumbnail);

        // Set onClickListener to open a website with more information about the selected article
        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(news.getWebUrl());

                Intent webIntent = new Intent(Intent.ACTION_VIEW, uri);

                context.startActivity(webIntent);
            }
        });


        return listItem;
    }

    /**
     * Date is given to us in format yyyy-MM-dd'T'kk:mm:ss'Z' (eg: 2021-01-19T07:44:16Z)
     * This has to be subtracted from current date and difference found.
     * So both currentTime and givenTime is converted to millis
     * */
    private String formatDateToTime(String dateStringUTC){
        // Parse the dateString into a date object
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date dateObject = null;
        try {
            dateObject = simpleDateFormat.parse(dateStringUTC);
            long dateInMillis = dateObject.getTime();
            long currentTime = System.currentTimeMillis();
            Log.e(LOG_TAG, "Publication Time: "+dateInMillis);
            return (String) DateUtils.getRelativeTimeSpanString(dateInMillis, currentTime, DateUtils.SECOND_IN_MILLIS);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /** Date is given to us in format yyyy-MM-dd'T'kk:mm:ss'Z' (eg: 2021-01-19T07:44:16Z)
     * This has to converted to Tue, 19 Jan 2021.
     * So first make a date object from given string. Then change date object to another format
     * */
    private String formatDateToDate(String dateStringUTC){
        // Parse the dataString into a data object
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date dateObject = null;
        try{
            dateObject = simpleDateFormat.parse(dateStringUTC);

            String date = new SimpleDateFormat("EEE, d MMM yyyy").format(dateObject);
            return date;

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }



}
