package com.sweethome.jimmy.mynews.Utils;


import com.sweethome.jimmy.mynews.Models.Article;
import com.sweethome.jimmy.mynews.Models.Doc;
import com.sweethome.jimmy.mynews.Models.Response;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NyTimesStreams {

    private static final String apiKeyNt = "vYNxoAopAjLFANQNx7dMBKZDL8isrF9t";

    public static Observable<Article> streamFetchTopStories(String section){
        NyTimesService nyTimesService = NyTimesService.retrofit.create(NyTimesService.class);
        return nyTimesService.getTopStories(section, apiKeyNt)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<Article> streamFetchMostPopular(){
        NyTimesService nyTimesService = NyTimesService.retrofit.create(NyTimesService.class);
        return nyTimesService.getMostPopular(apiKeyNt)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<Response> streamFetchSearchArticles(String query, Timestamp beginDate, Timestamp endDate, String section){
        NyTimesService nyTimesService = NyTimesService.retrofit.create(NyTimesService.class);
        return nyTimesService.getSearchArticles(query, beginDate, endDate, section, apiKeyNt)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
