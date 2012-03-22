
package com.primaryt.mshwidget;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Window;
import android.widget.Toast;

import com.primaryt.mshwidget.bo.School;
import com.primaryt.mshwidget.threads.SchoolFetchThread;
import com.primaryt.mshwidget.utils.Preferences;

public class LaunchingActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.launcher_screen);

        initializeUIElements();
    }

    private void initializeUIElements() {

        Preferences prefs = new Preferences(this);
        if (prefs.getSelectedSchoolID() == -1) {
            Intent intent = new Intent(this, HolidayAppActivity.class);
            intent.putExtra("ignore", false);
            startActivity(intent);
        } else {
            if (prefs.getSelectedSchoolCountry() != null
                    && !TextUtils.isEmpty(prefs.getSelectedSchoolCountry())) {
                navigateToWebsite();
                finish();
            } else {
                determineSchoolCountry();
            }
        }
    }

    private void navigateToWebsite() {
        Preferences prefs = new Preferences(getApplicationContext());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String country = prefs.getSelectedSchoolCountry();
        String url = "http://" + country + ".myschoolholidays.com/" + prefs.getSelectedSchoolURL();
        intent.setData(Uri.parse(url));
        startActivity(intent);
        finish();
    }

    private void determineSchoolCountry() {
        Preferences prefs = new Preferences(getApplicationContext());
        String schoolName = prefs.getSchoolName();
        if (schoolName.contains("(")) {
            int index = schoolName.indexOf("(");
            schoolName = schoolName.substring(0, index).trim();
        }

        SchoolFetchThread thread = new SchoolFetchThread(this, handler, schoolName);
        thread.start();
    }

    private void updateSchoolList(ArrayList<School> schools) {
        if (schools != null && schools.size() == 1) {
            School school = schools.get(0);
            Preferences prefs = new Preferences(getApplicationContext());
            prefs.updateSelectedSchool(school);
            navigateToWebsite();
        }
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.getData() != null) {
                if (msg.getData().containsKey("value")) {
                    @SuppressWarnings("unchecked")
                    ArrayList<School> schoolList = (ArrayList<School>) msg.getData()
                            .getSerializable("value");
                    updateSchoolList(schoolList);
                } else if (msg.getData().containsKey("exception")) {
                    showErrorMessage(msg.getData().get("exception").toString(), msg.getData()
                            .getString("query"));
                }
            }
        }

    };

    private void showErrorMessage(String message, String query) {
        Toast.makeText(this, getString(R.string.lab_country_fetch_error), Toast.LENGTH_LONG).show();
    }
}
