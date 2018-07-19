package com.dyhdyh.support.fragmenthelper.example;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dyhdyh.support.fragmenthelper.FragmentLifecycle;

/**
 * @author dengyuhan
 *         created 2018/7/13 14:31
 */
public abstract class BaseFragment extends Fragment implements FragmentLifecycle {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_layout, container, false);
        final TextView tv = layout.findViewById(R.id.tv);
        tv.setText(getLabel());
        return layout;
    }

    @NonNull
    protected abstract String getLabel();

    @Override
    public void onForeground() {
        Log.d("---->", "--------->" + this + "---->显示在最前");
    }

    @Override
    public void onBackground() {
        Log.d("---->", "--------->" + this + "---->退到后台");
    }
}
