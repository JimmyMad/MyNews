package com.sweethome.jimmy.mynews.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

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
    private int hit = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        SharedPreferences sharedPrefs = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        Log.e(TAG, sharedPrefs.getString("query", ""));
        Log.e(TAG, sharedPrefs.getString("categories", ""));

        DisposableObserver<SearchArticle> disposableObserver = new DisposableObserver<SearchArticle>() {
            @Override
            public void onNext(SearchArticle searchArticle) {
                hit = searchArticle.getResponse().getMeta().getHits();
                createNotificationChannel();
                createNotification();
                Log.e(TAG, "je suis passé par la");
            }

            @Override
            public void onError(Throwable e) {
                String msg = e.getMessage();
                Log.e("Erreur 1 :", msg);
            }

            @Override
            public void onComplete() {
                Log.e("Erreur 2 :", "On complete");
            }
        };
        Disposable disposable = NyTimesStreams.streamFetchSearchArticles(sharedPrefs.getString("query", ""), sharedPrefs.getString("categories", "")).subscribeWith(disposableObserver);

        Log.e(TAG, String.valueOf(hit));

    }

    private void createNotification() {
        String text = "No article found with your demand.";
        if (hit >= 1)
            text = "We found " + hit + " article(s) matching your demand.";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.context, CHANNEL_ID)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.new_york_times_default_image_article))
                .setSmallIcon(R.drawable.nyt_16)
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
