package com.sweethome.jimmy.mynews.Views;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sweethome.jimmy.mynews.Controllers.Fragments.PageArticlesListFragment;
import com.sweethome.jimmy.mynews.R;



public class PageAdapter extends FragmentPagerAdapter {

    private Context context;

    public PageAdapter(FragmentManager fm, Context context) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0 :
                return PageArticlesListFragment.newInstance(position);
            case 1 :
                return PageArticlesListFragment.newInstance(position);
            case 2 :
                return PageArticlesListFragment.newInstance(position);
            default :
                return PageArticlesListFragment.newInstance(0);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch(position){
            case 0 :
                return context.getString(R.string.TabTitleTopStories);
            case 1 :
                return context.getString(R.string.TabTitleMostPopular);
            case 2 :
                return context.getString(R.string.TabTitleBusiness);
            default :
                return null;
        }
    }


}
