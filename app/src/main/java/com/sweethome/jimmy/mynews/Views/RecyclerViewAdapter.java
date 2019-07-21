package com.sweethome.jimmy.mynews.Views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sweethome.jimmy.mynews.Models.Result;
import com.sweethome.jimmy.mynews.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    // FOR DATA
    private List<Result> results;
    private int position;

    // CONSTRUCTOR
    public RecyclerViewAdapter(List<Result> results, int position) {
        this.results = results;
        this.position = position;
    }



    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_articles_list_item, parent, false);
        return new RecyclerViewHolder(view, this.position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.updateWithResults(results.get(position));
    }

    @Override
    public int getItemCount() { return results.size(); }
}
