package com.duyp.architecture.mvvm.ui.modules.repo.detail.code.files.paths;

import android.arch.lifecycle.MutableLiveData;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.annimon.stream.Objects;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.BranchesModel;
import com.duyp.architecture.mvvm.data.model.RepoFile;
import com.duyp.architecture.mvvm.data.source.State;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.helper.InputHelper;
import com.duyp.architecture.mvvm.ui.base.BaseListDataViewModel;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.code.files.paths.RepoFilePathsAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lombok.Getter;

/**
 * Created by duypham on 11/2/17.
 *
 */

@Getter
public class RepoFilePathsViewModel extends BaseListDataViewModel<RepoFile, RepoFilePathsAdapter> {

    private String repoId;
    private String login;
    private String path;
    private String currentBranch;

    private ArrayList<RepoFile> paths = new ArrayList<>();

    private final MutableLiveData<Boolean> onDataReady = new MutableLiveData<>();

    private final MutableLiveData<Object> onRefresh = new MutableLiveData<>();

    @Inject
    public RepoFilePathsViewModel(UserManager userManager) {
        super(userManager);
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle bundle) {
        if (bundle != null) {
            repoId = bundle.getString(BundleConstant.ID);
            login = bundle.getString(BundleConstant.EXTRA);
            path = Objects.toString(bundle.getString(BundleConstant.EXTRA_TWO), "");
            currentBranch = Objects.toString(bundle.getString(BundleConstant.EXTRA_THREE), "master");
            boolean forceAppend = bundle.getBoolean(BundleConstant.EXTRA_FOUR);
            if (InputHelper.isEmpty(repoId) || InputHelper.isEmpty(login)) {
                throw new NullPointerException(String.format("error, repoId(%s) or login(%s) is null", repoId, login));
            }
            if (forceAppend && paths.isEmpty()) {
                List<RepoFile> repoFiles = new ArrayList<>();
                if (!InputHelper.isEmpty(path)) {
                    Uri uri = Uri.parse(path);
                    StringBuilder builder = new StringBuilder();
                    if (uri.getPathSegments() != null && !uri.getPathSegments().isEmpty()) {
                        List<String> pathSegments = uri.getPathSegments();
                        for (int i = 0; i < pathSegments.size(); i++) {
                            String name = pathSegments.get(i);
                            RepoFile file = new RepoFile();
                            if (i == 0) {
                                builder.append(name);
                            } else {
                                builder.append("/").append(name);
                            }
                            file.setPath(builder.toString());
                            file.setName(name);
                            repoFiles.add(file);
                        }
                    }
                    if (!repoFiles.isEmpty()) {
                        paths.addAll(repoFiles);
                        setData(paths, true);
                    }
                }
            }
            onDataReady.setValue(true);
        } else {
            throw new NullPointerException("Bundle is null");
        }
    }

    public void appendPath(@NonNull RepoFile file) {
        if (adapter != null) {
            path = file.getPath();
            adapter.addItem(file);
        }
    }

    public void reversePath() {
        if (adapter != null) {
            adapter.removeLastItem();
            if (adapter.getData() != null && adapter.getData().size() > 0) {
                path = adapter.getData().get(0).getPath();
            } else {
                path = "";
            }
            onRefresh.setValue(new Object());
        }
    }

    @Override
    protected void callApi(int page, OnCallApiDone<RepoFile> onCallApiDone) {
        publishState(State.success(null));
    }

    @Override
    public void onItemClick(View v, RepoFile item){
        if (adapter != null && adapter.getData() != null) {
            path = item.getPath();
            int position = adapter.getAdapterPosition(item);
            if ((position + 1) < adapter.getItemCount()) {
                adapter.subList(position + 1, adapter.getItemCount());
            }
            onRefresh.setValue(new Object());
        }
    }

    public void onHomeClick() {
        if (adapter != null) {
            adapter.clear();
            path = "";
            onRefresh.setValue(new Object());
        }
    }

    public void setCurrentBranch(String branch) {
        if (currentBranch == null || !currentBranch.equals(branch)) {
            currentBranch = branch;
            onRefresh.setValue(new Object());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        onRefresh.setValue(null);

    }
}
