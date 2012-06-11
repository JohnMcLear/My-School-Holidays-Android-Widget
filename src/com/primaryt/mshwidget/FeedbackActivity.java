
package com.primaryt.mshwidget;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class FeedbackActivity extends Activity {

    private static final int SEND_FEEDBACK = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_activity);

        initializeUIElements();
    }

    private void initializeUIElements() {
        Button buttonDone = (Button) findViewById(R.id.buttonCancel);
        buttonDone.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button buttonRate = (Button) findViewById(R.id.buttonRate);
        buttonRate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                rateApplication();
            }
        });
        Button buttonFeedback = (Button) findViewById(R.id.buttonFeedback);
        buttonFeedback.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                sendFeedback();
            }
        });
    }

    private void rateApplication() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.primaryt.mshwidget"));
            startActivity(intent);
        } catch (Exception e) {
            showToast(R.string.lab_no_market);
        }
    }

    private void sendFeedback() {
        try {
            Intent intent = new Intent(this, EmailConfirmationActivity.class);
            startActivityForResult(intent, SEND_FEEDBACK);
        } catch (Exception e) {
            showToast(R.string.lab_no_email_client);
        }
    }

    private void showToast(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }
}
