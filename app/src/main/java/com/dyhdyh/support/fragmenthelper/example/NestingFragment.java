package com.dyhdyh.support.fragmenthelper.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dyhdyh.support.fragmenthelper.FragmentHelper;
import com.dyhdyh.support.fragmenthelper.adapter.SimpleFragmentPagerAdapter;

/**
 * @author dengyuhan
 * created 2019/4/23 15:23
 */
public class NestingFragment extends LabelFragment {
    private ViewPager viewPager;

    public static LabelFragment create(String label) {
        NestingFragment f = new NestingFragment();
        final Bundle bundle = new Bundle();
        bundle.putString("label", label);
        f.setArguments(bundle);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_nesting, container, false);
        tv = layout.findViewById(R.id.tv);
        tv.setText(getLabel());

        viewPager = layout.findViewById(R.id.vp);

        BaseFragment[] fragments = new BaseFragment[]{
                LabelFragment.create(getLabel() + " A"), LabelFragment.create(getLabel() + " B"), LabelFragment.create(getLabel() + " C"), LabelFragment.create(getLabel() + " D")
        };
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        FragmentHelper.registerChildFragmentLifecycle(viewPager);
        return layout;
    }


    @Override
    public void onResumeShow() {
        super.onResumeShow();

        FragmentHelper.onChildResumeShow(viewPager);
    }

    @Override
    public void onPauseShow() {
        super.onPauseShow();

        FragmentHelper.onChildPauseShow(viewPager);
    }
}
