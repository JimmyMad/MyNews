package com.sweethome.jimmy.mynews;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class PageAdapter extends FragmentPagerAdapter {

    public PageAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0 :
                return PageArticlesListFragment.newInstance();
            case 1 :
                return PageArticlesListFragment.newInstance();
            case 2 :
                return PageArticlesListFragment.newInstance();
            default :
                return PageArticlesListFragment.newInstance();
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
                return "TOP STORIES";
            case 1 :
                return "MOST POPULAR";
            case 2 :
                return "BUSINESS";
            default :
                return null;
        }
    }


}
