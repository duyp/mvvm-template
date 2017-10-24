package com.duyp.architecture.mvvm.ui.modules.feed;

import android.support.annotation.NonNull;
import android.view.View;

import com.duyp.architecture.mvvm.data.model.Event;
import com.duyp.architecture.mvvm.ui.widgets.recyclerview.BaseViewHolder;

final class FeedViewHolder extends BaseViewHolder<Event> {

    protected FeedViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bind(@NonNull Event event) {

    }
}