package com.sweethome.jimmy.mynews.Utils;


import com.sweethome.jimmy.mynews.Models.Article;
import com.sweethome.jimmy.mynews.Models.Result;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NyTimesStreams {

    public static Observable<Article> streamFetchTopStories(){
        NyTimesService nyTimesService = NyTimesService.retrofit.create(NyTimesService.class);
        return nyTimesService.getResults()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}