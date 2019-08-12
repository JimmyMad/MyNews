package com.sweethome.jimmy.mynews.Utils;

import android.content.SharedPreferences;
import android.util.Log;

import com.sweethome.jimmy.mynews.Models.SearchArticle;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SearchArticleForNotification {

    private SharedPreferences sharedPreferences;
    private int hit;

    SearchArticleForNotification(SharedPreferences sharedPreferences) {
     this.sharedPreferences = sharedPreferences;
    }

    public int getHit() {

        Log.e(TAG, sharedPreferences.getString("query", ""));
        Log.e(TAG, sharedPreferences.getString("categories", ""));
        Disposable disposable = NyTimesStreams.streamFetchSearchArticles(sharedPreferences.getString("query", ""),
                sharedPreferences.getString("categories", "")).subscribeWith(new DisposableObserver<SearchArticle>() {

            @Override
            public void onNext(SearchArticle searchArticle) {
                hit = searchArticle.getResponse().getMeta().getHits();
                Log.e(TAG, "je suis passé par la");
            }

            @Override
            public void onError(Throwable e) {
                String msg = e.getMessage();
                Log.e("Erreur 1 :", msg);
            }

            @Override
            public void onComplete() {
                //hit = searchArticle.getResponse().getMeta().getHits();
                Log.e("Erreur 2 :", "On complete");
            }
        });
        return hit;
    }
}
