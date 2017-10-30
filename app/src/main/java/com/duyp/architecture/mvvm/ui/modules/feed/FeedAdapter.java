package com.duyp.architecture.mvvm.ui.modules.feed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.duyp.architecture.mvvm.data.model.Event;
import com.duyp.architecture.mvvm.injection.qualifier.ActivityContext;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseAdapter;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;

import javax.inject.Inject;

import lombok.Setter;

/**
 * Created by duypham on 10/24/17.
 *
 */

public class FeedAdapter extends BaseAdapter<Event> {

    @Setter
    private boolean hasAvatar = true;

    @Inject
    public FeedAdapter(@ActivityContext Context context, NavigatorHelper navigatorHelper) {
        super(context, navigatorHelper);
    }

    @Override
    protected RecyclerView.ViewHolder createItemHolder(ViewGroup viewGroup, int i) {
        FeedsViewHolder holder = new FeedsViewHolder(FeedsViewHolder.getView(viewGroup, !hasAvatar), navigatorHelper);
        if (holder.avatar != null) {
            holder.avatar.setOnClickListener(v -> {
                Event event = getItem(holder.getAdapterPosition());
                if (event != null) {
                    navigatorHelper.navigateUserProfile(event.getActor());
                }
            });
        }
        return holder;
    }

    @Override
    protected void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, @NonNull Event event) {
        ((FeedsViewHolder)viewHolder).bind(event);
    }
}
