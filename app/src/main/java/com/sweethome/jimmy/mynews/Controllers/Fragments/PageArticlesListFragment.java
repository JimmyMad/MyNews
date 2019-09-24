package com.sweethome.jimmy.mynews.Controllers.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sweethome.jimmy.mynews.Controllers.Activities.WebViewActivity;
import com.sweethome.jimmy.mynews.Models.Article;
import com.sweethome.jimmy.mynews.Models.ArticleMostPopular;
import com.sweethome.jimmy.mynews.Models.Result;
import com.sweethome.jimmy.mynews.Models.ResultMostPopular;
import com.sweethome.jimmy.mynews.R;
import com.sweethome.jimmy.mynews.Utils.ItemClickSupport;
import com.sweethome.jimmy.mynews.Utils.NyTimesStreams;
import com.sweethome.jimmy.mynews.Views.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class PageArticlesListFragment extends Fragment {

    @BindView(R.id.fragment_article_page_recyclerView)
    RecyclerView recyclerView;

    private Disposable disposable;
    private List<Result> results;
    private List<ResultMostPopular> resultMostPopulars;
    private RecyclerViewAdapter adapter;
    private int position;

    public PageArticlesListFragment() {
    }

    public PageArticlesListFragment(int position) {
        this.position = position;
    }

    public static PageArticlesListFragment newInstance(int position) {
        return (new PageArticlesListFragment(position));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles_list, container, false);
        ButterKnife.bind(this, view);
        configureRecyclerView();
        configureOnClickRecyclerView();
        executeHttpRequest(this.position);

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }


    private void configureRecyclerView() {
        this.results = new ArrayList<>();
        this.resultMostPopulars = new ArrayList<>();
        this.adapter = new RecyclerViewAdapter(this.results, this.resultMostPopulars, null, this.position);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    // 1 - Configure item click on RecyclerView
    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_articles_list_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        String url = adapter.getArticleUrl(position);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                });
    }

    private void disposeWhenDestroy() {
        if (disposable != null && disposable.isDisposed()) disposable.dispose();
    }

    // Execute the request for each of the 3 tabs
    private void executeHttpRequest(int position) {

        switch (position) {
            case 0:
                disposable = NyTimesStreams.streamFetchTopStories("home").subscribeWith(new DisposableObserver<Article>() {
                    @Override
                    public void onNext(Article article) {
                        updateUI(article, null);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
                break;
            case 1:
                disposable = NyTimesStreams.streamFetchMostPopular().subscribeWith(new DisposableObserver<ArticleMostPopular>() {
                    @Override
                    public void onNext(ArticleMostPopular articleMostPopular) {
                        updateUI(null, articleMostPopular);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
                break;
            case 2:
                disposable = NyTimesStreams.streamFetchTopStories("business").subscribeWith(new DisposableObserver<Article>() {
                    @Override
                    public void onNext(Article article) {
                        updateUI(article, null);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
                break;
        }

    }

    // Updates the UI with the articles
    private void updateUI(Article article, ArticleMostPopular articleMostPopular) {
        if (article != null)
            this.results.addAll(article.getResults());
        else
            this.resultMostPopulars.addAll(articleMostPopular.getResultMostPopulars());
        adapter.notifyDataSetChanged();
    }
}
