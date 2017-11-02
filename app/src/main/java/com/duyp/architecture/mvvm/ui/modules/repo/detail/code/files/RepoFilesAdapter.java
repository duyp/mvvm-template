package com.duyp.architecture.mvvm.ui.modules.repo.detail.code.files;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.duyp.androidutils.rx.functions.PlainConsumer;
import com.duyp.architecture.mvvm.data.model.RepoFile;
import com.duyp.architecture.mvvm.injection.qualifier.ActivityContext;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseAdapter;
import com.duyp.architecture.mvvm.ui.base.interfaces.OnItemClickListener;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;

import javax.annotation.Nullable;
import javax.inject.Inject;

import lombok.Setter;

/**
 * Created by duypham on 11/2/17.
 *
 */

public class RepoFilesAdapter extends BaseAdapter<RepoFile> {

    @Setter
    @Nullable
    OnItemClickListener<RepoFile> onMenuClick;

    @Inject
    public RepoFilesAdapter(@ActivityContext Context context, NavigatorHelper navigatorHelper) {
        super(context, navigatorHelper);
    }

    @Override
    protected RecyclerView.ViewHolder createItemHolder(ViewGroup viewGroup, int itemType) {
        RepoFilesViewHolder viewHolder = RepoFilesViewHolder.newInstance(viewGroup);
        viewHolder.menu.setOnClickListener(v -> {
            if (onMenuClick != null) {
                RepoFile repoFile = getItem(viewHolder.getAdapterPosition());
                if (repoFile != null) {
                    onMenuClick.onItemClick(viewHolder.menu, repoFile);
                }
            }
        });
        return viewHolder;
    }

    @Override
    protected void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, @NonNull RepoFile repoFile) {
        ((RepoFilesViewHolder)viewHolder).bind(repoFile);
    }
}
