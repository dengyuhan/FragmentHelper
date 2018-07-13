package com.dyhdyh.support.fragmenthelper.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import java.util.List;

/**
 * 不自动回收的PagerAdapter
 * author  dengyuhan
 * created 2017/6/23 16:05
 */
public class CacheFragmentPagerAdapter extends SimpleFragmentPagerAdapter{

    public CacheFragmentPagerAdapter(FragmentManager fm, Fragment... fragmentArray) {
        super(fm, fragmentArray);
    }

    public CacheFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm, fragments);
    }

    public CacheFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm, fragments, titles);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}
