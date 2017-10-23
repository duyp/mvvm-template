package com.duyp.architecture.mvvm.ui.modules.main;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.ui.widgets.AvatarLayout;
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
    private NavigationView accountsNav;
    private NavigationView extrasNav;

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

    public void updateUser(User user) {
        View header = extrasNav.getHeaderView(0);
        ((AvatarLayout)header.findViewById(R.id.navAvatarLayout)).bindData(avatarLoader, user);
        ((TextView)header.findViewById(R.id.navFullName)).setText(user.getDisplayName());
        ((TextView)header.findViewById(R.id.navUsername)).setText(user.getLogin());
    }
}
