package com.sweethome.jimmy.mynews.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.sweethome.jimmy.mynews.Models.SearchArticle;
import com.sweethome.jimmy.mynews.R;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class BroadCastReceiver extends BroadcastReceiver {


    private static final String CHANNEL_ID = "0" ;
    private Context context;
    private int hit = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        SharedPreferences sharedPrefs = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        DisposableObserver<SearchArticle> disposableObserver = new DisposableObserver<SearchArticle>() {
            @Override
            public void onNext(SearchArticle searchArticle) {
                hit = searchArticle.getResponse().getMeta().getHits();
                createNotificationChannel();
                createNotification();
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        };
        Disposable disposable = NyTimesStreams.streamFetchSearchArticles(sharedPrefs.getString("query", ""), sharedPrefs.getString("categories", "")).subscribeWith(disposableObserver);
    }

    private void createNotification() {
        String text = context.getString(R.string.NotificationNoResult);
        if (hit >= 1)
            text = context.getString(R.string.NotificationResultFound1) + hit + context.getString(R.string.NotificationResultFound2);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.context, CHANNEL_ID)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.new_york_times_default_image_article))
                .setSmallIcon(R.drawable.nyt_16)
                .setContentTitle(context.getString(R.string.app_name))
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
