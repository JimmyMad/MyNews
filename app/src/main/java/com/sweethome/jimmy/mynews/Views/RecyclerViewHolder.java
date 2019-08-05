package com.sweethome.jimmy.mynews.Views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.sweethome.jimmy.mynews.Models.Doc;
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

    public void updateWithResults(Result result, int position) {
        if (position == 1) {
            if (result.getMedia() != null && result.getMedia().size() >= 1) {
                Glide.with(this.imageView).load(result.getMedia().get(0).getMediaMetadata().get(0).getUrl()).into(imageView);
            } else
                imageView.setImageResource(R.mipmap.new_york_times_default_image_article);
        } else {
            if (result.getMultimedia() != null && result.getMultimedia().size() >= 1) {
                Glide.with(this.imageView).load(result.getMultimedia().get(0).getUrl()).into(imageView);
            } else
                imageView.setImageResource(R.mipmap.new_york_times_default_image_article);
        }
        String cat = result.getSection();


        if (result.getOrgFacet().size() >= 1)
            cat += " > " + result.getOrgFacet().get(0);
        else if (result.getPerFacet().size() >= 1)
            cat += " > " + result.getPerFacet().get(0);
        else if (result.getGeoFacet().size() >= 1)
            cat += " > " + result.getGeoFacet().get(0);
        else if (result.getDesFacet().size() >= 1)
            cat += " > " + result.getDesFacet().get(0);

        categoryTextView.setText(cat);
        titleTextView.setText(result.getTitle());

        String dateBeforeFormat = result.getPublishedDate().substring(0, 10);
        String[] dates = dateBeforeFormat.split("-");
        String date = dates[2] + "/" + dates[1] + "/" + dates[0];
        datesTextView.setText(date);
    }

    public void updateWithDoc(Doc doc) {
        if (doc.getMultimedia().size() >= 17+1) {
                Glide.with(this.imageView).load("https://static01.nyt.com/" + doc.getMultimedia().get(17).getUrl()).into(imageView);
            } else
                if (doc.getMultimedia().size() >= 1) {
                    Glide.with(this.imageView).load("https://static01.nyt.com/" + doc.getMultimedia().get(1).getUrl()).into(imageView);
                }
                else {
                    imageView.setImageResource(R.mipmap.new_york_times_default_image_article);
                }

        String cat = doc.getSectionName();


        if (doc.getSubsection_name() != null)
            cat += " > " + doc.getSubsection_name();
        else if (doc.getNewsDesk() != null)
            cat += " > " + doc.getNewsDesk();

        categoryTextView.setText(cat);
        titleTextView.setText(doc.getHeadline().getMain());
        String dateBeforeFormat = doc.getPubDate().substring(0, 10);
        String[] dates = dateBeforeFormat.split("-");
        String date = dates[2] + "/" + dates[1] + "/" + dates[0];
        datesTextView.setText(date);
    }
}
