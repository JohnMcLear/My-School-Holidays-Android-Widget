package com.primaryt.mshwidget.threads;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import android.content.Context;
import android.os.Handler;

import com.primaryt.mshwidget.bo.Event;
import com.primaryt.mshwidget.utils.EventInfoParser;
import com.primaryt.mshwidget.utils.IOUtils;

public class EventFetchThread extends BasicThread {
	private final static String BASE_URL = "http://myschoolholidays.com/myschool-text.php?s=";

	private String queryString;

	public EventFetchThread(Context context, Handler handler, String queryString) {
		super(context, handler);
		this.queryString = queryString;
	}

	@Override
	public void run() {
		HttpClient client = new DefaultHttpClient();
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
		EventInfoParser eventParser = new EventInfoParser(response);
		try {
			ArrayList<Event> eventList = eventParser.parse();
			sendEvent(eventList);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
