package com.dyhdyh.support.fragmenthelper;

/**
 * @author dengyuhan
 * created 2018/7/13 13:57
 */
public interface FragmentLifecycle {

    /**
     * 恢复显示
     *
     * @param lifecycle 是否生命周期的回调
     */
    void onResumeShow(boolean lifecycle);

    /**
     * 暂停显示
     * @param lifecycle 是否生命周期的回调
     */
    void onPauseShow(boolean lifecycle);

}
