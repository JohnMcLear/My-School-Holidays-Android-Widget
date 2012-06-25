package com.primaryt.mshwidget.threads;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.primaryt.mshwidget.bo.School;
import com.primaryt.mshwidget.utils.Config;
import com.primaryt.mshwidget.utils.IOUtils;
import com.primaryt.mshwidget.utils.SchoolInfoParser;

public class SchoolFetchThread extends BasicThread {

	private final static String BASE_URL = "http://myschoolholidays.com/api/searchForSchoolOrDistrict?q=";

	private final static String TAG = "SchoolFetchThread";

	private String queryString;

	public SchoolFetchThread(Context context, Handler handler,
			String queryString) {
		super(context, handler);
		this.queryString = queryString;
	}

	@Override
	public void run() {
		if (Config.LOGGING) {
			Log.i(TAG, "Query String: " + queryString);
			Log.i(TAG, "API Call: " + BASE_URL + queryString);
		}

		HttpClient client = new DefaultHttpClient();
		queryString = URLEncoder.encode(queryString);
		HttpGet getRequest = new HttpGet(BASE_URL + queryString);
		try {
			HttpResponse response = client.execute(getRequest);
			if (checkResponseCode(response.getStatusLine().getStatusCode())) {
				processData(response.getEntity().getContent());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			catchAndSendException(e, queryString);
		} catch (IOException e) {
			e.printStackTrace();
			catchAndSendException(e, queryString);
		}
	}

	private void processData(InputStream stream) throws IOException {
		String response = IOUtils.convertStreamToString(stream);
		if (Config.LOGGING) {
			Log.i(TAG, "Response: " + response);
		}
		if (response != null) {
			SchoolInfoParser schoolParser = new SchoolInfoParser(response);
			try {
				ArrayList<School> schoolList = schoolParser.parse();
				if (schoolList != null) {
					sendSchoolList(schoolList);
				} else {
					catchAndSendException(new IOException("No results found"),
							queryString);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				catchAndSendException(new IOException("No results found"),
						queryString);
			}
		} else {
			catchAndSendException(new IOException("No data"), queryString);
		}
	}

}
