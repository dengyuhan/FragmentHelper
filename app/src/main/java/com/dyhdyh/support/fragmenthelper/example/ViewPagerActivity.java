package com.dyhdyh.support.fragmenthelper.example;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.dyhdyh.support.fragmenthelper.FragmentPageHelper;
import com.dyhdyh.support.fragmenthelper.adapter.SimpleFragmentPagerAdapter;

/**
 * @author dengyuhan
 *         created 2018/7/13 14:44
 */
public class ViewPagerActivity extends AppCompatActivity {
    ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        vp = findViewById(R.id.vp);

        BaseFragment[] fragments = new BaseFragment[]{
                new AFragment(), new BFragment(), new CFragment(), new DFragment()
        };
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(adapter);
        FragmentPageHelper.bindFragmentLifecycle(vp);
    }
}