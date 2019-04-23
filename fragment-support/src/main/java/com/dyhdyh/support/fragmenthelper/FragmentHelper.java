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
 * created 2019/4/23 17:58
 */
public final class FragmentHelper {
    private FragmentManager mFragmentManager;
    private @IdRes
    int mContainerViewId;
    private List<Fragment> mFragments;
    private OnFragmentChangeListener mOnFragmentChangeListener;

    private Fragment mLastShowFragment;

    private boolean mAllowingStateLoss;


    public FragmentHelper(FragmentManager fragmentManager, @IdRes int containerViewId) {
        this.mFragmentManager = fragmentManager;
        this.mContainerViewId = containerViewId;

        this.mFragmentManager.registerFragmentLifecycleCallbacks(new FragmentShowLifecycleCallbacks(new OnAllowFragmentLifecycleCallback() {
            @Override
            public boolean onAllowLifecycle(Fragment fragment) {
                return mLastShowFragment == fragment;
            }
        }), false);
    }

    public FragmentHelper setAllowingStateLoss(boolean allowingStateLoss) {
        this.mAllowingStateLoss = allowingStateLoss;
        return this;
    }

    public FragmentHelper setFragments(Fragment... fragments) {
        this.mFragments = Arrays.asList(fragments);
        return this;
    }

    public FragmentHelper setFragments(List<Fragment> fragments) {
        this.mFragments = fragments;
        return this;
    }

    public FragmentHelper build() {
        return build(true);
    }

    public FragmentHelper build(boolean changed) {
        if (changed) {
            changeFragment(0);
        }
        return this;
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
            //隐藏上一次选中的fragment
            if (mLastShowFragment != null) {
                transaction.hide(mLastShowFragment);
                FragmentLifecycleManager.notifyPauseShow(mLastShowFragment, false);
            }

            if (fragment.isAdded()) {
                //如果已经加过 显示当前选的
                transaction.show(fragment);
                FragmentLifecycleManager.notifyResumeShow(mLastShowFragment, false);
            } else {
                transaction.add(mContainerViewId, fragment, fragment.getClass().getName());
            }

            mLastShowFragment = fragment;
            if (mAllowingStateLoss) {
                transaction.commitAllowingStateLoss();
            } else {
                transaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FragmentHelper setOnFragmentChangeListener(OnFragmentChangeListener onFragmentChangeListener) {
        this.mOnFragmentChangeListener = onFragmentChangeListener;
        return this;
    }


    private static class FragmentShowLifecycleCallbacks extends FragmentManager.FragmentLifecycleCallbacks {

        private OnAllowFragmentLifecycleCallback mAllowCallback;

        public FragmentShowLifecycleCallbacks(OnAllowFragmentLifecycleCallback callback) {
            this.mAllowCallback = callback;
        }

        @Override
        public void onFragmentResumed(FragmentManager fm, Fragment f) {
            if (mAllowCallback != null && mAllowCallback.onAllowLifecycle(f) && f instanceof FragmentLifecycle) {
                FragmentLifecycleManager.notifyResumeShow(f, true);
            }
        }

        @Override
        public void onFragmentPaused(FragmentManager fm, Fragment f) {
            if (mAllowCallback != null && mAllowCallback.onAllowLifecycle(f) && f instanceof FragmentLifecycle) {
                FragmentLifecycleManager.notifyPauseShow(f, true);
            }
        }

        @Override
        public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
            FragmentLifecycleManager.notifyDestroy(f);
        }
    }
}
