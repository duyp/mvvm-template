package com.duyp.architecture.mvvm.ui.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseAdapter;
import com.duyp.architecture.mvvm.ui.base.interfaces.OnItemClickListener;
import com.duyp.architecture.mvvm.ui.base.interfaces.PaginationListener;
import com.duyp.architecture.mvvm.ui.base.interfaces.Refreshable;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import io.realm.RealmResults;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by duypham on 10/24/17.
 *
 */

@Getter
public abstract class BaseListDataViewModel<T, A extends BaseAdapter<T>>
        extends BaseViewModel implements PaginationListener, Refreshable, OnItemClickListener<T> {

    protected List<T> data;

    @Nullable
    protected A adapter;

    @Setter
    private int currentPage;

    @Setter
    private int previousTotal;

    private int lastPage = Integer.MAX_VALUE;

    public BaseListDataViewModel(UserManager userManager) {
        super(userManager);
    }

    @CallSuper
    public void initAdapter(@NonNull A adapter) {
        this.adapter = adapter;
        adapter.setData(data);
        adapter.setItemClickListener(this);
    }

    @CallSuper
    @Override
    public void onCreate(@Nullable Bundle bundle, NavigatorHelper navigatorHelper) {
        super.onCreate(bundle, navigatorHelper);
    }

    /**
     * Call this to fill data to {@link #adapter}
     * @param newData new data
     * @param refresh true if data come from refresh action (call remote api)
     */
    protected void setData(@NonNull List<T> newData, boolean refresh) {
        if (data == null || data instanceof RealmResults || newData instanceof RealmResults) {
            this.data = newData;
        } else {
            if (refresh) {
                data.clear();
            }
            data.addAll(newData);
        }
        if (adapter != null) {
            adapter.setData(data);
        }
    }

    @Override
    public void refresh() {
        onCallApi(1);
    }

    public void refresh(int delay) {
        new Handler(Looper.myLooper()).postDelayed(this::refresh, delay);
    }

    @Override
    public final boolean onCallApi(int page) {
        if (page == 1) {
            lastPage = Integer.MAX_VALUE;
        }
        if (page > lastPage || lastPage == 0) {
            return false;
        }
        currentPage = page;
        callApi(page, (lastPage, isRefresh, response) -> {
            this.lastPage = lastPage;
            setData(response, isRefresh);
        });
        return true;
    }

    protected abstract void callApi(int page, OnCallApiDone<T> onCallApiDone);

    public interface OnCallApiDone<E> {

        /**
         * Called after success response come
         * @param last last page
         * @param isRefresh true if refreshed
         * @param response response data
         */
        void onDone(int last, boolean isRefresh, List<E> response);
    }

    /**
     * IMPORTANCE to clear adapter reference since adapter instance is related to activity / fragment
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (adapter != null) {
            this.data = adapter.getData();
        }
        this.adapter = null;
    }
}
