package dk.au.mad21fall.appproject.justdrink.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import dk.au.mad21fall.appproject.justdrink.Model.Constants;
import dk.au.mad21fall.appproject.justdrink.Model.Repository;
import dk.au.mad21fall.appproject.justdrink.R;


public class PlaceOfferService extends Service {


    //execute something in another thread
    private ScheduledExecutorService executorService;

    //counter to make unique notification id's
    private int notificationCounter = 0;

    //to make notifications
    NotificationManagerCompat notificationManager;

    //define number of seconds between 2 notifications
    private final int NOTIFICATIONPERIOD = 90;

    public PlaceOfferService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = NotificationManagerCompat.from(this);

        //Scheduled ThreadPool to execute at specific times
        executorService = Executors.newScheduledThreadPool(10);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"Service is started",Toast.LENGTH_SHORT).show();
        Log.d(Constants.SERVICE,"Service has started");

        //required by API level 26+
        createNotificationChannel();

        //execute sendNotiicationToUser after x seconds and repeat every x seconds
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.d(Constants.SERVICE,"Send a notification");
                Repository.getInstance(getApplication().getApplicationContext()).getOffer(new Response.Listener<OfferMessage>() {
                    @Override
                    public void onResponse(OfferMessage response) {
                        sendNotificationToUser(response);
                    }
                });

            }
        },NOTIFICATIONPERIOD,NOTIFICATIONPERIOD, TimeUnit.SECONDS);

        //not stop Service after shutdown of app
        return START_STICKY;
    }

    //build the notification with unique id. Notify user afterward
    private void sendNotificationToUser(OfferMessage msg) {
        notificationManager.notify(notificationCounter++,getNotificationBuilder(msg).build());
    }

    //log when stopped
    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Service is ended",Toast.LENGTH_SHORT).show();
    }

    //onBind not used
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    //build the notification with image, titel and text with movietitle and release year. Default notification priority
    private NotificationCompat.Builder getNotificationBuilder(OfferMessage msg) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Constants.CHANNELID)
                .setSmallIcon(R.mipmap.ic_launcher2)
                .setContentTitle("Tilbud fra "+msg.owner)
                .setContentText(msg.offerMessage)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        return builder;
    }

    //Function used to create Notification Channel which is a must for API 26+ to make notifications
    //Whole this functions implementation is taken from Android Developers:
    //https://developer.android.com/training/notify-user/channels
    //The code is under the heading "Create a notification channel"
    //Visited: 15th October 2021
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channelTitle);
            String description = getString(R.string.channelDescription);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(Constants.CHANNELID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

