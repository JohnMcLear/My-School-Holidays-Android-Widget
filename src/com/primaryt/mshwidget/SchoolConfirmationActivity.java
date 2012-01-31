package com.primaryt.mshwidget;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SchoolConfirmationActivity extends Activity {

	private String selectedSchool;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.school_confirmation_activity);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			if (bundle.containsKey("school")) {
				selectedSchool = bundle.getString("school");
			}
		}

		Button buttonExit = (Button) findViewById(R.id.buttonExit);
		buttonExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
			}
		});

		TextView tvSchoolName = (TextView) findViewById(R.id.textViewSchoolName);
		if (selectedSchool != null && !TextUtils.isEmpty(selectedSchool)) {
			tvSchoolName.setText(selectedSchool);
		}

		TextView tvHelpText = (TextView) findViewById(R.id.textViewHelpText);
		tvHelpText.setText(Html.fromHtml(getResources().getString(
				R.string.lab_school_confirmation)));
	}

}
