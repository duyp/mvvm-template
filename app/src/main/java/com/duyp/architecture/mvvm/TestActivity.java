package com.duyp.architecture.mvvm;

import android.support.v4.app.FragmentManager;

import com.duyp.architecture.mvvm.base.activity.BaseActivity;
import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.utils.AvatarLoader;
import com.duyp.architecture.mvvm.utils.qualifier.ActivityFragmentManager;

import javax.inject.Inject;

/**
 * Created by duypham on 10/20/17.
 *
 */

public class TestActivity extends BaseActivity {

    @Inject
    GithubService githubService;

    @Inject
    @ActivityFragmentManager
    FragmentManager fragmentManager;

    @Inject
    AvatarLoader avatarLoader;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }
}
