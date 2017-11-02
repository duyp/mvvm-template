package com.duyp.architecture.mvvm.ui.base.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.duyp.architecture.mvvm.Constants;
import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.ui.base.BaseListDataViewModel;
import com.duyp.architecture.mvvm.ui.base.OnLoadMore;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseAdapter;
import com.duyp.architecture.mvvm.ui.base.interfaces.Scrollable;
import com.duyp.architecture.mvvm.ui.base.interfaces.TabBadgeListener;
import com.duyp.architecture.mvvm.ui.base.interfaces.UiRefreshable;
import com.duyp.architecture.mvvm.ui.widgets.StateLayout;
import com.duyp.architecture.mvvm.ui.widgets.recyclerview.DynamicRecyclerView;
import com.duyp.architecture.mvvm.ui.widgets.recyclerview.scroll.RecyclerViewFastScroller;

import javax.inject.Inject;

import io.realm.RealmObject;

/**
 * Created by duypham on 10/23/17.
 * Base fragment with {@link SwipeRefreshLayout} and {@link DynamicRecyclerView} and basic configurations on these UI objects
 *
 */

public abstract class BaseRecyclerViewFragment<
        B extends ViewDataBinding,
        T,
        A extends BaseAdapter<T>,
        VM extends BaseListDataViewModel<T, A>>
        extends BaseViewModelFragment<B, VM> implements UiRefreshable, Scrollable{

    protected SwipeRefreshLayout refreshLayout;
    protected DynamicRecyclerView recyclerView;
    protected StateLayout stateLayout;
    protected RecyclerViewFastScroller fastScroller;
    private boolean isRefreshing;

    @Inject
    protected A adapter;

    @Inject
    OnLoadMore onLoadMore;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        stateLayout = view.findViewById(R.id.stateLayout);
        fastScroller = view.findViewById(R.id.fastScroller);

        viewModel.initAdapter(adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(stateLayout, refreshLayout);
        stateLayout.setOnReloadListener(v -> refreshWithUi());

        fastScroller.attachRecyclerView(recyclerView);

        refreshLayout.setColorSchemeResources(R.color.material_amber_700, R.color.material_blue_700,
                R.color.material_purple_700, R.color.material_lime_700);
        refreshLayout.setOnRefreshListener(this::refresh);
    }

    @Override
    protected int getLayout() {
        return R.layout.refresh_recycler_view;
    }

    @Override
    public void setLoading(boolean loading) {
        if (!loading) {
            doneRefresh();
            adapter.removeProgress();
        } else {
            if (!adapter.isProgressAdded()) {
                refreshUi();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        onLoadMore.init(recyclerView, viewModel);
    }

    @Override
    public void onStop() {
        super.onStop();
        onLoadMore.unRegisterListener(recyclerView);
    }

    @Override
    public void scrollTop(boolean animate) {
        if (recyclerView != null) {
            if (animate) {
                recyclerView.smoothScrollToPosition(0);
            } else {
                recyclerView.scrollToPosition(0);
            }
        }
    }

    @Override
    public void refresh() {
        if (!isRefreshing) {
            viewModel.refresh();
            onLoadMore.reset();
            isRefreshing = true;
        }
    }

    @Override
    public void doneRefresh() {
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(false);
        }
        isRefreshing = false;
    }

    @Override
    public void refreshWithUi() {
        refreshWithUi(0);
    }

    @Override
    public void refreshWithUi(int delay) {
        if (refreshLayout != null) {
            refreshLayout.postDelayed(() -> {
                refreshUi();
                refresh();
            }, delay);
        }
    }

    protected void refreshUi() {
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(true);
        }
    }

    protected void setNoDataText(String text) {
        stateLayout.setEmptyText(text);
    }

    public void updateTabCount(int tabIndex, Integer count) {
        int n = count != null ? count : 0;
        Fragment fragment = getParentFragment();
        if (fragment != null && fragment instanceof TabBadgeListener) {
            ((TabBadgeListener) fragment).setBadge(tabIndex, n);
        }
    }

    @Override
    public void setRefreshEnabled(boolean enabled) {
        refreshLayout.setEnabled(enabled);
    }
}
