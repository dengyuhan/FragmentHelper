package com.dyhdyh.support.fragmenthelper;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * @author dengyuhan
 *         created 2018/7/23 14:06
 */
public class ShowFragmentLifecycleCallbacks extends FragmentManager.FragmentLifecycleCallbacks {

    @Override
    public void onFragmentResumed(FragmentManager fm, Fragment f) {
        if (f instanceof FragmentLifecycle){
            ((FragmentLifecycle) f).onResumeShow();
        }
    }

    @Override
    public void onFragmentPaused(FragmentManager fm, Fragment f) {
        if (f instanceof FragmentLifecycle){
            ((FragmentLifecycle) f).onPauseShow();
        }
    }
}
