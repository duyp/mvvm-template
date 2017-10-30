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
public abstract class BaseListDataViewModel<T extends RealmObject, A extends BaseAdapter<T>>
        extends BaseViewModel implements PaginationListener, Refreshable, OnItemClickListener<T> {

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
        adapter.setItemClickListener(this);
    }

    @CallSuper
    @Override
    public void onCreate(@Nullable Bundle bundle, NavigatorHelper navigatorHelper) {
        super.onCreate(bundle, navigatorHelper);
    }

    /**
     * Call this to fill data to {@link #adapter}
     * @param list new data list
     * @param refresh true if data come from refresh action (call remote api)
     */
    protected void setData(@NonNull List<T> list, boolean refresh) {
        if (adapter != null) {
            adapter.setData(list, refresh);
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

    public interface OnCallApiDone<E extends RealmObject> {
        void onDone(int last, boolean isRefresh, List<E> response);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.adapter = null;
    }
}
