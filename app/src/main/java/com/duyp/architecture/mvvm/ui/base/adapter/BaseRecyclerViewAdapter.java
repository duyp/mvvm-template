package com.duyp.architecture.mvvm.ui.base.adapter;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.duyp.androidutils.realm.BaseRealmLiveDataAdapter;
import com.duyp.architecture.mvvm.R;

import io.realm.RealmObject;
import lombok.Getter;

/**
 * Created by duypham on 10/24/17.
 * Base adapter
 */

public abstract class BaseRecyclerViewAdapter<T extends RealmObject> extends BaseRealmLiveDataAdapter<T> {

    @Getter private boolean isProgressAdded = false;

    private View progressView;

//    private final RecyclerView.AdapterDataObserver adapterDataObserver = new RecyclerView.AdapterDataObserver() {
//        @Override
//        public void onChanged() {
//            super.onChanged();
//            // remove progress and unRegister observer immediately
//            removeProgress();
//            unregisterAdapterDataObserver(adapterDataObserver);
//        }
//    };

    public BaseRecyclerViewAdapter(Context context, @NonNull LifecycleOwner owner) {
        super(context, owner);
        this.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
            }
        });
    }

    public void addProgress() {
        isProgressAdded = true;
        addFooter(getProgressView());
//        this.registerAdapterDataObserver(adapterDataObserver);
    }

    public void removeProgress() {
        isProgressAdded = false;
        removeFooter(getProgressView());
    }

    private View getProgressView() {
        if (progressView == null) {
            progressView = mInflater.inflate(R.layout.progress_layout, null);
        }
        return progressView;
    }
}
