package com.sweethome.jimmy.mynews.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sweethome.jimmy.mynews.Models.Doc;
import com.sweethome.jimmy.mynews.Models.Result;
import com.sweethome.jimmy.mynews.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    // FOR DATA
    private List<Result> results;
    private List<Doc> docs;
    private int position;

    // CONSTRUCTOR
    public RecyclerViewAdapter(List<Result> results, List<Doc> docs, int position) {
        this.results = results;
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
        if (results != null)
            holder.updateWithResults(results.get(position), null,  this.position);
        else
            holder.updateWithResults(null, docs.get(position), 0);
    }

    @Override
    public int getItemCount() {
        if (results != null)
            return results.size();
        else
            return docs.size();
    }

    public String getArticleUrl(int position) {
        if (results != null)
            return results.get(position).getUrl();
        else
            return docs.get(position).getWebUrl();
    }
}
