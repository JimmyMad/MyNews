package com.sweethome.jimmy.mynews.Views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sweethome.jimmy.mynews.Models.Result;
import com.sweethome.jimmy.mynews.R;


import butterknife.BindView;
import butterknife.ButterKnife;


public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.Fragment_Article_Page_Item_Image) ImageView imageView;
    @BindView(R.id.Fragment_Article_Page_Item_Category) TextView categoryTextView;
    @BindView(R.id.Fragment_Article_Page_Item_Title) TextView titleTextView;
    @BindView(R.id.Fragment_Article_Page_Item_Dates) TextView datesTextView;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithResults(Result results) {
        if(results.getMultimedia().size() != 0) {
            Glide.with(this.imageView).load(results.getMultimedia().get(0).getUrl()).into(imageView);
        }
        categoryTextView.setText(results.getSection());
        titleTextView.setText(results.getTitle());
        datesTextView.setText(results.getPublishedDate());
    }

}
