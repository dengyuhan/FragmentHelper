package com.dyhdyh.support.fragmenthelper;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.dyhdyh.support.fragmenthelper.listener.OnFragmentPageChangeListener;

/**
 * fragment viewpager
 *
 * @author dengyuhan
 *         created 2018/7/13 15:49
 */
public class FragmentPageHelper {

    public static void bindFragmentLifecycle(ViewPager viewPager) {
        final PagerAdapter adapter = viewPager.getAdapter();
        if (adapter != null && adapter instanceof FragmentPagerAdapter) {
            final OnFragmentPageChangeListener listener = new OnFragmentPageChangeListener((FragmentPagerAdapter) adapter);
            viewPager.addOnPageChangeListener(listener);

            //如果初始化位置是0手动调一次
            if (viewPager.getCurrentItem() == 0) {
                listener.onPageSelected(0);
            }
        }
    }
}
