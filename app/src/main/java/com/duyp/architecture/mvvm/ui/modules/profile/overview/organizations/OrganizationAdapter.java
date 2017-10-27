package com.duyp.architecture.mvvm.ui.modules.profile.overview.organizations;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseAdapter;

/**
 * Created by duypham on 10/27/17.
 *
 */

public class OrganizationAdapter extends BaseAdapter<User> {

    @Override
    protected RecyclerView.ViewHolder createItemHolder(ViewGroup viewGroup, int itemType) {
        return OrganizationViewHolder.newInstance(viewGroup);
    }

    @Override
    protected void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, @NonNull User user) {
        ((OrganizationViewHolder)viewHolder).bind(user);
    }

}
