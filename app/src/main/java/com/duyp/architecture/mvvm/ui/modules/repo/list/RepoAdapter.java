package com.duyp.architecture.mvvm.ui.modules.repo.list;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseAdapter;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;

import javax.inject.Inject;

import lombok.Setter;

/**
 * Created by duypham on 10/25/17.
 *
 */

public class RepoAdapter extends BaseAdapter<Repo> {

    @Setter
    private boolean hasAvatar = true;

    @Inject
    public RepoAdapter() {
        super();
    }

    @Override
    protected void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, @NonNull Repo repo) {
        ((ReposViewHolder)viewHolder).bind(repo);
    }

    @Override
    protected RecyclerView.ViewHolder createItemHolder(ViewGroup viewGroup, int i) {
        ReposViewHolder holder = ReposViewHolder.newInstance(viewGroup, false, hasAvatar);
        if (holder.imvAvatar != null) {
            holder.imvAvatar.setOnClickListener(v -> {
                Repo repo = getItem(holder.getAdapterPosition());
                if (repo != null && navigatorHelper != null) {
                    navigatorHelper.navigateUserProfile(repo.getOwner());
                }
            });
        }
        return holder;
    }
}
