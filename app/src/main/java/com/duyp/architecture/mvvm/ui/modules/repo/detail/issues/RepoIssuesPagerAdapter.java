package com.duyp.architecture.mvvm.ui.modules.repo.detail.issues;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.duyp.architecture.mvvm.data.model.type.IssueState;
import com.duyp.architecture.mvvm.injection.qualifier.ChildFragmentManager;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.issues.list.RepoIssuesFragment;

import javax.annotation.Nonnull;
import javax.inject.Inject;

/**
 * Created by duypham on 10/30/17.
 *
 */

public class RepoIssuesPagerAdapter extends FragmentStatePagerAdapter{

    private String repoName;
    private String login;

    @Inject
    public RepoIssuesPagerAdapter(@ChildFragmentManager FragmentManager fm) {
        super(fm);
    }

    public void init(@Nonnull String repoName, @Nonnull String login) {
        this.repoName = repoName;
        this.login = login;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return RepoIssuesFragment.newInstance(repoName, login, IssueState.OPEN);
        } else {
            return RepoIssuesFragment.newInstance(repoName, login, IssueState.CLOSED);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0 ? "Open" : "Closed";
    }
}