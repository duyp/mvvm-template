package com.duyp.architecture.mvvm.ui.base;

import android.support.annotation.CallSuper;

import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseRecyclerViewAdapter;
import com.duyp.architecture.mvvm.ui.base.interfaces.PaginationListener;
import com.duyp.architecture.mvvm.ui.base.interfaces.Refreshable;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by duypham on 10/24/17.
 *
 */

@Getter
public abstract class BaseListDataViewModel<T extends RealmObject, A extends BaseRecyclerViewAdapter<T>>
        extends BaseViewModel implements PaginationListener, Refreshable{

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
    public void initAdapter(A adapter) {
        this.adapter = adapter;
        adapter.setItemClickListener(this::onItemClick);
    }

    @Override
    public void refresh() {
        onCallApi(1);
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
        callApi(page, lastPage -> {
            this.lastPage = lastPage;
//            adapter.removeProgress();
        });
        return true;
    }

    protected abstract void callApi(int page, OnCallApiDone onCallApiDone);

    protected abstract void onItemClick(T item);

    public interface OnCallApiDone {
        void onDone(int lastPage);
    }
}
