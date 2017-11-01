package com.duyp.architecture.mvvm.ui.modules.repo.detail.code.release;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.duyp.androidutils.rx.functions.PlainConsumer;
import com.duyp.architecture.mvvm.data.model.Release;
import com.duyp.architecture.mvvm.injection.qualifier.ActivityContext;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseAdapter;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;

import javax.inject.Inject;

import lombok.Setter;

/**
 * Created by duypham on 11/1/17.
 *
 */

public class ReleaseAdapter extends BaseAdapter<Release> {

    @Setter
    PlainConsumer<Release> onDownloadClickListener;

    @Inject
    public ReleaseAdapter(@ActivityContext Context context, NavigatorHelper navigatorHelper) {
        super(context, navigatorHelper);
    }

    @Override
    protected RecyclerView.ViewHolder createItemHolder(ViewGroup viewGroup, int itemType) {
        ReleasesViewHolder holder = ReleasesViewHolder.newInstance(viewGroup);
        holder.download.setOnClickListener(v -> {
            Release release = getItem(holder.getAdapterPosition());
            if (release != null) {
                onDownloadClickListener.accept(release);
            }
        });
        return holder;
    }

    @Override
    protected void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, @NonNull Release release) {
        ((ReleasesViewHolder)viewHolder).bind(release);
    }
}
