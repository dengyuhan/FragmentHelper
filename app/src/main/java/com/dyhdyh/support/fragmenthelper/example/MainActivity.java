package com.dyhdyh.support.fragmenthelper.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.dyhdyh.support.fragmenthelper.FragmentHelper;
import com.dyhdyh.support.fragmenthelper.listener.SimpleOnTabSelectedListener;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;

    private FragmentHelper mFragmentHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tab);


        BaseFragment[] fragments = new BaseFragment[]{
                new AFragment(), new BFragment(), new CFragment(), new DFragment()
        };
        mFragmentHelper = new FragmentHelper(getSupportFragmentManager(), R.id.fragment_container, fragments);


        for (int i = 0; i < fragments.length; i++) {
            final TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(fragments[i].getLabel());
            tabLayout.addTab(tab);
        }

        tabLayout.addOnTabSelectedListener(new SimpleOnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mFragmentHelper.changeFragment(tab.getPosition());
            }
        });

        mFragmentHelper.changeFragment(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void clickViewPager(MenuItem item) {
        startActivity(new Intent(this, ViewPagerActivity.class));
    }
}
