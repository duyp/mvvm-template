package com.duyp.architecture.mvvm.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import lombok.Setter;

/**
 * Created by Kosh on 11 Nov 2016, 2:07 PM
 */

public class UsersAdapter extends BaseAdapter<User> {

    @Setter
    private boolean isContributor;

    @Setter
    private boolean isFilter;

    public UsersAdapter() {
        this(false);
    }

    public UsersAdapter(boolean isContributor) {
        this(isContributor, false);
    }

    public UsersAdapter(boolean isContributor, boolean isFilter) {
        this.isContributor = isContributor;
        this.isFilter = isFilter;
    }

    @Override
    protected RecyclerView.ViewHolder createItemHolder(ViewGroup viewGroup, int itemType) {
        return UsersViewHolder.newInstance(viewGroup, isFilter);
    }

    @Override
    protected void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, @NonNull User user) {
        ((UsersViewHolder)viewHolder).bind(user, isContributor);
    }
}
