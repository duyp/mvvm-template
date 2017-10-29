package com.duyp.architecture.mvvm.ui.modules.repo.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.duyp.androidutils.realm.LiveRealmObject;
import com.duyp.androidutils.rx.Rx;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.RealmString;
import com.duyp.architecture.mvvm.data.model.RepoDetail;
import com.duyp.architecture.mvvm.data.repository.RepoDetailRepo;
import com.duyp.architecture.mvvm.data.source.State;
import com.duyp.architecture.mvvm.data.source.Status;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.ui.adapter.TopicsAdapter;
import com.duyp.architecture.mvvm.ui.base.BaseViewModel;

import javax.inject.Inject;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;
import lombok.Getter;

/**
 * Created by duypham on 10/29/17.
 *
 */

public class RepoDetailViewModel extends BaseViewModel implements BottomNavigation.OnMenuItemSelectionListener{

    private final RepoDetailRepo repoDetailRepo;

    @Getter private LiveRealmObject<RepoDetail> data;
    private String owner;
    private String repoName;

    @Getter private MutableLiveData<State> watchStatus = new MutableLiveData<>();
    @Getter private MutableLiveData<State> starStatus = new MutableLiveData<>();
    @Getter private MutableLiveData<State> folkStatus = new MutableLiveData<>();

    @Getter private final TopicsAdapter topicsAdapter;

    @Inject
    public RepoDetailViewModel(UserManager userManager, RepoDetailRepo repo, TopicsAdapter adapter) {
        super(userManager);
        this.repoDetailRepo = repo;
        this.topicsAdapter = adapter;
        watchStatus.setValue(State.NONE);
        starStatus.setValue(State.NONE);
        folkStatus.setValue(State.NONE);
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle bundle) {
        if (bundle == null) {
            throw new IllegalArgumentException("Must pass repo data to use this fragment");
        }
        data = repoDetailRepo.initRepo(bundle.getParcelable(BundleConstant.EXTRA));
        owner = data.getData().getOwner().getLogin();
        repoName = data.getData().getName();
        topicsAdapter.setData(Rx.map(data.getData().getTopics(), RealmString::getValue), false);
        new Handler(Looper.myLooper()).postDelayed(this::refresh, 300);
    }

    public void refresh() {
        execute(false, repoDetailRepo.getRepo(owner, repoName), null);
    }

    @Override
    public void onMenuItemSelect(int i, int i1, boolean b) {

    }

    @Override
    public void onMenuItemReselect(int i, int i1, boolean b) {

    }

    @Nullable
    public RepoDetail getRepoDetail() {
        if (data != null) {
            return data.getData();
        }
        return null;
    }

    public enum State {
        NONE,
        TRUE,
        FALSE
    }
}
