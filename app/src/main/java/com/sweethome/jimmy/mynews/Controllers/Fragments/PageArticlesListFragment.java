package com.sweethome.jimmy.mynews.Controllers.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sweethome.jimmy.mynews.Models.Article;
import com.sweethome.jimmy.mynews.Models.Result;
import com.sweethome.jimmy.mynews.R;
import com.sweethome.jimmy.mynews.Utils.NyTimesStreams;
import com.sweethome.jimmy.mynews.Views.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class PageArticlesListFragment extends Fragment {

    // DESIGN
    @BindView(R.id.fragment_article_page_recyclerView)
    RecyclerView recyclerView;

    // DATA
    private Disposable disposable;
    private List<Result> results;
    private RecyclerViewAdapter adapter;
    private int position;

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
        executeHttpRequest(this.position);

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }


    // -----------------
    // CONFIGURATION
    // -----------------

    private void configureRecyclerView() {
        this.results = new ArrayList<>();
        this.adapter = new RecyclerViewAdapter(this.results, this.position);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void disposeWhenDestroy() {
        if (disposable != null && disposable.isDisposed()) disposable.dispose();
    }

    private void executeHttpRequest(int position) {

        switch (position) {
            case 0:
                disposable = NyTimesStreams.streamFetchTopStories().subscribeWith(new DisposableObserver<Article>() {
                    @Override
                    public void onNext(Article article) {
                        updateUI(article);
                    }

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onComplete() {}
                });
            case 1:
                disposable = NyTimesStreams.streamFetchMostPopular().subscribeWith(new DisposableObserver<Article>() {
                    @Override
                    public void onNext(Article article) {
                        updateUI(article);
                    }

                    @Override
                    public void onError(Throwable e) {
                        String msg = e.getMessage();
                        Log.e("Erreur pos 1", msg);

                    }

                    @Override
                    public void onComplete() {}
                });
        }

    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(Article article) {

        this.results.addAll(article.getResults());
        adapter.notifyDataSetChanged();
    }

}
