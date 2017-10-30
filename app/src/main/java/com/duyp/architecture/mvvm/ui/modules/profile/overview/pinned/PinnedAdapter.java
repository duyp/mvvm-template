package com.duyp.architecture.mvvm.ui.modules.profile.overview.pinned;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.duyp.architecture.mvvm.injection.qualifier.ActivityContext;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseAdapter;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;

import java.text.NumberFormat;

import javax.inject.Inject;

import github.GetPinnedReposQuery;

/**
 * Created by duypham on 10/28/17.
 *
 */

public class PinnedAdapter extends BaseAdapter<GetPinnedReposQuery.Node> {

    private final NumberFormat numberFormat = NumberFormat.getNumberInstance();

    @Inject
    public PinnedAdapter(@ActivityContext Context context, NavigatorHelper navigatorHelper) {
        super(context, navigatorHelper);
    }

    @Override
    protected RecyclerView.ViewHolder createItemHolder(ViewGroup viewGroup, int itemType) {
        return PinnedViewHolder.createInstance(viewGroup, numberFormat);
    }

    @Override
    protected void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, @NonNull GetPinnedReposQuery.Node node) {
        ((PinnedViewHolder)viewHolder).bind(node);
    }
}
