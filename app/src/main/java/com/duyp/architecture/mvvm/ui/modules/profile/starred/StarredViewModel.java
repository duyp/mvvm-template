package com.duyp.architecture.mvvm.ui.modules.profile.starred;

import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.Pageable;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.data.remote.UserRestService;
import com.duyp.architecture.mvvm.ui.base.BaseListDataViewModel;
import com.duyp.architecture.mvvm.ui.modules.profile.ProfileViewModel;
import com.duyp.architecture.mvvm.ui.modules.repo.list.RepoAdapter;
import com.duyp.architecture.mvvm.utils.SafeMutableLiveData;

import javax.inject.Inject;

import io.reactivex.Single;
import lombok.Setter;

/**
 * Created by duypham on 10/28/17.
 *
 */

public class StarredViewModel extends BaseListDataViewModel<Repo, RepoAdapter> {

    private String targetUser;

    private final UserRestService userRestService;

    @Setter
    @Nullable
    private SafeMutableLiveData<Integer> staredCount;

    @Inject
    public StarredViewModel(UserManager userManager, UserRestService userRestService) {
        super(userManager);
        this.userRestService = userRestService;
    }

    public void initProfileViewModel(ProfileViewModel viewModel) {
        this.staredCount = viewModel.getStaredCount();
    }

    @Override
    public void initAdapter(@NonNull RepoAdapter adapter) {
        super.initAdapter(adapter);
        adapter.setHasAvatar(false);
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle bundle) {
        targetUser = userManager.extractUser(bundle);
        new Handler().postDelayed(this::refresh, 100);
    }

    @Override
    protected void callApi(int page, OnCallApiDone<Repo> onCallApiDone) {
        Single<Pageable<Repo>> single;
        if (staredCount != null && (staredCount.getValue() == null || staredCount.getValue() < 0)) {
             single = Single.zip(userRestService.getStarred(targetUser, page),
                    userRestService.getStarredCount(targetUser), (repoPageable, count) -> {
                        if (count != null){
                            staredCount.setValue(count.getLast());
                        }
                        return repoPageable;
                    });
        } else {
            single = userRestService.getStarred(targetUser, page);
        }
        execute(true, single, repoPageable -> {
            onCallApiDone.onDone(repoPageable.getLast(), page == 1, repoPageable.getItems());
        });
    }

    @Override
    protected void onItemClick(Repo item) {
        navigatorHelper.navigateRepoDetail(item);
    }
}
