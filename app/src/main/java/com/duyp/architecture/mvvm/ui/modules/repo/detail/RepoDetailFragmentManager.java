package com.duyp.architecture.mvvm.ui.modules.repo.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.data.model.RepoDetail;
import com.duyp.architecture.mvvm.helper.AppHelper;
import com.duyp.architecture.mvvm.injection.qualifier.ActivityFragmentManager;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.code.RepoCodePagerFragment;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.issues.RepoIssuesPagerFragment;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.project.RepoProjectsPagerFragment;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.pullrequest.RepoPullRequestPagerFragment;
import com.duyp.architecture.mvvm.ui.navigator.BaseFragmentManager;

import static com.duyp.architecture.mvvm.helper.ActivityHelper.getVisibleFragment;
import static com.duyp.architecture.mvvm.ui.modules.repo.detail.Tab.*;

import javax.inject.Inject;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;
import lombok.Getter;

/**
 * Created by duypham on 10/30/17.
 *
 */

public class RepoDetailFragmentManager extends BaseFragmentManager implements BottomNavigation.OnMenuItemSelectionListener {

    @Getter
    private RepoDetail repoDetail;

    @Inject
    public RepoDetailFragmentManager(@ActivityFragmentManager FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void init(RepoDetail repo) {
        this.repoDetail = repo;
    }

    public void onModuleChanged(@Tab int tab) {
        Fragment currentVisible = getVisibleFragment(fragmentManager);
        RepoCodePagerFragment codePagerView = (RepoCodePagerFragment) findFragmentByTag(RepoCodePagerFragment.TAG);
        RepoIssuesPagerFragment repoIssuesPagerView = (RepoIssuesPagerFragment) findFragmentByTag(RepoIssuesPagerFragment.TAG);
        RepoPullRequestPagerFragment pullRequestPagerView = (RepoPullRequestPagerFragment) findFragmentByTag(RepoPullRequestPagerFragment.TAG);
        RepoProjectsPagerFragment projectsFragmentPager = (RepoProjectsPagerFragment)findFragmentByTag(RepoProjectsPagerFragment.TAG);
        switch (tab) {
            case CODE:
                if (codePagerView == null) {
                    onAddAndHide(RepoCodePagerFragment.newInstance(repoDetail.getName(), repoDetail.getOwner().getLogin(),
                            repoDetail.getHtmlUrl(), repoDetail.getUrl(), repoDetail.getDefaultBranch()), currentVisible);
                } else {
                    onShowHideFragment(codePagerView, currentVisible);
                }
                break;
//            case RepoPagerMvp.ISSUES:
//                if ((!getRepo().isHasIssues())) {
//                    sendToView(view -> view.showMessage(R.string.error, R.string.repo_issues_is_disabled));
//                    break;
//                }
//                if (repoIssuesPagerView == null) {
//                    onAddAndHide(fragmentManager, RepoIssuesPagerFragment.newInstance(repoId(), login()), currentVisible);
//                } else {
//                    onShowHideFragment(fragmentManager, repoIssuesPagerView, currentVisible);
//                }
//                break;
//            case RepoPagerMvp.PULL_REQUEST:
//                if (pullRequestPagerView == null) {
//                    onAddAndHide(fragmentManager, RepoPullRequestPagerFragment.newInstance(repoId(), login()), currentVisible);
//                } else {
//                    onShowHideFragment(fragmentManager, pullRequestPagerView, currentVisible);
//                }
//                break;
//            case RepoPagerMvp.PROJECTS:
//                if (projectsFragmentPager == null) {
//                    onAddAndHide(fragmentManager, RepoProjectsFragmentPager.Companion.newInstance(login(), repoId()), currentVisible);
//                } else {
//                    onShowHideFragment(fragmentManager, projectsFragmentPager, currentVisible);
//                }
//                break;
            default: break;
        }
    }

    @Override
    public void onMenuItemSelect(int id, int position, boolean fromUser) {
        onModuleChanged(position);
    }

    @Override
    public void onMenuItemReselect(int id, int position, boolean v) {}
}
