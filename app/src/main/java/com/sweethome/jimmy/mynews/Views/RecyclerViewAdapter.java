package com.sweethome.jimmy.mynews.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sweethome.jimmy.mynews.Models.Doc;
import com.sweethome.jimmy.mynews.Models.Result;
import com.sweethome.jimmy.mynews.Models.ResultMostPopular;
import com.sweethome.jimmy.mynews.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    // List of TopStories / Business etc... articles
    private List<Result> results;
    // List of MostPopular articles
    private List<ResultMostPopular> resultMostPopulars;
    // List of Searched articles
    private List<Doc> docs;
    // The tab position (0 = TopStories, 1 = MostPopular, 2 = Business)
    private int position;

    public RecyclerViewAdapter(List<Result> results, List<ResultMostPopular> resultMostPopulars, List<Doc> docs, int position) {
        this.results = results;
        this.resultMostPopulars = resultMostPopulars;
        this.docs = docs;
        this.position = position;
    }



    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_articles_list_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        if (results != null || resultMostPopulars != null) {
            if (this.position == 1)
                holder.updateWithResults(null, resultMostPopulars.get(position), null);
            else
                holder.updateWithResults(results.get(position),null, null);
        }
        else
            holder.updateWithResults(null, null, docs.get(position));
    }

    @Override
    public int getItemCount() {
        if (results != null || resultMostPopulars != null) {
            if (this.position == 1)
                return resultMostPopulars.size();
            else
                return results.size();
        }
        else
            return docs.size();
    }

    // Gets the url to load the WebView
    public String getArticleUrl(int position) {
        if (results != null || resultMostPopulars != null) {
            if (this.position == 1)
                return resultMostPopulars.get(position).getUrl();
            else
                return results.get(position).getUrl();
        }
        else
            return docs.get(position).getWebUrl();
    }
}
