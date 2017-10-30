package com.duyp.architecture.mvvm.ui.modules.main;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.duyp.androidutils.AlertUtils;
import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.injection.qualifier.ActivityContext;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;
import com.duyp.architecture.mvvm.ui.widgets.AvatarLayout;
import com.duyp.architecture.mvvm.utils.AvatarLoader;

import javax.inject.Inject;

/**
 * Created by duypham on 10/23/17.
 *
 */

public class MainDrawerHolder {

    private final Context context;
    private final AvatarLoader avatarLoader;
    private final UserManager userManager;
    private final NavigatorHelper navigatorHelper;

    private DrawerLayout drawer;
    private NavigationView accountsNav;
    private NavigationView extrasNav;

    @Inject
    public MainDrawerHolder(@ActivityContext Context context, AvatarLoader avatarLoader, UserManager userManager, NavigatorHelper navigatorHelper) {
        this.avatarLoader = avatarLoader;
        this.userManager = userManager;
        this.navigatorHelper = navigatorHelper;
        this.context = context;
    }

    public void init(DrawerLayout drawerLayout) {
        drawer = drawerLayout;
        accountsNav = drawer.findViewById(R.id.accountsNav);
        extrasNav = drawer.findViewById(R.id.extrasNav);

        extrasNav.getHeaderView(0).setOnClickListener(v -> {
            navigateCurrentUserProfile();
        });
        extrasNav.setNavigationItemSelectedListener(item -> {
            if (item.isChecked()) {
                return false;
            }
            switch (item.getItemId()) {
                case R.id.profile:
                    navigateCurrentUserProfile();
                    break;
                case R.id.logout:
                    userManager.logout();
                    navigatorHelper.navigateLoginActivity(true);
                    break;
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void navigateCurrentUserProfile() {
        if (userManager.getCurrentUser() != null) {
            navigatorHelper.navigateUserProfile(userManager.getCurrentUser());
        } else {
            AlertUtils.showToastShortMessage(context, "Must login first!");
        }
    }

    public void updateUser(User user) {
        View header = extrasNav.getHeaderView(0);
        ((AvatarLayout)header.findViewById(R.id.navAvatarLayout)).bindData(avatarLoader, user);
        ((TextView)header.findViewById(R.id.navFullName)).setText(user.getDisplayName());
        ((TextView)header.findViewById(R.id.navUsername)).setText(user.getLogin());
    }

    public boolean closeDrawer() {
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }
}
