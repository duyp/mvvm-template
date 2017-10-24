package com.duyp.architecture.mvvm.ui.modules.feed;

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

public class FeedAdapter extends BaseRecyclerAdapter<Event, FeedViewHolder, BaseViewHolder.OnItemClickListener<Event>>{


    @Override
    protected FeedViewHolder viewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindView(FeedViewHolder holder, int position) {

    }
}
