package com.duyp.architecture.mvvm.ui.modules.main;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.injection.qualifier.ActivityFragmentManager;
import com.duyp.architecture.mvvm.ui.base.BaseViewModel;
import com.duyp.architecture.mvvm.ui.modules.Issue.IssueFragment;
import com.duyp.architecture.mvvm.ui.modules.feed.FeedFragment;
import com.duyp.architecture.mvvm.ui.modules.pullrequest.PullRequestFragment;
import com.duyp.architecture.mvvm.ui.modules.repo.RepoFragment;
import com.duyp.architecture.mvvm.utils.FragmentUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by duypham on 10/23/17.
 * ViewModel for {@link MainActivity}, deals with fragments manager, bottom navigation
 */

public class MainViewModel extends BaseViewModel implements BottomNavigation.OnMenuItemSelectionListener {

    static final int FEEDS = 0;
    static final int REPO = 1;
    static final int ISSUES = 2;
    static final int PULL_REQUESTS = 3;

    @IntDef({FEEDS, ISSUES, PULL_REQUESTS, REPO})
    @Retention(RetentionPolicy.SOURCE) @interface Tab {}

    @NonNull
    @Getter
    private MutableLiveData<Integer> currentTab = new MutableLiveData<>();

    private final Fragment[] fragments = new Fragment[4];

    @Setter @ActivityFragmentManager
    private FragmentManager fragmentManager;

    @Inject
    public MainViewModel(UserManager userManager) {
        super(userManager);
    }

    @Override
    public void onMenuItemSelect(int id, int position, boolean fromUser) {
        if (currentTab.getValue() == null || currentTab.getValue() != position) {
            currentTab.setValue(position);
            showFragment(position);
        }
    }

    @Override
    public void onMenuItemReselect(int id, int position, boolean fromUser) {
        Log.d(TAG, "onMenuItemReselect: ");
        // in case of activity has been rotated, onItemReselected will be called,
        // but fragment haven't been added
        Fragment f = getFragment(position);
        if (f == null || !f.isAdded()) {
            showFragment(position);
        }
    }

    public void showFragment(@Tab int position) {
        hideAllFragments();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment f = getFragment(position);
        if (f.isAdded()) {
            Log.d(TAG, "show fragment: " + f);
            ft.show(f).commit();
        } else {
            Log.d(TAG, "add fragment: " + f);
            ft.add(R.id.container, f, f.getClass().getSimpleName()).commit();
        }
    }

    private void hideAllFragments() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        for (Fragment f : fragments) {
            if (f != null && f.isVisible()) {
                ft.hide(f);
            }
        }
        ft.commit();
    }

    private Fragment getFragment(@Tab int tab) {
        switch (tab) {
            case FEEDS: return getFeedFragment();
            case REPO: return getRepoFragment();
            case ISSUES: return getIssuesFragment();
            case PULL_REQUESTS: return getPullRequestsFragment();
            default: return getFeedFragment();
        }
    }

    private Fragment getFeedFragment() {
        if (fragments[FEEDS] == null) {
            fragments[FEEDS] = new FeedFragment();
        }
        return fragments[FEEDS];
    }

    private Fragment getRepoFragment() {
        if (fragments[REPO] == null) {
            fragments[REPO] = FragmentUtils.createFragmentInstance(new FeedFragment(), "hungpn");
        }
        return fragments[REPO];
    }

    private Fragment getIssuesFragment() {
        if (fragments[ISSUES] == null) {
            fragments[ISSUES] = new IssueFragment();
        }
        return fragments[ISSUES];
    }

    private Fragment getPullRequestsFragment() {
        if (fragments[PULL_REQUESTS] == null) {
            fragments[PULL_REQUESTS] = new PullRequestFragment();
        }
        return fragments[PULL_REQUESTS];
    }
}
