package com.sweethome.jimmy.mynews;

import androidx.test.rule.ActivityTestRule;

import com.sweethome.jimmy.mynews.Controllers.Activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class PageAdapterTest {

    private MainActivity mActivity;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Throwable {
        mActivity = mainActivityActivityTestRule.getActivity();
    }

    @Test
    public void numberOfTabs_isCorrect() {
        assertEquals(3, mActivity.pagerAdapter.getCount());
    }

    @Test
    public void titles_areCorrect() {
        assertEquals("TOP STORIES", Objects.requireNonNull(mActivity.pagerAdapter.getPageTitle(0)));
        assertEquals("MOST POPULAR", Objects.requireNonNull(mActivity.pagerAdapter.getPageTitle(1)));
        assertEquals("BUSINESS", Objects.requireNonNull(mActivity.pagerAdapter.getPageTitle(2)));
    }

}