package com.sweethome.jimmy.mynews.Views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sweethome.jimmy.mynews.Models.Doc;
import com.sweethome.jimmy.mynews.Models.Result;
import com.sweethome.jimmy.mynews.Models.ResultMostPopular;
import com.sweethome.jimmy.mynews.R;
import com.sweethome.jimmy.mynews.Utils.Tools;

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

    // Fills the recyclerView with the articles
    public void updateWithResults(Result result, ResultMostPopular resultMostPopular, Doc doc) {

        if (result != null) {
            if (result.getMultimedia() != null && result.getMultimedia().size() >= 1) {
                Glide.with(this.imageView).load(result.getMultimedia().get(0).getUrl()).into(imageView);
            } else
                imageView.setImageResource(R.mipmap.new_york_times_default_image_article);
            String cat = result.getSection();

        if (result.getDesFacet().size() >= 1)
                cat += " > " + result.getDesFacet().get(0);
        else if (result.getPerFacet().size() >= 1)
            cat += " > " + result.getPerFacet().get(0);
        else if (result.getGeoFacet().size() >= 1)
            cat += " > " + result.getGeoFacet().get(0);
        else if (result.getOrgFacet().size() >= 1)
            cat += " > " + result.getOrgFacet().get(0);

            categoryTextView.setText(cat);
            titleTextView.setText(result.getTitle());
            datesTextView.setText(Tools.dateFormatter(result.getPublishedDate()));
        }

        if (resultMostPopular != null) {
            if (resultMostPopular.getMedia() != null && resultMostPopular.getMedia().size() >= 1) {
                Glide.with(this.imageView).load(resultMostPopular.getMedia().get(0).getMediaMetadata().get(0).getUrl()).into(imageView);
            } else
                imageView.setImageResource(R.mipmap.new_york_times_default_image_article);

            String cat = resultMostPopular.getSection();

            categoryTextView.setText(cat);
            titleTextView.setText(resultMostPopular.getTitle());
            datesTextView.setText(Tools.dateFormatter(resultMostPopular.getPublishedDate()));
        }

        if (doc != null) {
            if (doc.getMultimedia().size() >= 17 + 1) {
                Glide.with(this.imageView).load("https://static01.nyt.com/" + doc.getMultimedia().get(17).getUrl()).into(imageView);
            } else if (doc.getMultimedia().size() >= 1) {
                Glide.with(this.imageView).load("https://static01.nyt.com/" + doc.getMultimedia().get(1).getUrl()).into(imageView);
            } else {
                imageView.setImageResource(R.mipmap.new_york_times_default_image_article);
            }

            String cat = doc.getSectionName();

            if (doc.getSubsection_name() != null)
                cat += " > " + doc.getSubsection_name();
            else if (doc.getNewsDesk() != null)
                cat += " > " + doc.getNewsDesk();

            categoryTextView.setText(cat);
            titleTextView.setText(doc.getHeadline().getMain());
            datesTextView.setText(Tools.dateFormatter(doc.getPubDate()));
        }
    }
}
