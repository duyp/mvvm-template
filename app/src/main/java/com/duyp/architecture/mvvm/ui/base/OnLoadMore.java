package com.duyp.architecture.mvvm.ui.base;

import android.support.annotation.Nullable;

import com.duyp.architecture.mvvm.ui.base.interfaces.PaginationListener;
import com.duyp.architecture.mvvm.ui.widgets.recyclerview.scroll.InfiniteScroll;

import lombok.Getter;
import lombok.Setter;

public class OnLoadMore<P> extends InfiniteScroll {

    private final PaginationListener<P> presenter;

    @Getter @Setter @Nullable
    private P parameter;

    public OnLoadMore(PaginationListener<P> presenter) {
        this(presenter, null);
    }

    public OnLoadMore(PaginationListener<P> presenter, @Nullable P parameter) {
        super();
        this.presenter = presenter;
        this.parameter = parameter;
    }

    @Override public boolean onLoadMore(int page, int totalItemsCount) {
        if (presenter != null) {
            presenter.setPreviousTotal(totalItemsCount);
            return presenter.onCallApi(page + 1, parameter);
        }
        return false;
    }
}