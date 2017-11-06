package com.duyp.architecture.mvvm.data.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.duyp.androidutils.realm.LiveRealmObject;
import com.duyp.architecture.mvvm.data.local.daos.IssueDetailDao;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.Issue;
import com.duyp.architecture.mvvm.data.model.IssueDetail;
import com.duyp.architecture.mvvm.data.remote.IssueService;
import com.duyp.architecture.mvvm.data.source.Resource;
import com.duyp.architecture.mvvm.helper.RestHelper;

import javax.inject.Inject;

import io.reactivex.Flowable;
import lombok.Getter;

/**
 * Created by duypham on 10/31/17.
 */

public class IssueDetailRepo extends BaseRepo<IssueDetail, IssueDetailDao> {

    @Getter
    private LiveRealmObject<IssueDetail> data;

    private final IssueService issueService;

    @Inject
    public IssueDetailRepo(UserManager userManager, IssueDetailDao dao, IssueService service) {
        super(userManager, dao);
        this.issueService = service;
    }

    @NonNull
    public LiveRealmObject<IssueDetail> initIssue(@NonNull Issue issue) {
        data = dao.getIssueDetail(issue);
        return data;
    }

    @Nullable
    public LiveRealmObject<IssueDetail> initIssue(String owner, String repoName, int issueNumber) {
        data = dao.getIssueDetail(owner, repoName, issueNumber);
        return data;
    }

    public Flowable<Resource<IssueDetail>> getIssue(String login, String repoName, int issueNumber) {
         return RestHelper.createRemoteSourceMapper(issueService.getIssue(login, repoName, issueNumber), issueDetail -> {
             issueDetail.setLogin(login);
             issueDetail.setRepoName(repoName);
             issueDetail.setNumber(issueNumber);
             dao.addOrUpdate(issueDetail);
         });
    }
}
