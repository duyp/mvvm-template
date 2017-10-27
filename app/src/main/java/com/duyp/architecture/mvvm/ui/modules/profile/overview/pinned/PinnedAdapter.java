package com.duyp.architecture.mvvm.ui.modules.profile.overview.pinned;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.duyp.architecture.mvvm.ui.base.adapter.BaseAdapter;

import java.text.NumberFormat;

import github.GetPinnedReposQuery;

/**
 * Created by duypham on 10/28/17.
 *
 */

public class PinnedAdapter extends BaseAdapter<GetPinnedReposQuery.Node> {

    private final NumberFormat numberFormat = NumberFormat.getNumberInstance();

    @Override
    protected RecyclerView.ViewHolder createItemHolder(ViewGroup viewGroup, int itemType) {
        return PinnedViewHolder.createInstance(viewGroup, numberFormat);
    }

    @Override
    protected void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, @NonNull GetPinnedReposQuery.Node node) {
        ((PinnedViewHolder)viewHolder).bind(node);
    }
}
