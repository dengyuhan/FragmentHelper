package com.dyhdyh.support.fragmenthelper;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.dyhdyh.support.fragmenthelper.listener.OnFragmentPageChangeListener;

/**
 * fragment viewpager
 * @author dengyuhan
 *         created 2018/7/13 15:49
 */
public class FragmentPageHelper {

    public static void bindFragmentLifecycle(ViewPager viewPager) {
        final PagerAdapter adapter = viewPager.getAdapter();
        if (adapter != null && adapter instanceof FragmentPagerAdapter) {
            viewPager.addOnPageChangeListener(new OnFragmentPageChangeListener((FragmentPagerAdapter) adapter));
        }
    }
}
