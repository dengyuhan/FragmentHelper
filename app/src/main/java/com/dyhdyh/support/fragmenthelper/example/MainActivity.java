package com.dyhdyh.support.fragmenthelper.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void clickFragmentActivity(View view) {
        startActivity(new Intent(this, FragmentExampleActivity.class));
    }

    public void clickViewPagerActivity(View view) {
        startActivity(new Intent(this, ViewPagerExampleActivity.class));
    }
}
