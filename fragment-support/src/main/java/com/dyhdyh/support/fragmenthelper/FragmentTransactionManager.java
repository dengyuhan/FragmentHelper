package com.dyhdyh.support.fragmenthelper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * @author dengyuhan
 * created 2017/11/10 11:36
 */
public final class FragmentTransactionManager {

    public static void addFragment(FragmentManager fm, ViewGroup parent, Fragment fragment) {
        addFragment(fm, parent, fragment, false);
    }

    public static void addFragment(FragmentManager fm, ViewGroup parent, Fragment fragment, boolean allowingStateLoss) {
        FrameLayout container = new FrameLayout(parent.getContext());
        container.setId(fragment.hashCode());
        parent.addView(container);
        final FragmentTransaction ft = fm.beginTransaction();
        ft.add(container.getId(), fragment);
        ft.commit();
        if (allowingStateLoss) {
            ft.commitAllowingStateLoss();
        } else {
            ft.commit();
        }
    }

    public static boolean removeFragment(ViewGroup parent, Fragment fragment) {
        return removeFragment(parent, fragment, false);
    }

    public static boolean removeFragment(ViewGroup parent, Fragment fragment, boolean allowingStateLoss) {
        if (fragment != null && fragment.isAdded()) {
            final View container = parent.findViewById(fragment.hashCode());
            if (container != null) {
                parent.removeView(container);
            }
            final FragmentTransaction ft = fragment.getFragmentManager().beginTransaction();
            ft.remove(fragment);
            if (allowingStateLoss) {
                ft.commitAllowingStateLoss();
            } else {
                ft.commit();
            }
            return true;
        }
        return false;
    }

}
