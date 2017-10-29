package com.duyp.architecture.mvvm.ui.navigator;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.duyp.androidutils.navigation.Navigator;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.ui.modules.login.LoginActivity;
import com.duyp.architecture.mvvm.ui.modules.main.MainActivity;
import com.duyp.architecture.mvvm.ui.modules.profile.ProfileActivity;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.RepoDetailActivity;

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
        if (clearAllPrevious) {
            mNavigator.finishActivity();
        }
    }

    public void navigateMainActivity(boolean clearAllPrevious) {
        mNavigator.startActivity(MainActivity.class, intent -> {
            if (clearAllPrevious) {
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        });
        if (clearAllPrevious) {
            mNavigator.finishActivity();
        }
    }

    public void navigateUserProfile(@Nullable User user) {
        mNavigator.startActivity(ProfileActivity.class, intent -> {
            intent.putExtra(BundleConstant.EXTRA, user);
        });
    }

    public void navigateRepoDetail(@NonNull Repo repo) {
        mNavigator.startActivity(RepoDetailActivity.class, intent -> {
            intent.putExtra(BundleConstant.EXTRA, repo);
        });
    }
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