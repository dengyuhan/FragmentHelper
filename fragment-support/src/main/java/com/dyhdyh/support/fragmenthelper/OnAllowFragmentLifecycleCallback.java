package com.dyhdyh.support.fragmenthelper;

import android.support.v4.app.Fragment;

/**
 * 是否允许fragment执行声明周期的回调
 * @author dengyuhan
 *         created 2018/9/18 19:57
 */
public interface OnAllowFragmentLifecycleCallback {

    boolean onAllowLifecycle(Fragment fragment);

}
