
package com.primaryt.mshwidget.receivers;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.primaryt.mshwidget.services.BackgroundUpdaterService;

public class HolidayAppWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Intent intent = new Intent(context, BackgroundUpdaterService.class);
        context.startService(intent);
        Log.i("HAWidget", "Starting Update service");
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.i("HAWidget", "onEnabled");
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.i("HAWidget", "onDeleted");
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.i("HAWidget", "onDisabled");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.i("HAWidget", "onReceive");
    }
}
