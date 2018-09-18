package com.dyhdyh.support.fragmenthelper;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.dyhdyh.support.fragmenthelper.listener.OnFragmentChangeListener;
import com.dyhdyh.support.fragmenthelper.listener.OnFragmentPageChangeListener;

import java.util.Arrays;
import java.util.List;

/**
 * @author dengyuhan
 *         created 2017/11/10 11:36
 */
public class FragmentHelper {
    private FragmentManager mFragmentManager;
    private @IdRes
    int mContainerViewId;
    private List<Fragment> mFragments;
    private OnFragmentChangeListener mOnFragmentChangeListener;

    private Fragment mLastShowFragment;

    public FragmentHelper(FragmentManager fragmentManager, @IdRes int containerViewId, Fragment... fragmentArray) {
        this(fragmentManager, containerViewId, Arrays.asList(fragmentArray));
    }

    public FragmentHelper(FragmentManager fragmentManager, @IdRes int containerViewId, List<Fragment> fragments) {
        this.mFragmentManager = fragmentManager;
        this.mContainerViewId = containerViewId;
        this.mFragments = fragments;

        this.mFragmentManager.registerFragmentLifecycleCallbacks(new ShowFragmentLifecycleCallbacks(new OnAllowFragmentLifecycleCallback() {
            @Override
            public boolean onAllowLifecycle(Fragment fragment) {
                return mLastShowFragment == fragment;
            }
        }), false);
    }

    public List<Fragment> getFragments() {
        return mFragments;
    }

    public void changeFragment(int index) {
        changeFragment(mFragments.get(index));
    }

    public void changeFragment(Fragment fragment) {
        try {
            if (mLastShowFragment == fragment) {
                return;
            }
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            if (fragment.isAdded()) {
                //如果已经加过 显示当前选的
                transaction.show(fragment);
                if (fragment instanceof FragmentLifecycle) {
                    ((FragmentLifecycle) fragment).onResumeShow();
                }
            } else {
                transaction.add(mContainerViewId, fragment, fragment.getClass().getName());
            }
            //隐藏上一次选中的fragment
            if (mLastShowFragment != null) {
                transaction.hide(mLastShowFragment);
                if (mLastShowFragment instanceof FragmentLifecycle) {
                    ((FragmentLifecycle) mLastShowFragment).onPauseShow();
                }
            }
            mLastShowFragment = fragment;
            transaction.commitAllowingStateLoss();
            mFragmentManager.executePendingTransactions();

            if (mOnFragmentChangeListener != null) {
                mOnFragmentChangeListener.onFragmentChanged(fragment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnFragmentChangeListener(OnFragmentChangeListener onFragmentChangeListener) {
        this.mOnFragmentChangeListener = onFragmentChangeListener;
    }


    public static void bindFragmentLifecycle(FragmentManager fm, ViewPager viewPager) {
        final PagerAdapter adapter = viewPager.getAdapter();
        if (adapter != null && adapter instanceof FragmentPagerAdapter) {
            fm.registerFragmentLifecycleCallbacks(new ShowFragmentPagerLifecycleCallbacks(viewPager), false);
            viewPager.addOnPageChangeListener(new OnFragmentPageChangeListener((FragmentPagerAdapter) adapter, viewPager.getCurrentItem()));
        }
    }

    /**
     * 当fragment里面有子fragment
     *
     * @param viewPager
     */
    public static void bindChildFragmentLifecycle(ViewPager viewPager) {
        final PagerAdapter adapter = viewPager.getAdapter();
        if (adapter != null && adapter instanceof FragmentPagerAdapter) {
            viewPager.addOnPageChangeListener(new OnFragmentPageChangeListener((FragmentPagerAdapter) adapter, viewPager.getCurrentItem()));
        }
    }


    /**
     * 对viewpager里的fragment批量做操作
     *
     * @param viewPager
     * @param runnable
     */
    public static void callPagerFragmentRunnable(ViewPager viewPager, OnFragmentPagerRunnable runnable) {
        if (runnable != null) {
            final PagerAdapter adapter = viewPager.getAdapter();
            if (adapter instanceof FragmentPagerAdapter) {
                final int count = adapter.getCount();
                for (int i = 0; i < count; i++) {
                    final Fragment item = ((FragmentPagerAdapter) adapter).getItem(i);
                    runnable.onFragmentItemRun(i, item);
                }
            }
        }
    }

    /**
     * 从viewpager里找到当前的fragment执行一些操作
     *
     * @param viewPager
     * @param runnable
     */
    public static void callPagerCurrentFragmentRunnable(ViewPager viewPager, OnFragmentPagerRunnable runnable) {
        if (runnable != null) {
            final PagerAdapter adapter = viewPager.getAdapter();
            if (adapter instanceof FragmentPagerAdapter) {
                final int currentItem = viewPager.getCurrentItem();
                final Fragment item = ((FragmentPagerAdapter) adapter).getItem(currentItem);
                runnable.onFragmentItemRun(currentItem, item);
            }
        }
    }

    public interface OnFragmentPagerRunnable {
        void onFragmentItemRun(int index, Fragment item);
    }
}
