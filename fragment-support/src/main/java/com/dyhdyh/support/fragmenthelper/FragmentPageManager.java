package com.dyhdyh.support.fragmenthelper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;

import com.dyhdyh.support.fragmenthelper.listener.OnFragmentPageChangeListener;

/**
 * viewpager+fragment管理
 *
 * @author dengyuhan
 * created 2019/4/23 17:24
 */
public final class FragmentPageManager {

    public static void registerFragmentShowLifecycle(FragmentManager fm, ViewPager viewPager) {
        registerFragmentShowLifecycle(fm, viewPager, true);
    }

    /**
     *
     * @param fm
     * @param viewPager
     * @param resumedCallback 是否在注册时根据状态回调一次
     */
    public static void registerFragmentShowLifecycle(FragmentManager fm, ViewPager viewPager, boolean resumedCallback) {
        final PagerAdapter adapter = viewPager.getAdapter();
        if (adapter instanceof FragmentPagerAdapter) {
            final int currentItem = viewPager.getCurrentItem();
            fm.registerFragmentLifecycleCallbacks(new FragmentPagerShowLifecycleCallbacks(viewPager), false);
            viewPager.addOnPageChangeListener(new OnFragmentPageChangeListener((FragmentPagerAdapter) adapter, currentItem));

            //如果是onResume之后才注册的 就手动回调一次
            if (resumedCallback) {
                final Fragment fragment = ((FragmentPagerAdapter) adapter).getItem(currentItem);
                if (fragment.isResumed()) {
                    FragmentLifecycleManager.notifyResumeShow(fragment, false);
                }
            }
        }
    }

    /**
     * 当fragment里面有子fragment
     *
     * @param viewPager
     */
    public static void registerChildFragmentShowLifecycle(ViewPager viewPager) {
        final PagerAdapter adapter = viewPager.getAdapter();
        if (adapter instanceof FragmentPagerAdapter) {
            viewPager.addOnPageChangeListener(new OnFragmentPageChangeListener((FragmentPagerAdapter) adapter, viewPager.getCurrentItem()));
        }
    }


    public static void notifyCurrentResumeShow(ViewPager viewPager) {
        notifyCurrentResumeShow(viewPager, false);
    }

    /**
     * 通知viewpager当前的fragment回调onResumeShow
     *
     * @param viewPager
     * @param lifecycle 是否来自生命周期
     */
    public static void notifyCurrentResumeShow(ViewPager viewPager, boolean lifecycle) {
        final OnFragmentPagerRunnable runnable = new OnFragmentPagerRunnable() {
            @Override
            public void onFragmentItemRun(int index, Fragment item) {
                FragmentLifecycleManager.notifyResumeShow(item, lifecycle);
            }
        };
        if (ViewCompat.isLaidOut(viewPager)) {
            callPagerCurrentFragmentRunnable(viewPager, runnable);
        } else {
            viewPager.post(new Runnable() {
                @Override
                public void run() {
                    callPagerCurrentFragmentRunnable(viewPager, runnable);
                }
            });
        }
    }

    public static void notifyCurrentPauseShow(ViewPager viewPager) {
        notifyCurrentPauseShow(viewPager, false);
    }

    /**
     * 通知viewpager当前的fragment回调onPauseShow
     *
     * @param viewPager
     * @param lifecycle 是否来自生命周期
     */
    public static void notifyCurrentPauseShow(ViewPager viewPager, boolean lifecycle) {
        callPagerCurrentFragmentRunnable(viewPager, new OnFragmentPagerRunnable() {
            @Override
            public void onFragmentItemRun(int index, Fragment item) {
                FragmentLifecycleManager.notifyPauseShow(item, lifecycle);
            }
        });
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

    public static Fragment getViewPagerCurrentFragment(ViewPager viewPager) {
        return getFragmentByViewPager(viewPager, viewPager.getCurrentItem());
    }

    public static Fragment getFragmentByViewPager(ViewPager viewPager, int position) {
        try {
            final PagerAdapter adapter = viewPager.getAdapter();
            if (adapter instanceof FragmentPagerAdapter) {
                return ((FragmentPagerAdapter) adapter).getItem(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface OnFragmentPagerRunnable {
        void onFragmentItemRun(int index, Fragment item);
    }


    /**
     * @author dengyuhan
     * created 2018/7/23 14:11
     */
    private static class FragmentPagerShowLifecycleCallbacks extends FragmentManager.FragmentLifecycleCallbacks {
        private ViewPager mViewPager;

        public FragmentPagerShowLifecycleCallbacks(ViewPager viewPager) {
            this.mViewPager = viewPager;
        }

        @Override
        public void onFragmentResumed(FragmentManager fm, Fragment f) {
            final Fragment fragment = FragmentPageManager.getViewPagerCurrentFragment(mViewPager);
            if (fragment == f) {
                FragmentLifecycleManager.notifyResumeShow(f, true);
            }
        }

        @Override
        public void onFragmentPaused(FragmentManager fm, Fragment f) {
            final Fragment fragment = FragmentPageManager.getViewPagerCurrentFragment(mViewPager);
            if (fragment == f) {
                FragmentLifecycleManager.notifyPauseShow(f, true);
            }
        }
    }

}
