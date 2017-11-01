package com.duyp.architecture.mvvm.ui.modules.search;

import android.os.Bundle;

import com.duyp.androidutils.navigation.Navigator;
import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.ui.base.activity.BaseActivity;

import javax.inject.Inject;

/**
 * Created by duypham on 11/1/17.
 *
 */

public class SearchActivity extends BaseActivity {

    @Inject
    Navigator navigator;

    @Override
    public int getLayout() {
        return R.layout.container_with_toolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            navigator.replaceFragment(R.id.container, new RepoSearchFragment());
        }
        setToolbarTitle("Search Repositories");
    }

    @Override
    protected boolean canBack() {
        return true;
    }
}
