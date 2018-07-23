package com.dyhdyh.support.fragmenthelper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * @author dengyuhan
 *         created 2018/7/23 14:11
 */
public class ShowFragmentPagerLifecycleCallbacks extends FragmentManager.FragmentLifecycleCallbacks {
    private ViewPager mViewPager;

    public ShowFragmentPagerLifecycleCallbacks(ViewPager viewPager) {
        this.mViewPager = viewPager;
    }

    @Override
    public void onFragmentResumed(FragmentManager fm, Fragment f) {
        final PagerAdapter adapter = mViewPager.getAdapter();
        if (adapter instanceof FragmentPagerAdapter) {
            final Fragment item = ((FragmentPagerAdapter) adapter).getItem(mViewPager.getCurrentItem());
            if (item == f) {
                if (item instanceof FragmentLifecycle) {
                    ((FragmentLifecycle) f).onResumeShow();
                }
            }
        }
    }

    @Override
    public void onFragmentPaused(FragmentManager fm, Fragment f) {
        final PagerAdapter adapter = mViewPager.getAdapter();
        if (adapter instanceof FragmentPagerAdapter) {
            final Fragment item = ((FragmentPagerAdapter) adapter).getItem(mViewPager.getCurrentItem());
            if (item == f) {
                if (item instanceof FragmentLifecycle) {
                    ((FragmentLifecycle) f).onPauseShow();
                }
            }
        }
    }
}
