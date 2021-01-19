package com.example.newsapp;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * SettingsActivity(activity_settings) is shown when user clicks on Settings menu item
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }


    /**
     * NewsPreferenceFragment implements the Preference.OnPreferenceChangeListener interface
     * for a callback to be invoked when the value of this Preference has been changed by the user.
     * Basically listens to changes made by user.
     */
    public static class NewsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        Preference numOfItems;

        /** Inflates the given XML resource and adds the preference hierarchy to
         * the current preference hierarchy
         * */
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            numOfItems = findPreference(getString(R.string.settings_num_of_items_key));
            bindPreferenceSummaryToValue(numOfItems);
        }

        /** Called when a preference has been changed by the user. This is called before the state
         * of the Preference is about to be updated and before the state is persisted.
         *
         * */

        /**
         * Called when a preference has been changed by the user. This is called before the state
         * of the Preference is about to be updated and before the state is persisted.
         * @param preference The changed preference
         * @param newValue the newValue of the Preference
         * @return
         */
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();
            preference.setSummary(stringValue);
            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference){
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferencesString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferencesString);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }

}
