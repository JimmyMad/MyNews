package com.sweethome.jimmy.mynews.Controllers.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sweethome.jimmy.mynews.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageArticlesListFragment extends Fragment {

    public PageArticlesListFragment() {}

    public static PageArticlesListFragment newInstance() {
        return (new PageArticlesListFragment());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_articles_list, container, false);

        FrameLayout rootView = result.findViewById(R.id.fragment_article_page_frameLayout);
        TextView textView = result.findViewById(R.id.fragment_article_page_textView);

        return result;
    }



}
