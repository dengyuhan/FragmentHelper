package com.dyhdyh.support.fragmenthelper.example;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.dyhdyh.support.fragmenthelper.FragmentHelper;
import com.dyhdyh.support.fragmenthelper.adapter.SimpleFragmentPagerAdapter;

/**
 * @author dengyuhan
 * created 2018/7/13 14:44
 */
public class ViewPagerExampleActivity extends AppCompatActivity {
    ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        vp = findViewById(R.id.vp);

        BaseFragment[] fragments = new BaseFragment[]{
                NestingFragment.create("1"), NestingFragment.create("2"), NestingFragment.create("3"), NestingFragment.create("4")
        };
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(adapter);
        FragmentHelper.bindFragmentLifecycle(getSupportFragmentManager(), vp);
    }
}