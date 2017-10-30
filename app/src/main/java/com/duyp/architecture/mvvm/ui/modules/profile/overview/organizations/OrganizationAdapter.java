package com.duyp.architecture.mvvm.ui.modules.profile.overview.organizations;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.injection.qualifier.ActivityContext;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseAdapter;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;

import javax.inject.Inject;

/**
 * Created by duypham on 10/27/17.
 *
 */

public class OrganizationAdapter extends BaseAdapter<User> {

    @Inject
    public OrganizationAdapter(@ActivityContext Context context, NavigatorHelper navigatorHelper) {
        super(context, navigatorHelper);
    }

    @Override
    protected RecyclerView.ViewHolder createItemHolder(ViewGroup viewGroup, int itemType) {
        return OrganizationViewHolder.newInstance(viewGroup);
    }

    @Override
    protected void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, @NonNull User user) {
        ((OrganizationViewHolder)viewHolder).bind(user);
    }

}
