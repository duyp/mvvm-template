package com.duyp.architecture.mvvm;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.duyp.androidutils.glide.loader.SimpleGlideLoader;
import com.duyp.architecture.mvvm.base.activity.BaseActivity;
import com.duyp.architecture.mvvm.data.UserManager;
import com.duyp.architecture.mvvm.databinding.ActivityMainBinding;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Inject
    SimpleGlideLoader glideLoader;

    @Inject
    UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }
}
