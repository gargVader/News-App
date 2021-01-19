package com.example.newsapp.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.newsapp.R;
import com.example.newsapp.fragment.ArtanddesignFragment;
import com.example.newsapp.fragment.BaseFragment;
import com.example.newsapp.fragment.BusinessFragment;
import com.example.newsapp.fragment.CultureFragment;
import com.example.newsapp.fragment.EducationFragment;
import com.example.newsapp.fragment.EnvironmentFragment;
import com.example.newsapp.fragment.FashionFragment;
import com.example.newsapp.fragment.FilmFragment;
import com.example.newsapp.fragment.HomeFragment;
import com.example.newsapp.fragment.LifeandstyleFragment;
import com.example.newsapp.fragment.MusicFragment;
import com.example.newsapp.fragment.PoliticsFragment;
import com.example.newsapp.fragment.ScienceFragment;
import com.example.newsapp.fragment.SocietyFragment;
import com.example.newsapp.fragment.SportFragment;
import com.example.newsapp.fragment.TechnologyFragment;
import com.example.newsapp.fragment.TravelFragment;
import com.example.newsapp.fragment.WorldFragment;
import com.example.newsapp.utils.Constants;

/**
 * Generates {@link Fragment} to be displayed in a view pager
 */
public class CategoryFragmentPageAdapter extends FragmentPagerAdapter {

    /** Context of app */
    private Context context;

    /** Tag for log messages */
    private String LOG_TAG = CategoryFragmentPageAdapter.class.getSimpleName();

    /**
     * Constructor for {@link FragmentPagerAdapter}
     * @param context is the context of the app
     * @param fm is the FragmentManager that will keep each fragment's state in the adapter
     *           across swipes
     */
    public CategoryFragmentPageAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    /**
     * Returns Fragment object associated with the given tab position
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch(position){
            case Constants.WORLD: return new WorldFragment();
            case Constants.POLITICS: return new PoliticsFragment();
            case Constants.BUSINESS: return new BusinessFragment();
            case Constants.SPORT: return new SportFragment();
            case Constants.TECHNOLOGY: return new TechnologyFragment();
            case Constants.SCIENCE: return new ScienceFragment();
            case Constants.FILM: return new FilmFragment();
            case Constants.MUSIC: return new MusicFragment();
            case Constants.FASHION: return new FashionFragment();
            case Constants.TRAVEL: return new TravelFragment();
            case Constants.EDUCATION: return new EducationFragment();
            case Constants.SOCIETY: return new SocietyFragment();
            case Constants.CULTURE: return new CultureFragment();
            case Constants.ENVIRONMENT: return new EnvironmentFragment();
            case Constants.LIFEANDSTYLE: return new LifeandstyleFragment();
            case Constants.ARTANDDESIGN: return new ArtanddesignFragment();
            default: return new HomeFragment();
        }
    }

    /**
     * Returns the number of views available
     * @return
     */
    @Override
    public int getCount() {
        return 17;
    }

    /**
     * This method may be called by the ViewPager to obtain a title string to describe the
     * specified page.
     * @param position
     * @return
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){

            case Constants.WORLD: return context.getString(R.string.WORLD);
            case Constants.POLITICS: return context.getString(R.string.POLITICS);
            case Constants.BUSINESS: return context.getString(R.string.BUSINESS);
            case Constants.SPORT: return context.getString(R.string.SPORT);
            case Constants.TECHNOLOGY: return context.getString(R.string.TECHNOLOGY);
            case Constants.SCIENCE: return context.getString(R.string.SCIENCE);
            case Constants.FILM: return context.getString(R.string.FILM);
            case Constants.MUSIC: return context.getString(R.string.MUSIC);
            case Constants.FASHION: return context.getString(R.string.FASHION);
            case Constants.TRAVEL: return context.getString(R.string.TRAVEL);
            case Constants.EDUCATION: return context.getString(R.string.EDUCATION);
            case Constants.SOCIETY: return context.getString(R.string.SOCIETY);
            case Constants.CULTURE: return context.getString(R.string.CULTURE);
            case Constants.ENVIRONMENT: return context.getString(R.string.ENVIRONMENT);
            case Constants.LIFEANDSTYLE: return context.getString(R.string.LIFEANDSTYLE);
            case Constants.ARTANDDESIGN: return context.getString(R.string.ARTANDDESIGN);
            default: return context.getString(R.string.HOME);

        }
    }
}
