package com.duyp.architecture.mvvm.utils;

import android.content.Intent;

import com.duyp.androidutils.navigation.Navigator;
import com.duyp.architecture.mvvm.ui.modules.login.LoginActivity;

/**
 * Created by duypham on 9/7/17.
 * Util class for navigating common page in application
 */

public class NavigatorHelper {

    private static final String TAG_LOGIN = "TAG_LOGIN";
    private static final String TAG_PROFILE = "TAG_PROFILE";
    private static final String TAG_ALL_REPO = "TAG_ALL_REPO";

    private final Navigator mNavigator;

    public NavigatorHelper(Navigator navigator) {
        this.mNavigator = navigator;
    }

    public void navigateLoginActivity(boolean clearAllPrevious) {
        mNavigator.startActivity(LoginActivity.class, intent -> {
            if (clearAllPrevious) {
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        });
    }

//    public void navigateUserProfile(@IdRes int containerId, @Nullable User user) {
//        ProfileFragment fragment = mNavigator.findFragmentByTag(TAG_PROFILE);
//        if (fragment == null) {
//            fragment = ProfileFragment.newInstance(user);
//        }
//        mNavigator.replaceFragment(containerId, fragment, TAG_PROFILE, null);
//    }
//
//    public void navigateUserProfileActivity(@Nullable User user, View... transitionViews) {
//        mNavigator.startActivityWithTransition(ProfileActivity.class, intent -> {
//            intent.putExtra(Constants.EXTRA_DATA, Parcels.wrap(user));
//        }, false, false, transitionViews);
//    }
//
//    public void navigateLoginFragment(@IdRes int containerId) {
//        LoginFragment fragment = mNavigator.findFragmentByTag(TAG_LOGIN);
//        if (fragment == null) {
//            fragment = new LoginFragment();
//        }
//        mNavigator.replaceFragment(containerId, fragment, TAG_LOGIN, null);
//    }
//
//    public void navigateAllRepositoriesFragment(@IdRes int containerId) {
//        RepositoriesFragment fragment = mNavigator.findFragmentByTag(TAG_ALL_REPO);
//        if (fragment == null) {
//            fragment = new RepositoriesFragment();
//        }
//        mNavigator.replaceFragment(containerId, fragment, TAG_ALL_REPO, null);
//    }
//
//    public void navigateMyRepositoriesFragment(@IdRes int containerId) {
//        mNavigator.replaceFragment(containerId, UserRepositoryFragment.createInstance(null));
//    }
//
//    public void navigateRepositoryDetail(@NonNull Long repoId, View... views) {
//        mNavigator.startActivity(RepositoryDetailActivity.class, intent -> {
//            intent.putExtra(Constants.EXTRA_DATA, repoId);
//        });
//    }
}