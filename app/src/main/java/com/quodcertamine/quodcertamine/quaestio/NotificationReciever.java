package com.quodcertamine.quodcertamine.quaestio;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.Random;

/**
 * Created by Nick on 2/9/2017.
 */
public class NotificationReciever extends BroadcastReceiver {

    private String[] content = {
            "Good Morning! Here is a question",
            "What would you rather do this morning?",
            "Click here to gain some insight",
            "What is Would You Rather thinking about this morning"
    };

    @Override
    public void onReceive(Context context, Intent intent){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent review_intent = new Intent(context, MainActivity.class);
        review_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,review_intent,PendingIntent.FLAG_UPDATE_CURRENT);

        int random = new Random().nextInt(content.length);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.applogo)
                .setContentTitle("Quaestio")
                .setContentText(content[random])
                .setAutoCancel(true);
        notificationManager.notify(100, builder.build());
    }
}
