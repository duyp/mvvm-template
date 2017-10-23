package com.duyp.architecture.mvvm.ui.modules.main.feed;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.duyp.architecture.mvvm.data.model.Event;
import com.duyp.architecture.mvvm.ui.widgets.recyclerview.BaseRecyclerAdapter;
import com.duyp.architecture.mvvm.ui.widgets.recyclerview.BaseViewHolder;

/**
 * Created by duypham on 10/24/17.
 *
 */

public class FeedAdapter extends BaseRecyclerAdapter<Event, FeedAdapter.FeedViewHolder, BaseViewHolder.OnItemClickListener<Event>>{


    @Override
    protected FeedViewHolder viewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindView(FeedViewHolder holder, int position) {

    }

    final class FeedViewHolder extends BaseViewHolder<Event> {

        protected FeedViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bind(@NonNull Event event) {

        }
    }
}
