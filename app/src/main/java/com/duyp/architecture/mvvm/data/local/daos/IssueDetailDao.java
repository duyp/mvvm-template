package com.duyp.architecture.mvvm.data.local.daos;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.duyp.androidutils.realm.BaseRealmDaoImpl;
import com.duyp.androidutils.realm.LiveRealmObject;
import com.duyp.architecture.mvvm.data.model.Issue;
import com.duyp.architecture.mvvm.data.model.IssueDetail;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by duypham on 10/31/17.
 *
 */

public class IssueDetailDao extends BaseRealmDaoImpl<IssueDetail> {

    private final Gson gson;

    @Inject
    public IssueDetailDao(RealmConfiguration realmConfiguration, Gson gson) {
        super(Realm.getInstance(realmConfiguration), IssueDetail.class);
        this.gson = gson;
    }

    @NonNull
    public LiveRealmObject<IssueDetail> getIssueDetail(@NonNull Issue issue) {
        LiveRealmObject<IssueDetail> detail = getById(issue.getId());
        if (detail != null && detail.getData() != null) {
            return detail;
        }
        String s = gson.toJson(issue);
        IssueDetail temp = gson.fromJson(s, IssueDetail.class); // clone
        getRealm().beginTransaction();
        IssueDetail data = getRealm().copyToRealm(temp);
        getRealm().commitTransaction();
        return asLiveData(data);
    }

    @Nullable
    public LiveRealmObject<IssueDetail> getIssueDetail(String owner, String repoName, int issueNumber) {
        IssueDetail detail = query()
                .equalTo("repoName", repoName)
                .equalTo("login", owner)
                .equalTo("number", issueNumber)
                .findFirst();
        if (detail != null) {
            return asLiveData(detail);
        }
        return null;
    }
}
