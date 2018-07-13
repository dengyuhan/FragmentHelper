package com.dyhdyh.support.fragmenthelper.listener;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.dyhdyh.support.fragmenthelper.FragmentLifecycle;

/**
 * @author dengyuhan
 *         created 2018/7/13 15:46
 */
public class OnFragmentPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
    private FragmentPagerAdapter mAdapter;
    private int mPosition;

    public OnFragmentPageChangeListener(FragmentPagerAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public void onPageSelected(int position) {
        try {
            Fragment item = mAdapter.getItem(this.mPosition);
            if (item instanceof FragmentLifecycle) {
                ((FragmentLifecycle) item).onBackground();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        this.mPosition = position;

        try {
            Fragment item = mAdapter.getItem(this.mPosition);
            if (item instanceof FragmentLifecycle) {
                ((FragmentLifecycle) item).onForeground();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
