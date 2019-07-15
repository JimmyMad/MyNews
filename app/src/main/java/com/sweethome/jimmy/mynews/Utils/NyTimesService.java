package com.sweethome.jimmy.mynews.Utils;

import com.sweethome.jimmy.mynews.Models.TopStoriesHome;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface NyTimesService {
    @GET("/topstories/v2/home.json?api-key=vYNxoAopAjLFANQNx7dMBKZDL8isrF9t")
    Observable<List<TopStoriesHome>> getTopStories();

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
