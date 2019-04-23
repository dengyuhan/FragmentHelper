package com.dyhdyh.support.fragmenthelper;

import android.support.v4.app.Fragment;
import android.util.SparseArray;

/**
 * Fragment显示生命周期的回调管理
 *
 * @author dengyuhan
 * created 2019/4/23 17:30
 */
public final class FragmentLifecycleManager {
    private static FragmentShowLifecycleCallbacks mCallbacks;
    private static SparseArray<Boolean> mStateArray = new SparseArray<>();

    public static void registerFragmentShowCallbacks(FragmentShowLifecycleCallbacks callbacks) {
        mCallbacks = callbacks;
    }

    public static void unregisterFragmentShowCallbacks() {
        mCallbacks = null;
    }

    /**
     * 通知回调
     *
     * @param fragment
     */
    public static void notifyResumeShow(Fragment fragment, boolean lifecycle) {
        if (fragment instanceof FragmentLifecycle) {
            mStateArray.put(fragment.hashCode(), true);
            ((FragmentLifecycle) fragment).onResumeShow(lifecycle);
            if (mCallbacks != null) {
                mCallbacks.onResumeShowed(fragment, lifecycle);
            }
        }
    }


    /**
     * 通知回调
     *
     * @param fragment
     */
    public static void notifyPauseShow(Fragment fragment, boolean lifecycle) {
        if (fragment instanceof FragmentLifecycle) {
            mStateArray.put(fragment.hashCode(), false);
            ((FragmentLifecycle) fragment).onPauseShow(lifecycle);
            if (mCallbacks != null) {
                mCallbacks.onResumePaused(fragment, lifecycle);
            }
        }
    }

    /**
     * 是否处于ResumeShow状态
     *
     * @param fragment
     */
    public static boolean isResumeShowed(Fragment fragment) {
        if (fragment == null) {
            return false;
        }
        final Boolean isResumeShowed = mStateArray.get(fragment.hashCode());
        return isResumeShowed == null ? false : isResumeShowed;
    }

    static void notifyDestroy(Fragment fragment) {
        if (fragment != null) {
            mStateArray.remove(fragment.hashCode());
        }
    }


    public interface FragmentShowLifecycleCallbacks {

        void onResumeShowed(Fragment f, boolean lifecycle);

        void onResumePaused(Fragment f, boolean lifecycle);
    }
}
