package com.duyp.architecture.mvvm.ui.modules.repo.detail.code.files.paths;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.duyp.architecture.mvvm.data.model.RepoFile;
import com.duyp.architecture.mvvm.injection.qualifier.ActivityContext;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseAdapter;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;

import javax.inject.Inject;

/**
 * Created by duypham on 11/2/17.
 *
 */

public class RepoFilePathsAdapter extends BaseAdapter<RepoFile> {

    @Inject
    public RepoFilePathsAdapter(@ActivityContext Context context, NavigatorHelper navigatorHelper) {
        super(context, navigatorHelper);
    }

    @Override
    protected RecyclerView.ViewHolder createItemHolder(ViewGroup viewGroup, int itemType) {
        return RepoFilePathsViewHolder.newInstance(viewGroup);
    }

    @Override
    protected void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, @NonNull RepoFile repoFile) {
        ((RepoFilePathsViewHolder)viewHolder).bind(repoFile);
    }
}
