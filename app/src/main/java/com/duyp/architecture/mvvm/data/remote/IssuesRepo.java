package com.duyp.architecture.mvvm.data.remote;

import com.duyp.androidutils.realm.LiveRealmResults;
import com.duyp.architecture.mvvm.data.local.daos.IssueDaoImpl;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.Issue;
import com.duyp.architecture.mvvm.data.model.Pageable;
import com.duyp.architecture.mvvm.data.model.type.IssueState;
import com.duyp.architecture.mvvm.data.repository.BaseRepo;
import com.duyp.architecture.mvvm.data.source.Resource;
import com.duyp.architecture.mvvm.helper.RestHelper;

import javax.inject.Inject;

import io.reactivex.Flowable;
import lombok.Getter;

/**
 * Created by duypham on 10/31/17.
 */

public class IssuesRepo extends BaseRepo<Issue, IssueDaoImpl> {

    private String repoName;
    private String login;
    @IssueState private String state;

    @Getter
    private LiveRealmResults<Issue> data;

    private final IssueService issueService;

    @Inject
    public IssuesRepo(UserManager userManager, IssueDaoImpl dao, IssueService service) {
        super(userManager, dao);
        this.issueService = service;
    }

    public LiveRealmResults<Issue> init(String repoName, String login, @IssueState String state) {
        this.repoName = repoName;
        this.login = login;
        this.state = state;
        data = dao.getRepoIssues(repoName, login, state);
        return data;
    }

    public Flowable<Resource<Pageable<Issue>>> getIssues(int page, String sortBy) {
        return RestHelper.createRemoteSourceMapper(issueService.getRepositoryIssues(login, repoName, state, sortBy, page),
                issuePageable -> {
                    dao.saveAllAsync(issuePageable.getItems(), repoName, login);
                });
    }
}
