package com.duyp.architecture.mvvm.ui.base.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.ui.base.BaseListDataViewModel;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseRecyclerViewAdapter;
import com.duyp.architecture.mvvm.ui.base.OnLoadMore;
import com.duyp.architecture.mvvm.ui.widgets.StateLayout;
import com.duyp.architecture.mvvm.ui.widgets.recyclerview.DynamicRecyclerView;
import com.duyp.architecture.mvvm.ui.widgets.recyclerview.scroll.RecyclerViewFastScroller;

import javax.inject.Inject;

/**
 * Created by duypham on 10/23/17.
 *
 */

public abstract class BaseRecyclerViewFragment<
        B extends ViewDataBinding,
        VM extends BaseListDataViewModel,
        A extends BaseRecyclerViewAdapter>
        extends BaseViewModelFragment<B, VM> {

    protected SwipeRefreshLayout refreshLayout;
    protected DynamicRecyclerView recyclerView;
    protected StateLayout stateLayout;
    protected RecyclerViewFastScroller fastScroller;

    @Inject
    A adapter;

    @Inject
    OnLoadMore onLoadMore;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        stateLayout = view.findViewById(R.id.stateLayout);
        fastScroller = view.findViewById(R.id.fastScroller);

        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(stateLayout, refreshLayout);

        fastScroller.attachRecyclerView(recyclerView);

        refreshLayout.setColorSchemeResources(R.color.material_amber_700, R.color.material_blue_700,
                R.color.material_purple_700, R.color.material_lime_700);
        refreshLayout.setOnRefreshListener(() -> {
            if (viewModel != null) {
                viewModel.refresh();
            }
            onLoadMore.reset();
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.refresh_recycler_view;
    }

    @Override
    public void setLoading(boolean loading) {
        if (refreshLayout != null && refreshLayout.isRefreshing() != loading) {
            refreshLayout.post(() -> refreshLayout.setRefreshing(loading));
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
}
