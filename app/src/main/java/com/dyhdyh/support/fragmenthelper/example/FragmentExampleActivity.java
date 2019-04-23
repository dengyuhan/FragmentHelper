package com.dyhdyh.support.fragmenthelper.example;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import com.dyhdyh.support.fragmenthelper.FragmentHelper;
import com.dyhdyh.support.fragmenthelper.listener.SimpleOnTabSelectedListener;

public class FragmentExampleActivity extends AppCompatActivity {
    TabLayout tabLayout;

    private FragmentHelper mFragmentHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        tabLayout = findViewById(R.id.tab);


        BaseFragment[] fragments = new BaseFragment[]{
                LabelFragment.create("A"), LabelFragment.create("B"), LabelFragment.create("C"), LabelFragment.create("D")
        };
        mFragmentHelper = new FragmentHelper(getSupportFragmentManager(), R.id.fragment_container)
                .setFragments(fragments);


        for (int i = 0; i < fragments.length; i++) {
            final TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(fragments[i].getLabel());
            tabLayout.addTab(tab);
        }

        tabLayout.addOnTabSelectedListener(new SimpleOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mFragmentHelper.changeFragment(tab.getPosition());
            }
        });

        mFragmentHelper.build();
    }

}
