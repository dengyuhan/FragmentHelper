package com.dyhdyh.support.fragmenthelper.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.dyhdyh.support.fragmenthelper.FragmentLifecycle;

import java.util.Arrays;
import java.util.List;

/**
 * author  dengyuhan
 * created 2017/6/9 18:15
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    private List<String> mTitles;

    public SimpleFragmentPagerAdapter(FragmentManager fm, Fragment... fragmentArray) {
        super(fm);
        this.mFragments = Arrays.asList(fragmentArray);
    }

    public SimpleFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        this(fm, fragments, null);
    }

    public SimpleFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.mFragments = fragments;
        this.mTitles = titles;
    }

    public List<Fragment> getFragments() {
        return mFragments;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (this.mTitles == null) {
            return super.getPageTitle(position);
        } else {
            return mTitles.get(position);
        }
    }
}
