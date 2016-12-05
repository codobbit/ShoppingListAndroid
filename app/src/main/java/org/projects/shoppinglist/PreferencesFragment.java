package org.projects.shoppinglist;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;


public class PreferencesFragment extends PreferenceFragment {

    //These values are specifed in the prefs.xml file
    //and needs to correspond exactly to those in the prefs.xml file
    private static String SETTINGS_NAMEKEY = "name";

    public static String getName(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(SETTINGS_NAMEKEY, "");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //adding the prefs
        addPreferencesFromResource(R.xml.prefs);
    }
}
