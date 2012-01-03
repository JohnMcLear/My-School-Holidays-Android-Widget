
package com.primaryt.mshwidget.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.primaryt.mshwidget.bo.School;

public class Preferences {
    private final static String PREFS_FILE = "preferences";

    private final static String SCHOOL_ID = "school_id";

    private final static String SCHOOL_NAME = "school_name";

    private SharedPreferences preferences;

    /**
     * Constructor, need to pass the application context to build/update the
     * SharedPreferences
     * 
     * @param context
     */
    public Preferences(Context context) {
        preferences = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
    }

    public void updateSelectedSchool(School school) {
        Editor edit = preferences.edit();
        edit.putString(SCHOOL_NAME, school.getSchoolLabel());
        edit.putLong(SCHOOL_ID, school.getSchoolID());
        edit.commit();

    }

    public long getSelectedSchoolID() {
        return preferences.getLong(SCHOOL_ID, -1);
    }

    public String getSelectedSchoolURL() {
        String url = preferences.getString(SCHOOL_NAME, "");
        url = url.substring(0, url.indexOf("(") - 1);
        url = url + " " + getSelectedSchoolID();
        return url.replaceAll(" ", "-");
    }
}
