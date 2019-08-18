package com.sweethome.jimmy.mynews.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.sweethome.jimmy.mynews.Controllers.Activities.MainActivity;
import com.sweethome.jimmy.mynews.Models.SearchArticle;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SearchArticleForNotification {

    private Context context;
    private int hit;

    SearchArticleForNotification(Context context) {
        this.context = context;
        this.hit = 0;
    }

    public int getHit() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String query = sharedPreferences.getString("query", "");
        String categories = sharedPreferences.getString("categories", "");


        return hit;
    }
}
