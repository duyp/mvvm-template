package com.duyp.architecture.mvvm.ui.modules.main;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.view.ViewGroup;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.utils.AvatarLoader;

import javax.inject.Inject;

/**
 * Created by duypham on 10/23/17.
 *
 */

public class MainDrawerHolder {

    private final AvatarLoader avatarLoader;
    private final Activity activity;

    private DrawerLayout drawer;
    private ViewGroup accountsNav;
    private ViewGroup extrasNav;

    @Inject
    public MainDrawerHolder(Activity activity, AvatarLoader avatarLoader) {
        this.avatarLoader = avatarLoader;
        this.activity = activity;
    }

    public void init(DrawerLayout drawerLayout) {
        drawer = drawerLayout;
        accountsNav = drawer.findViewById(R.id.accountsNav);
        extrasNav = drawer.findViewById(R.id.extrasNav);
    }
}
