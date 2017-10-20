package com.duyp.architecture.mvvm.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.duyp.androidutils.glide.loader.SimpleGlideLoader;
import com.duyp.androidutils.navigation.FragmentNavigator;
import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.base.fragment.BaseViewModelFragment;
import com.duyp.architecture.mvvm.databinding.ActivityMainBinding;
import com.duyp.architecture.mvvm.utils.AvatarLoader;
import com.duyp.architecture.mvvm.utils.NavigatorHelper;
import com.duyp.architecture.mvvm.utils.qualifier.ActivityFragmentManager;
import com.duyp.architecture.mvvm.utils.qualifier.ChildFragmentManager;

import javax.inject.Inject;

/**
 * Created by duypham on 10/20/17.
 *
 */

public class TestFragment extends BaseViewModelFragment<ActivityMainBinding, TestViewModel> {

    @Inject
    SimpleGlideLoader glideLoader;

    @Inject
    AvatarLoader avatarLoader;

    @Inject
    FragmentNavigator fragmentNavigator;

    @Inject
    @ChildFragmentManager
    FragmentManager fragmentManager;

    @Inject
    @ActivityFragmentManager
    FragmentManager activityFragmentManager;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.ensureInUserScope(userLiveData -> {}, () -> {});
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }
}
