package com.duyp.architecture.mvvm.ui.modules.main;

import android.support.annotation.IntDef;

import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.ui.base.BaseViewModel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

/**
 * Created by duypham on 10/23/17.
 *
 */

public class MainViewModel extends BaseViewModel implements BottomNavigation.OnMenuItemSelectionListener {

    static final int FEEDS = 0;
    static final int ISSUES = 1;
    static final int PULL_REQUESTS = 2;
    static final int PROFILE = 3;

    @IntDef({FEEDS, ISSUES, PULL_REQUESTS, PROFILE})
    @Retention(RetentionPolicy.SOURCE) @interface NavigationType {}
    
    @Inject
    public MainViewModel(UserManager userManager) {
        super(userManager);
    }

    @Override
    public void onMenuItemSelect(int id, int position, boolean fromUser) {

    }

    @Override
    public void onMenuItemReselect(int id, int position, boolean fromUser) {

    }
}
