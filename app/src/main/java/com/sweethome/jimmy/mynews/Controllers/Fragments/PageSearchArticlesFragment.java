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

import com.google.gson.Gson;
import com.sweethome.jimmy.mynews.Controllers.Activities.WebViewActivity;
import com.sweethome.jimmy.mynews.Models.Doc;
import com.sweethome.jimmy.mynews.Models.Response;
import com.sweethome.jimmy.mynews.R;
import com.sweethome.jimmy.mynews.Utils.ItemClickSupport;
import com.sweethome.jimmy.mynews.Views.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PageSearchArticlesFragment extends Fragment {


    // DESIGN
    @BindView(R.id.fragment_search_page_recyclerView)
    RecyclerView recyclerView;

    // DATA
    private List<Doc> docs;
    private RecyclerViewAdapter adapter;

    public PageSearchArticlesFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_search_articles, container, false);
        ButterKnife.bind(this, view);
        configureRecyclerView();
        configureOnClickRecyclerView();
        updateUI();

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    // -----------------
    // CONFIGURATION
    // -----------------

    private void configureRecyclerView() {
        this.docs = new ArrayList<>();
        this.adapter = new RecyclerViewAdapter(null, this.docs, 4);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    // -----------------
    // ACTION
    // -----------------

    // 1 - Configure item click on RecyclerView
    private void configureOnClickRecyclerView(){
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

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI() {
        Gson gson = new Gson();
        String json = getArguments().getString("ARTICLES_SEARCH");
        this.docs.addAll(gson.fromJson(json, Response.class).getDocs());
        adapter.notifyDataSetChanged();
    }
}