package com.duyp.architecture.mvvm.ui.base;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseAdapter;
import com.duyp.architecture.mvvm.ui.base.interfaces.PaginationListener;
import com.duyp.architecture.mvvm.ui.base.interfaces.Refreshable;

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
        extends BaseViewModel implements PaginationListener, Refreshable{

    protected A adapter;

    @Setter
    private int currentPage;

    @Setter
    private int previousTotal;

    private int lastPage = Integer.MAX_VALUE;

    private List<T> data;

    public BaseListDataViewModel(UserManager userManager) {
        super(userManager);
    }

    @CallSuper
    public void initAdapter(A adapter) {
        this.adapter = adapter;
        adapter.setItemClickListener(this::onItemClick);
        setData(getStartupLocalData(), true);
    }

    protected void setData(@NonNull List<T> list, boolean refresh) {
        if (data == null || data instanceof RealmResults || list instanceof RealmResults) {
            if (list.size() > 0) { // we should ignore if new list is empty (eg. response from load more event)
                this.data = list;
            }
        } else {
            if (refresh) {
                data.clear();
            }
            data.addAll(list);
        }
        adapter.setData(data);
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
        callApi(page, (lastPage, isRefresh, response) -> {
            this.lastPage = lastPage;
            setData(response, isRefresh);
        });
        return true;
    }

    protected abstract List<T> getStartupLocalData();

    protected abstract void callApi(int page, OnCallApiDone<T> onCallApiDone);

    protected abstract void onItemClick(T item);

    public interface OnCallApiDone<E extends RealmObject> {
        void onDone(int lastPage, boolean isRefresh, List<E> response);
    }
}
