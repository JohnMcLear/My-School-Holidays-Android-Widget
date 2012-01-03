
package com.primaryt.mshwidget;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;

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
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://mobile.myschoolholidays.com/"
                    + prefs.getSelectedSchoolURL()));
            startActivity(intent);
        }
        finish();
    }

}
