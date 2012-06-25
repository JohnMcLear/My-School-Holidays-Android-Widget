package com.primaryt.mshwidget;

import java.util.ArrayList;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.primaryt.mshwidget.adapters.SchoolAdapter;
import com.primaryt.mshwidget.bo.School;
import com.primaryt.mshwidget.services.BackgroundUpdaterService;
import com.primaryt.mshwidget.threads.SchoolFetchThread;
import com.primaryt.mshwidget.utils.Preferences;

public class HolidayAppActivity extends Activity {

	private EditText editTextSchoolName;

	private SchoolAdapter adapter;

	private TextView textViewSchool;

	private Button buttonSave;

	private School selectedSchool;

	private int mAppWidgetId;

	private boolean ignore = true;

	private final static int REQUEST_CONFIRMATION = 1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.main);

		if (getIntent().hasExtra("ignore")) {
			ignore = getIntent().getBooleanExtra("ignore", false);
		}
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
			if (mAppWidgetId != 0) {
				ignore = false;
			}
		}

		initializeUIElements();

		// Intent newIntent = new Intent(this, LaunchingActivity.class);
		// PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
		// newIntent, 0);
		//
		// // Get the layout for the App Widget and attach an on-click listener
		// // to the button
		// RemoteViews views = new RemoteViews(this.getPackageName(),
		// R.layout.widget_holiday);
		// views.setOnClickPendingIntent(R.id.relativeLayout1, pendingIntent);
	}

	private void initializeUIElements() {

		Button buttonHelp = (Button) findViewById(R.id.buttonHelp);
		buttonHelp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				launchFeedbackAndRatingActivity();
			}
		});

		if (!ignore) {
			Preferences prefs = new Preferences(this);
			if (prefs.getSelectedSchoolID() == -1) {
				// Do nothing
			} else {
				// Update Widget
				updateWidget();
			}
		}

		editTextSchoolName = (EditText) findViewById(R.id.editTextSchoolName);

		editTextSchoolName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.length() > 3) {
					queryForSchools(s.toString());
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		ListView listViewSchools = (ListView) findViewById(R.id.listView1);
		listViewSchools.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int index, long position) {
				School school = (School) view.getTag();
				schoolSelected(school);
			}
		});
		adapter = new SchoolAdapter(this);
		listViewSchools.setAdapter(adapter);

		textViewSchool = (TextView) findViewById(R.id.textView2);
		textViewSchool.setVisibility(View.INVISIBLE);

		buttonSave = (Button) findViewById(R.id.button1);
		buttonSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				saveSelectedSchoolToPrefs();
				showConfirmationScreen();
			}
		});
	}

	private void queryForSchools(String schoolName) {
		if (!TextUtils.isEmpty(schoolName)) {
			Log.i("School", schoolName);
			textViewSchool.setVisibility(View.INVISIBLE);
			buttonSave.setVisibility(View.INVISIBLE);
			setProgressBarIndeterminateVisibility(true);
			adapter.clear();
			adapter.notifyDataSetChanged();
			Thread thread = new SchoolFetchThread(getApplicationContext(),
					handler, schoolName);
			thread.start();
		}
	}

	private Handler handler = new Handler() {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			setProgressBarIndeterminateVisibility(false);
			if (msg.getData() != null) {
				if (msg.getData().containsKey("value")) {
					ArrayList<School> schoolList = (ArrayList<School>) msg
							.getData().getSerializable("value");
					updateSchoolList(schoolList);
				} else if (msg.getData().containsKey("exception")) {
					showErrorMessage(msg.getData().get("exception").toString(),
							msg.getData().getString("query"));
				}
			}
		}

	};

	private void updateSchoolList(ArrayList<School> schoolList) {
		adapter.setSchoolList(schoolList);
		adapter.notifyDataSetChanged();

	}

	private void schoolSelected(School school) {
		selectedSchool = school;
		textViewSchool.setText(school.getSchoolLabel());
		textViewSchool.setVisibility(View.VISIBLE);
		buttonSave.setVisibility(View.VISIBLE);
	}

	private void saveSelectedSchoolToPrefs() {
		Preferences prefs = new Preferences(this);
		prefs.updateSelectedSchool(selectedSchool);
	}

	private void showErrorMessage(String message, String query) {
		if (query.equals(editTextSchoolName.getText().toString())) {
			Toast.makeText(this, message, Toast.LENGTH_LONG).show();
		}
	}

	private void updateWidget() {
		Intent resultValue = new Intent();
		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
		setResult(RESULT_OK, resultValue);

		Intent updateIntent = new Intent();
		updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		updateIntent
				.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
		sendBroadcast(updateIntent);

		Intent intent = new Intent(this, BackgroundUpdaterService.class);
		stopService(intent);
		startService(intent);

		finish();

	}

	private void showConfirmationScreen() {
		Intent intent = new Intent(this, SchoolConfirmationActivity.class);
		intent.putExtra("school", selectedSchool.getSchoolLabel());
		startActivityForResult(intent, REQUEST_CONFIRMATION);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CONFIRMATION) {
			if (resultCode == RESULT_OK) {
				updateWidget();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void launchFeedbackAndRatingActivity() {
		Intent intent = new Intent(this, FeedbackActivity.class);
		startActivity(intent);
	}
}
