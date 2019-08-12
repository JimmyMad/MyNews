package com.sweethome.jimmy.mynews.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.sweethome.jimmy.mynews.Models.SearchArticle;
import com.sweethome.jimmy.mynews.R;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MyBroadCastReceiver extends BroadcastReceiver {


    private static final String CHANNEL_ID = "0" ;
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        android.os.Debug.waitForDebugger();
        this.context = context;
        SharedPreferences sharedPrefs = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        Log.e(TAG, sharedPrefs.getString("query", ""));
        Log.e(TAG, sharedPrefs.getString("categories", ""));

        SearchArticleForNotification searchArticleForNotification = new SearchArticleForNotification(sharedPrefs);
        int hit = searchArticleForNotification.getHit();

        Log.e(TAG, String.valueOf(hit));

        createNotificationChannel();

        String text = "No article found with your demand.";
        if (hit >= 1)
            text = "We found " + hit + " article(s) matching your demand.";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.new_york_times_default_image_article)
                .setContentTitle("MyNews")
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.context);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = this.context.getString(R.string.channel_name);
            String description = this.context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = this.context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
