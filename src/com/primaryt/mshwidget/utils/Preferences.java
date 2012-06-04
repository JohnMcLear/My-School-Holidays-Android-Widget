
package com.primaryt.mshwidget.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.primaryt.mshwidget.bo.School;

public class Preferences {
    private final static String PREFS_FILE = "preferences";

    private final static String SCHOOL_ID = "school_id";

    private final static String SCHOOL_NAME = "school_name";

    private final static String SCHOOL_COUNTRY = "school_country";

    private final static String USE_COUNT = "use_count";

    private final static int PROMPT_FEEDBACK_AFTER_USE_COUNT = 5;

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
        edit.putString(SCHOOL_COUNTRY, school.getCountry());
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

    public String getSelectedSchoolCountry() {
        return preferences.getString(SCHOOL_COUNTRY, "");
    }

    public String getSchoolName() {
        return preferences.getString(SCHOOL_NAME, "");
    }

    private void appUseCountUp() {
        Editor edit = preferences.edit();
        edit.putInt(USE_COUNT, getAppUseCount() + 1);
        edit.commit();
    }

    private void resetCount() {
        Editor edit = preferences.edit();
        edit.putInt(USE_COUNT, 0);
        edit.commit();
    }

    private int getAppUseCount() {
        int count = 0;
        count = preferences.getInt(USE_COUNT, 0);
        if (count == PROMPT_FEEDBACK_AFTER_USE_COUNT) {
            resetCount();
        } else {
            appUseCountUp();
        }
        return count;
    }

    public boolean shouldPromptForFeedback() {
        boolean value = false;

        if (getAppUseCount() == PROMPT_FEEDBACK_AFTER_USE_COUNT) {
            value = true;
        }
        return value;
    }
}
