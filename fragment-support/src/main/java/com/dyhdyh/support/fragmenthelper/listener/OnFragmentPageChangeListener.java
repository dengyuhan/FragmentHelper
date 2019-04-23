package com.dyhdyh.support.fragmenthelper.listener;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.dyhdyh.support.fragmenthelper.FragmentLifecycleManager;

/**
 * @author dengyuhan
 * created 2018/7/13 15:46
 */
public class OnFragmentPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
    private FragmentPagerAdapter mAdapter;
    private int mPosition = -1;
    private int mSelectedPosition = -1;

    public OnFragmentPageChangeListener(FragmentPagerAdapter adapter, int currentItem) {
        this.mAdapter = adapter;
        this.mPosition = currentItem;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        if (mSelectedPosition == position && positionOffset == 0f) {
            try {
                mSelectedPosition = -1;
                if (this.mPosition >= 0) {
                    Fragment item = mAdapter.getItem(this.mPosition);
                    FragmentLifecycleManager.notifyPauseShow(item, false);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }

            this.mPosition = position;

            try {
                Fragment item = mAdapter.getItem(this.mPosition);
                FragmentLifecycleManager.notifyResumeShow(item, false);
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        mSelectedPosition = position;
    }
}
