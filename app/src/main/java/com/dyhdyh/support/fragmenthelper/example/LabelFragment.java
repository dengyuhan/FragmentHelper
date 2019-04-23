package com.dyhdyh.support.fragmenthelper.example;

import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * @author dengyuhan
 * created 2018/7/13 14:26
 */
public class LabelFragment extends BaseFragment {

    public static LabelFragment create(String label) {
        LabelFragment f = new LabelFragment();
        final Bundle bundle = new Bundle();
        bundle.putString("label", label);
        f.setArguments(bundle);
        return f;
    }

    @NonNull
    @Override
    protected String getLabel() {
        return getArguments().getString("label");
    }

    @Override
    public String toString() {
        return getLabel() + " - " + super.toString();
    }
}
