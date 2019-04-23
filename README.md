维护Fragment的显示状态

## ViewPager
#### Fragment
如果是`ViewPager`+`Fragment`，`viewpager.setAdapter`后注册回调

```
FragmentHelper.bindFragmentLifecycle(getSupportFragmentManager(), viewpager);
```

#### 嵌套Fragment
如果是`ViewPager`+`Fragment`，Fragment里还有`ViewPager`+`Fragment`，先Activity中的`viewpager.setAdapter`后注册回调

```
FragmentHelper.registerChildFragmentLifecycle(viewPager);
```

再在Fragment中手动回调子Fragment

```
@Override
public void onResumeShow() {
    FragmentHelper.onChildResumeShow(viewPager);
}

@Override
public void onPauseShow() {
    FragmentHelper.onChildPauseShow(viewPager);
}
```

#### 回调
Fragment实现`FragmentLifecycle`，即会回调`onResumeShow`和`onPauseShow`

```
@Override
public void onResumeShow() {
    Log.d("---->", "显示在最前");
}

@Override
public void onPauseShow() {
    Log.d("---->", "不显示了");
}
```