package com.duyp.architecture.mvvm.ui.base;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.duyp.architecture.mvvm.ui.base.interfaces.PaginationListener;
import com.duyp.architecture.mvvm.ui.widgets.recyclerview.scroll.InfiniteScroll;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

public class OnLoadMore extends InfiniteScroll {

    @Nullable
    private PaginationListener listener;

    @Inject
    public OnLoadMore() {}

    @Override public boolean onLoadMore(int page, int totalItemsCount) {
        if (listener != null) {
            listener.setPreviousTotal(totalItemsCount);
            return listener.onCallApi(page + 1);
        }
        return false;
    }

    public void init(RecyclerView recyclerView, @Nullable PaginationListener listener) {
        this.listener = listener;
        if (listener != null) {
            this.initialize(listener.getCurrentPage(), listener.getPreviousTotal());
        }
        recyclerView.addOnScrollListener(this);
    }

    public void unRegisterListener(RecyclerView recyclerView) {
        recyclerView.removeOnScrollListener(this);
        this.listener = null;
    }
}