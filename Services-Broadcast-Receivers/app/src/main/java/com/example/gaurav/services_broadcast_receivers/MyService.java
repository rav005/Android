package com.example.gaurav.services_broadcast_receivers;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by gaurav on 10/08/15.
 */
//Inspired from: https://developer.android.com/training/run-background-service/create-service.html#DefineManifest
//  https://developer.android.com/training/run-background-service/report-status.html
public class MyService extends IntentService {

    public MyService(){
        super("MyService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent != null){
            final String action = intent.getAction();

            if("com.example.gaurav.services_broadcast_receivers.action.SLEEP".equals(action)){
                int sleepTime = intent.getIntExtra("Time", 0);
                sleepTime *= 1000;
                Log.d("MyService", "MyService - onHandleIntent: intent will sleep for " + sleepTime + " milliseconds.");

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("MyService", "MyService - onHandleIntent: done sleeping.");

                Intent response = new Intent("com.example.gaurav.services_broadcast_receivers.broadcast.Complete");
                response.putExtra("Message", "MyService - onHandleIntent: successfully slept for " + sleepTime + " milliseconds.");

                LocalBroadcastManager.getInstance(this).sendBroadcast(response);
                Log.d("MyService", "MyService - onHandleIntent: successfully send response.");
            }

        }else{
            Log.d("MyService", "MyService - onHandleIntent: passed in intent is null.");
        }
    }
}
