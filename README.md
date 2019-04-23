## 全局回调
#### 注册回调
```
FragmentLifecycleManager.registerFragmentShowCallbacks(new FragmentLifecycleManager.FragmentShowLifecycleCallbacks() {
    @Override
    public void onResumeShowed(Fragment f, boolean lifecycle) {
        //lifecycle：这次回调是否来自生命周期
        Log.d("---->", "显示在最前");
    }

    @Override
    public void onResumePaused(Fragment f, boolean lifecycle) {
        Log.d("---->", "处于不显示");
    }
});
```
#### 注销回调
```
@Override
protected void onDestroy() {
    super.onDestroy();
    FragmentLifecycleManager.unregisterFragmentShowCallbacks();
}
```


## ViewPager + Fragment
#### Fragment
`viewpager.setAdapter`后注册回调

```
FragmentPageManager.registerFragmentShowLifecycle(getSupportFragmentManager(), viewpager);
```

#### 嵌套Fragment
如果是`ViewPager`+`Fragment`，Fragment里还有`ViewPager`+`Fragment`，先Activity中的`viewpager.setAdapter`后注册回调

```
FragmentHelper.registerChildFragmentLifecycle(viewPager);
```

再在Fragment中手动回调子Fragment

```
@Override
public void onResumeShow(boolean lifecycle) {
    FragmentPageManager.notifyCurrentResumeShow(viewPager);
}

@Override
public void onPauseShow(boolean lifecycle) {
    FragmentPageManager.notifyCurrentResumeShow(viewPager);
}
```

#### 回调
在Fragment实现`FragmentLifecycle`，即会回调`onResumeShow`和`onPauseShow`

```
@Override
public void onResumeShow(boolean lifecycle) {
    Log.d("---->", "显示在最前");
}

@Override
public void onPauseShow(boolean lifecycle) {
    //lifecycle：这次回调是否来自生命周期
    Log.d("---->", "处于不显示");
}
```