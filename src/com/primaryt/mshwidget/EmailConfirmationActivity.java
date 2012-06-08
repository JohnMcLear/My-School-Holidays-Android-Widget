
package com.primaryt.mshwidget;

import com.primaryt.mshwidget.utils.Preferences;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class EmailConfirmationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_confirmation_activity);

        initializeUIElements();
    }

    private void initializeUIElements() {
        Button buttonNo = (Button) findViewById(R.id.buttonNo);
        buttonNo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                cancelSendFeedback();
            }
        });

        Button buttonYes = (Button) findViewById(R.id.buttonYes);
        buttonYes.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                sendFeedback();
            }
        });
    }

    private void cancelSendFeedback() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void sendFeedback() {
        try {
            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {
                "android@primaryt.co.uk"
            });
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "MSHW Android Feedback");
            String emailBody = prepareEmailBody();
            intent.putExtra(android.content.Intent.EXTRA_TEXT, emailBody);
            intent.setType("plain/text");
            startActivity(intent);
            feedbackSent();
        } catch (Exception e) {
            showToast(R.string.lab_no_email_client);
        }
    }

    private String prepareEmailBody() throws NameNotFoundException {
        StringBuilder body = new StringBuilder();
        body.append("\n");
        body.append(getString(R.string.lab_device_info));
        body.append("\n-----------------------");
        body.append("\n" + getString(R.string.lab_device_make) + ": " + Build.MANUFACTURER + "/"
                + Build.MODEL + "/" + Build.PRODUCT );
        body.append("\n" + getString(R.string.lab_android_version) + ": " + Build.VERSION.RELEASE
                );
        body.append("\n" + getString(R.string.lab_mshw_version) + ": "
                + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);

        Preferences prefs = new Preferences(this);
        if (prefs.getSelectedSchoolID() != 0) {
            body.append("\n\n" + getString(R.string.lab_school_choosen) + ": " + "("
                    + prefs.getSelectedSchoolID() + ") " + prefs.getSchoolName());
        }
        
        body.append("\n\n"+getString(R.string.lab_your_problem));
        return body.toString();
    }
    
    private void feedbackSent(){
        setResult(RESULT_OK);
        finish();
    }

    private void showToast(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }
}
