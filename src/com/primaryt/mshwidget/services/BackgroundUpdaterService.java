
package com.primaryt.mshwidget.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.primaryt.mshwidget.LaunchingActivity;
import com.primaryt.mshwidget.R;
import com.primaryt.mshwidget.bo.Event;
import com.primaryt.mshwidget.receivers.HolidayAppWidgetProvider;
import com.primaryt.mshwidget.threads.EventFetchThread;
import com.primaryt.mshwidget.utils.Config;
import com.primaryt.mshwidget.utils.Preferences;

public class BackgroundUpdaterService extends Service {

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    private void updateTextInfo(ArrayList<Event> eventList) {
        if (Config.LOGGING) {
            String logfilePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "msh";
            File logDir = new File(logfilePath);
            if (!logDir.exists()) {
                logDir.mkdirs();
            }
            logfilePath = logfilePath + File.separator + "log.txt";
            FileWriter fw;
            try {
                fw = new FileWriter(new File(logfilePath));
                SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
                Date date = new Date(Calendar.getInstance().getTimeInMillis());
                fw.append("Time: " + format.format(date) + "\n");
                fw.flush();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        Event event = null;
        for (int i = 0; i < eventList.size(); i++) {
            event = eventList.get(i);
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_holiday);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                HolidayAppWidgetProvider.class));
        if (event != null) {
            for (int i = 0; i < appWidgetIds.length; i++) {
                int appWidgetId = appWidgetIds[i];
                String nextEventDate = event.getNextEventDate();
                remoteViews.setViewVisibility(R.id.syncing_image, View.GONE);
                if (nextEventDate != null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date convertedDate = dateFormat.parse(nextEventDate);

                        remoteViews.setImageViewResource(
                                R.id.date_0,
                                getResources().getIdentifier(
                                        "date_" + nextEventDate.charAt(8) + "", "drawable",
                                        "com.primaryt.mshwidget"));

                        remoteViews.setImageViewResource(
                                R.id.date_1,
                                getResources().getIdentifier(
                                        "date_" + nextEventDate.charAt(9) + "", "drawable",
                                        "com.primaryt.mshwidget"));

                        remoteViews.setImageViewResource(
                                R.id.month_0,
                                getResources().getIdentifier(
                                        "date_" + nextEventDate.charAt(5) + "", "drawable",
                                        "com.primaryt.mshwidget"));
                        remoteViews.setImageViewResource(
                                R.id.month_1,
                                getResources().getIdentifier(
                                        "date_" + nextEventDate.charAt(6) + "", "drawable",
                                        "com.primaryt.mshwidget"));

                        remoteViews.setImageViewResource(
                                R.id.year_0,
                                getResources().getIdentifier(
                                        "date_" + nextEventDate.charAt(0) + "", "drawable",
                                        "com.primaryt.mshwidget"));
                        remoteViews.setImageViewResource(
                                R.id.year_1,
                                getResources().getIdentifier(
                                        "date_" + nextEventDate.charAt(1) + "", "drawable",
                                        "com.primaryt.mshwidget"));
                        remoteViews.setImageViewResource(
                                R.id.year_2,
                                getResources().getIdentifier(
                                        "date_" + nextEventDate.charAt(2) + "", "drawable",
                                        "com.primaryt.mshwidget"));
                        remoteViews.setImageViewResource(
                                R.id.year_3,
                                getResources().getIdentifier(
                                        "date_" + nextEventDate.charAt(3) + "", "drawable",
                                        "com.primaryt.mshwidget"));

                        Calendar currentDate = Calendar.getInstance();
                        Long currentDateMS = currentDate.getTimeInMillis();
                        Long passedDateMS = convertedDate.getTime();
                        long diff = passedDateMS - currentDateMS;
                        long diffDays = diff / (24 * 60 * 60 * 1000);
                        diffDays = diffDays + 1;
                        if (diffDays < 10) {
                            remoteViews.setImageViewResource(R.id.imageDay1, 0);
                            remoteViews.setImageViewResource(
                                    R.id.imageDay2,
                                    getResources().getIdentifier("cal_" + diffDays, "drawable",
                                            "com.primaryt.mshwidget"));
                        } else {
                            int onesPlace = (int) (diffDays % 10);
                            int tensPlace = (int) (diffDays / 10);
                            remoteViews.setImageViewResource(
                                    R.id.imageDay1,
                                    getResources().getIdentifier("cal_" + tensPlace, "drawable",
                                            "com.primaryt.mshwidget"));
                            remoteViews.setImageViewResource(
                                    R.id.imageDay2,
                                    getResources().getIdentifier("cal_" + onesPlace, "drawable",
                                            "com.primaryt.mshwidget"));
                        }

                        remoteViews.setViewVisibility(R.id.date_0, View.VISIBLE);
                        remoteViews.setViewVisibility(R.id.date_1, View.VISIBLE);
                        remoteViews.setViewVisibility(R.id.imageView4, View.VISIBLE);
                        remoteViews.setViewVisibility(R.id.month_0, View.VISIBLE);
                        remoteViews.setViewVisibility(R.id.month_1, View.VISIBLE);
                        remoteViews.setViewVisibility(R.id.imageView5, View.VISIBLE);
                        remoteViews.setViewVisibility(R.id.year_0, View.VISIBLE);
                        remoteViews.setViewVisibility(R.id.year_1, View.VISIBLE);
                        remoteViews.setViewVisibility(R.id.year_2, View.VISIBLE);
                        remoteViews.setViewVisibility(R.id.year_3, View.VISIBLE);
                        // remoteViews.setTextViewText(R.id.textDays,""+
                        // diffDays);
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                Intent newIntent = new Intent(this, LaunchingActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, newIntent, 0);

                // Get the layout for the App Widget and attach an on-click
                // listener
                // to the button

                remoteViews.setOnClickPendingIntent(R.id.relativeLayout1, pendingIntent);
                appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
            }
        }

    }

    private Handler handler = new Handler() {

        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.getData() != null) {
                if (msg.getData().containsKey("value")) {
                    ArrayList<Event> eventList = (ArrayList<Event>) msg.getData().get("value");
                    updateTextInfo(eventList);
                } else if (msg.getData().containsKey("exception")) {
                    Log.i("HAService", msg.getData().getString("exception"));
                }
            }
            Log.i("HAService", "Stopping service");
            stopSelf();
            Log.i("HAService", "Service stopped");
        }

    };

    @Override
    public void onCreate() {
        super.onCreate();
        Preferences preference = new Preferences(this);
        Long schoolID = preference.getSelectedSchoolID();
        Thread thread = new EventFetchThread(this, handler, "" + schoolID);
        thread.start();

        Log.i("HAService", "Service started");
    }
}
