package com.duyp.architecture.mvvm.ui.modules.repo.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.duyp.androidutils.AlertUtils;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.FilterOptionsModel;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.data.repository.UserReposRepo;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.injection.qualifier.ApplicationContext;
import com.duyp.architecture.mvvm.ui.base.BaseListDataViewModel;

import javax.inject.Inject;

/**
 * Created by duypham on 10/25/17.
 *
 */

public class UserReposViewModel extends BaseListDataViewModel<Repo, RepoAdapter> {

    private final UserReposRepo repo;

    FilterOptionsModel filterOptions = new FilterOptionsModel();

    private final Context context;
    @Inject
    public UserReposViewModel(@ApplicationContext Context context, UserManager userManager, UserReposRepo repo) {
        super(userManager);
        this.repo = repo;
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        String targetUser = null;
        if (bundle != null) {
            targetUser = bundle.getString(BundleConstant.EXTRA);
        }
        repo.initUser(targetUser);
    }

    @Override
    public void initAdapter(RepoAdapter adapter) {
        super.initAdapter(adapter);
        adapter.updateData(repo.getData());
    }

    @Override
    protected void callApi(int page, OnCallApiDone onCallApiDone) {
        execute(repo.getUserRepositories(filterOptions, page), repoPageable -> {
            onCallApiDone.onDone(repoPageable.getLast());
        });
    }

    @Override
    protected void onItemClick(Repo item) {
        AlertUtils.showToastLongMessage(context, "Clicked:" + item.getFullName());
    }
}
