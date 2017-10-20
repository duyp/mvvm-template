package com.duyp.architecture.mvvm.ui;


import android.os.Bundle;

import com.duyp.androidutils.glide.loader.SimpleGlideLoader;
import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.base.activity.BaseViewModelActivity;
import com.duyp.architecture.mvvm.databinding.ActivityMainBinding;
import com.duyp.architecture.mvvm.utils.NavigatorHelper;

import javax.inject.Inject;

public class MainActivity extends BaseViewModelActivity<ActivityMainBinding, TestViewModel> {

    @Inject
    SimpleGlideLoader glideLoader;

    @Inject
    NavigatorHelper navigatorHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.ensureInUserScope(userLiveData -> {}, () -> {});
        navigatorHelper.replaceTestFragment();
    }


    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }
}