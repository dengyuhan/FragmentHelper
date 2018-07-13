package com.dyhdyh.support.fragmenthelper;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.dyhdyh.support.fragmenthelper.listener.OnFragmentChangeListener;

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
            } else {
                transaction.add(mContainerViewId, fragment, fragment.getClass().getName());
            }
            //隐藏上一次选中的fragment
            if (mLastShowFragment != null) {
                transaction.hide(mLastShowFragment);
                if (mLastShowFragment instanceof FragmentLifecycle) {
                    ((FragmentLifecycle) mLastShowFragment).onBackground();
                }
            }
            mLastShowFragment = fragment;
            transaction.commitAllowingStateLoss();
            mFragmentManager.executePendingTransactions();

            if (fragment instanceof FragmentLifecycle) {
                ((FragmentLifecycle) fragment).onForeground();
            }

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

}
