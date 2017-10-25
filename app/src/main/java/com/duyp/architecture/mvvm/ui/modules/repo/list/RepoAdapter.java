package com.duyp.architecture.mvvm.ui.modules.repo.list;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.duyp.androidutils.glide.loader.GlideLoader;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.injection.qualifier.ActivityContext;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseRecyclerViewAdapter;
import com.duyp.architecture.mvvm.ui.widgets.AvatarLayout;
import com.duyp.architecture.mvvm.utils.AvatarLoader;

import javax.inject.Inject;

/**
 * Created by duypham on 10/25/17.
 *
 */

public class RepoAdapter extends BaseRecyclerViewAdapter<Repo> {

    private final GlideLoader glideLoader;

    @Inject
    public RepoAdapter(@ActivityContext Context context, @NonNull LifecycleOwner owner, AvatarLoader avatarLoader) {
        super(context, owner);
        this.glideLoader = avatarLoader;
    }

    @Override
    protected void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, @NonNull Repo repo) {
        ((ReposViewHolder)viewHolder).bind(repo);
    }

    @Override
    protected RecyclerView.ViewHolder createHolder(ViewGroup viewGroup, int i) {
        return ReposViewHolder.newInstance(glideLoader, viewGroup, false, true);
    }
}
