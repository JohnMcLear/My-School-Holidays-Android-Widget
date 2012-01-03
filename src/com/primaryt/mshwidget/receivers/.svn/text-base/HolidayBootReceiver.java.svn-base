
package com.primaryt.mshwidget.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.primaryt.mshwidget.services.BackgroundUpdaterService;

public class HolidayBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, BackgroundUpdaterService.class);
        context.startService(serviceIntent);
        Log.i("HABootComplete", "Starting Update service");
    }

}
