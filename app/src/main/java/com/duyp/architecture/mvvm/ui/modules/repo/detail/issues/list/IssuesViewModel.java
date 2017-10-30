package com.duyp.architecture.mvvm.ui.modules.repo.detail.issues.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.Issue;
import com.duyp.architecture.mvvm.data.model.type.IssueState;
import com.duyp.architecture.mvvm.data.remote.IssuesRepo;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.ui.base.BaseListDataViewModel;

import javax.inject.Inject;

/**
 * Created by duypham on 10/30/17.
 *
 */

public class IssuesViewModel extends BaseListDataViewModel<Issue, IssuesAdapter> {

    private String repoName;
    private String login;
    @IssueState private String issueState;
    private boolean isLastUpdated = false;

    private final IssuesRepo issuesRepo;

    @Inject
    public IssuesViewModel(UserManager userManager, IssuesRepo issuesRepo) {
        super(userManager);
        this.issuesRepo = issuesRepo;
    }

    @Override
    public void initAdapter(@NonNull IssuesAdapter adapter) {
        super.initAdapter(adapter);
        adapter.setData(issuesRepo.getData().getData(), true);
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle bundle) {
        if (bundle != null) {
            repoName = bundle.getString(BundleConstant.ID);
            login = bundle.getString(BundleConstant.EXTRA);
            this.issueState = bundle.getString(BundleConstant.EXTRA_TWO);
            issuesRepo.init(repoName, login, issueState);
            refresh(100);
        }
    }

    @Override
    protected void callApi(int page, OnCallApiDone<Issue> onCallApiDone) {
        execute(true, issuesRepo.getIssues(page, getSortBy()), issuePageable -> {
            onCallApiDone.onDone(issuePageable.getLast(), page == 1, issuePageable.getItems());
        });
    }

    @Override
    public void onItemClick(View v, Issue item) {

    }

    public String getSortBy() {
        if (isLastUpdated) {
            return "updated";
        }
        return "created";
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        issuesRepo.onDestroy();
    }
}
