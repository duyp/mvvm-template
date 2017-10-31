package com.duyp.architecture.mvvm.ui.modules.issue.detail;

import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.duyp.androidutils.realm.LiveRealmObject;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.Issue;
import com.duyp.architecture.mvvm.data.model.IssueDetail;
import com.duyp.architecture.mvvm.data.model.PullsIssuesParser;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.data.remote.RepoService;
import com.duyp.architecture.mvvm.data.repository.IssueDetailRepo;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.ui.base.BaseViewModel;

import javax.inject.Inject;

import lombok.Getter;
import lombok.NonNull;

/**
 * Created by duypham on 10/31/17.
 * 
 */

@Getter
public class IssueDetailViewModel extends BaseViewModel {

    private int issueNumber;
    private String login;
    private String repoId;

    private boolean showToRepoBtn;
    private long commentId;

    @NonNull
    private MutableLiveData<Boolean> onDataReady = new MutableLiveData<>();

    private MutableLiveData<Boolean> collaborator = new MutableLiveData<>();

    @Nullable
    private LiveRealmObject<IssueDetail> data;

    private final IssueDetailRepo issueDetailRepo;
    private final RepoService repoService;

    @Inject
    public IssueDetailViewModel(UserManager userManager, IssueDetailRepo repo, RepoService service) {
        super(userManager);
        this.repoService = service;
        this.issueDetailRepo = repo;
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle bundle) {
        if (bundle == null) {
            throw new IllegalArgumentException("Must pass issue data to use this fragment");
        }
        Issue issue = bundle.getParcelable(BundleConstant.ITEM);
        if (issue != null) {
            issueNumber = issue.getNumber();
            login = issue.getLogin();
            repoId = issue.getRepoName();
            data = issueDetailRepo.initIssue(issue);
            onDataReady.setValue(true);
        } else {
            issueNumber = bundle.getInt(BundleConstant.ID);
            login = bundle.getString(BundleConstant.EXTRA);
            repoId = bundle.getString(BundleConstant.EXTRA_TWO);
            initIssue(login, repoId, issueNumber);
        }

        showToRepoBtn = bundle.getBoolean(BundleConstant.EXTRA_THREE);
        commentId = bundle.getLong(BundleConstant.EXTRA_SIX);

        if (data == null) {
            // not exist in database yet
            refresh(true, 100);
        } else {
            refresh(false, 300);
        }
    }

    private void initIssue(String owner, String repoName, int number) {
        data = issueDetailRepo.initIssue(owner, repoName, number);
        if (data != null) {
            onDataReady.setValue(true);
        }
    }

    public void refresh(boolean showProgress, int delay) {
        new Handler(Looper.myLooper()).postDelayed(() -> {
            execute(showProgress, issueDetailRepo.getIssue(login, repoId, issueNumber), issueDetail -> {
                if (data == null) {
                    initIssue(login, repoId, issueNumber);
                }
            });
            checkCollaborator();
        }, delay);
    }

    public boolean isLock() {
        return getIssueData() != null && getIssueData().isLocked();
    }

    @Nullable
    public IssueDetail getIssueData() {
        if (data == null) {
            return null;
        }
        return data.getData();
    }

    private void checkCollaborator() {
        String currentUser = issueDetailRepo.getCurrentUserLogin();
        if (currentUser == null) {
            collaborator.setValue(false);
        } else {
            execute(false, false, repoService.isCollaborator(login, repoId, currentUser), booleanResponse -> {
                collaborator.setValue(booleanResponse.code() == 204);
            }, errorEntity -> {
                collaborator.setValue(false);
            });
        }
    }

    public void onSubscribeOrMute(boolean mute) {
        if (getIssueData() == null) return;
//        makeRestCall(mute ? RestProvider.getNotificationService(isEnterprise()).subscribe(getIssue().getId(),
//                new NotificationSubscriptionBodyModel(false, true))
//                        : RestProvider.getNotificationService(isEnterprise()).subscribe(getIssue().getId(),
//                new NotificationSubscriptionBodyModel(true, false)),
//                booleanResponse -> {
//                    if (booleanResponse.code() == 204 || booleanResponse.code() == 200) {
//                        sendToView(view -> view.showMessage(R.string.success, R.string.successfully_submitted));
//                    } else {
//                        sendToView(view -> view.showMessage(R.string.error, R.string.network_error));
//                    }
//                });
    }

    public boolean isLocked() {
        return getIssueData() != null && getIssueData().isLocked();
    }

    public boolean isOwner() {
        IssueDetail issueDetail = getIssueData();
        if (issueDetail == null) return false;
        User userModel = issueDetail.getUser();
        String me = issueDetailRepo.getCurrentUserLogin();

        PullsIssuesParser parser = PullsIssuesParser.getForIssue(issueDetail.getHtmlUrl());
        return (userModel != null && userModel.getLogin().equalsIgnoreCase(me))
                || (parser != null && parser.getLogin().equalsIgnoreCase(me));
    }

    public boolean isRepoOwner() {
        String me = issueDetailRepo.getCurrentUserLogin();
        return TextUtils.equals(login, me);
    }

    public boolean isCollaborator() {
        return collaborator.getValue() != null && collaborator.getValue();
    }
}
