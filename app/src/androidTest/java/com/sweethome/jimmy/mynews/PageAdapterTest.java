package com.sweethome.jimmy.mynews;

import androidx.test.rule.ActivityTestRule;

import com.sweethome.jimmy.mynews.Controllers.Activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PageAdapterTest {

    private MainActivity mActivity;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        mActivity = mainActivityActivityTestRule.getActivity();
    }

    // Test to see if we have the 3 tabs
    @Test
    public void numberOfTabs_isCorrect() {
        assertEquals(3, mActivity.pagerAdapter.getCount());
    }

    // Test to see if the 3 tabs are correctly named
    @Test
    public void titles_areCorrect() {
        assertEquals(mActivity.getString(R.string.TabTitleTopStories), Objects.requireNonNull(mActivity.pagerAdapter.getPageTitle(0)));
        assertEquals(mActivity.getString(R.string.TabTitleMostPopular), Objects.requireNonNull(mActivity.pagerAdapter.getPageTitle(1)));
        assertEquals(mActivity.getString(R.string.TabTitleBusiness), Objects.requireNonNull(mActivity.pagerAdapter.getPageTitle(2)));
        assertNull(mActivity.pagerAdapter.getPageTitle(3));
    }

}