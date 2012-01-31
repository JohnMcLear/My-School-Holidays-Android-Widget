
package com.primaryt.mshwidget.threads;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.primaryt.mshwidget.bo.Event;
import com.primaryt.mshwidget.bo.School;

public class BasicThread extends Thread {
    private Handler handler;

    private Context context;

    public BasicThread(Context context, Handler handler) {
        this.handler = handler;
        this.context = context;
    }

    protected void catchAndSendException(Throwable e, String query) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putSerializable("exception", e.getLocalizedMessage());
        bundle.putString("query", query);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    protected boolean checkResponseCode(int responseCode) {
        boolean value = false;
        switch (responseCode) {
            case 200:
                value = true;
                break;
            default:
                value = false;
                break;
        }
        return value;
    }

    protected void sendSchoolList(ArrayList<School> schoolList) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putSerializable("value", schoolList);
        message.setData(bundle);
        handler.sendMessage(message);
    }
    
    protected void sendEvent(ArrayList<Event> event) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putSerializable("value", event);
        message.setData(bundle);
        handler.sendMessage(message);
    }
    public Context getContext(){
        return context;
    }
}
