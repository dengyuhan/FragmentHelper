package com.dyhdyh.support.fragmenthelper.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import java.util.List;

/**
 * 用池控制数量的PagerAdapter
 * @author dengyuhan
 * created 2019/4/23 11:21
 */
public class PoolFragmentPagerAdapter extends SimpleFragmentPagerAdapter {
    //默认5个
    private int mMaxPoolCount = 2;

    public PoolFragmentPagerAdapter(FragmentManager fm, Fragment... fragmentArray) {
        super(fm, fragmentArray);
    }

    public PoolFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm, fragments);
    }

    public PoolFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm, fragments, titles);
    }

    public void setMaxPoolCount(int maxPoolCount) {
        this.mMaxPoolCount = maxPoolCount;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
        if (getCount() > mMaxPoolCount) {
            super.destroyItem(container, position, object);
        }
    }
}
