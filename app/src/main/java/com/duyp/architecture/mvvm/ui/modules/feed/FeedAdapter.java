package com.duyp.architecture.mvvm.ui.modules.feed;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.duyp.architecture.mvvm.data.model.Event;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseRecyclerViewAdapter;

/**
 * Created by duypham on 10/24/17.
 *
 */

public class FeedAdapter extends BaseRecyclerViewAdapter<Event>{

    public FeedAdapter(Context context, @NonNull LifecycleOwner owner) {
        super(context, owner);
    }

    @Override
    protected void bindHolder(RecyclerView.ViewHolder viewHolder, @NonNull Event event) {

    }

    @Override
    protected RecyclerView.ViewHolder createHolder(ViewGroup viewGroup, int i) {
        return null;
    }
}
