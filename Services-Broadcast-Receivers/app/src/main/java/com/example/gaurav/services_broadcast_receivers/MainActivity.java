package com.example.gaurav.services_broadcast_receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

//Inspired from: https://developer.android.com/training/run-background-service/send-request.html
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inspired from: https://developer.android.com/training/run-background-service/report-status.html
        //  AND          http://stackoverflow.com/questions/4805269/programmatically-register-a-broadcast-receiver
        MyServiceMessageReceiver receiver = new MyServiceMessageReceiver();
        IntentFilter intentFilter = new IntentFilter("com.example.gaurav.services_broadcast_receivers.broadcast.Complete");

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);


        Intent intent = new Intent(this, MyService.class);
        intent.setAction("com.example.gaurav.services_broadcast_receivers.action.SLEEP");
        intent.putExtra("Time", 10);

        Log.d("MyService", "MainActivity - onCreate: starting service intent.");
        startService(intent);
        Log.d("MyService", "MainActivity - onCreate: finished with service intent.");
    }

    //Inspired from: https://developer.android.com/training/run-background-service/report-status.html
    // in-class example from Andrew S.
    private class MyServiceMessageReceiver extends BroadcastReceiver {

        public MyServiceMessageReceiver(){
            super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("MyService", "MyServiceMessageReceiver - onReceive: Got a new message.");
            Log.d("MyService", "MyServiceMessageReceiver - onReceive: Message is: " + intent.getStringExtra("Message"));

            //Inspired from: http://developer.android.com/guide/topics/ui/notifiers/notifications.html
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.notificationlogo)
                            .setContentTitle("MyService")
                            .setContentText("You got new Message from Service.");

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1001, mBuilder.build());

        }
    }
}
