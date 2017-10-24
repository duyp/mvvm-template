package com.duyp.architecture.mvvm.ui.base;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.duyp.androidutils.realm.BaseRealmLiveDataAdapter;
import com.duyp.architecture.mvvm.ui.widgets.recyclerview.BaseViewHolder;

import io.realm.RealmObject;

/**
 * Created by duypham on 10/24/17.
 * Base adapter
 */

public abstract class BaseRecyclerViewAdapter<T extends RealmObject, VH extends BaseViewHolder<T>, L extends BaseViewHolder.OnItemClickListener<T>>
        extends BaseRealmLiveDataAdapter<T, VH>{

    public BaseRecyclerViewAdapter(Context context, @NonNull LifecycleOwner owner) {
        super(context, owner);
    }


}
