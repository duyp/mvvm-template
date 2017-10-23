package com.duyp.architecture.mvvm.ui.modules.main;

import android.os.Bundle;
import android.util.Log;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.databinding.ActivityMainBinding;
import com.duyp.architecture.mvvm.helper.TypeFaceHelper;
import com.duyp.architecture.mvvm.ui.base.activity.BaseViewModelActivity;

import javax.inject.Inject;

/**
 * Created by duypham on 10/23/17.
 *
 */

public class MainActivity extends BaseViewModelActivity<ActivityMainBinding, MainViewModel> {

    @Inject
    MainDrawerHolder drawerHolder;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean canBack() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setToolbarIcon(R.drawable.ic_menu);
        drawerHolder.init(binding.drawer);
        binding.bottom.bottomNavigation.setDefaultTypeface(TypeFaceHelper.getTypeface());
    }
}
