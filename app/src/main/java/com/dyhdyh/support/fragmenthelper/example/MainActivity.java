package com.dyhdyh.support.fragmenthelper.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.dyhdyh.support.fragmenthelper.FragmentLifecycleManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentLifecycleManager.registerFragmentShowCallbacks(new FragmentLifecycleManager.FragmentShowLifecycleCallbacks() {
            @Override
            public void onResumeShowed(Fragment f, boolean lifecycle) {
                Log.d(lifecycle ? "来自生命周期" : "" + "---->", "显示在最前-----> " + this);
            }

            @Override
            public void onPauseShowed(Fragment f, boolean lifecycle) {
                Log.d(lifecycle ? "来自生命周期" : "" + "---->", "处于不显示-----> " + this);
            }

        });
    }

    public void clickFragmentActivity(View view) {
        startActivity(new Intent(this, FragmentExampleActivity.class));
    }

    public void clickViewPagerActivity(View view) {
        startActivity(new Intent(this, ViewPagerExampleActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentLifecycleManager.unregisterFragmentShowCallbacks();
    }
}
