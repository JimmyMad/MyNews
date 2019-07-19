package com.sweethome.jimmy.mynews.Utils;

import com.sweethome.jimmy.mynews.Models.Article;
import com.sweethome.jimmy.mynews.Models.Result;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NyTimesService {

    @GET("topstories/v2/home.json")
    Observable<Article> getTopStories(@Query("api-key") String apiKeyNt);

    @GET("mostpopular/v2/viewed/1.json")
    Observable<Article> getMostPopular(@Query("apiKey") String apiKeyNt);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
