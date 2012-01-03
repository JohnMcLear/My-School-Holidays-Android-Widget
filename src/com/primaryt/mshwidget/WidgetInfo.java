
package com.primaryt.mshwidget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class WidgetInfo extends Activity {
    private TextView textViewWidgetHelp;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.widget_help);

        textViewWidgetHelp = (TextView) findViewById(R.id.TextViewWidgetHelp);

        textViewWidgetHelp.setText(Html.fromHtml(
                getResources().getString(R.string.widget_help_text)).toString());

        Button buttonGo = (Button) findViewById(R.id.buttonGo);
        buttonGo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startSchoolSearchActivity();
            }
        });
    }

    private void startSchoolSearchActivity() {
        Intent intent = new Intent(this, HolidayAppActivity.class);
        startActivity(intent);
        finish();
    }
}
