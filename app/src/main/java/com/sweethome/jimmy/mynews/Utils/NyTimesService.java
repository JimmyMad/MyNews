package com.sweethome.jimmy.mynews.Utils;

import com.sweethome.jimmy.mynews.Models.Article;
import com.sweethome.jimmy.mynews.Models.Doc;
import com.sweethome.jimmy.mynews.Models.Response;
import com.sweethome.jimmy.mynews.Models.Result;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NyTimesService {

    @GET("topstories/v2/{section}.json")
    Observable<Article> getTopStories(@Path("section") String section, @Query("api-key") String apiKeyNt);

    @GET("mostpopular/v2/viewed/1.json")
    Observable<Article> getMostPopular(@Query("api-key") String apiKeyNt);

    /*@GET("search/v2/articlesearch.json")
    Observable<Response> getBusinessArticles(@Query("news_desk")String newsDesk, @Query("api-key") String apiKeyNt);*/

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
