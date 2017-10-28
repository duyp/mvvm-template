package com.duyp.architecture.mvvm.ui.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duyp.androidutils.adapter.BaseHeaderFooterAdapter;
import com.duyp.androidutils.rx.functions.PlainConsumer;
import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.helper.AnimHelper;
import com.duyp.architecture.mvvm.helper.PrefGetter;
import com.duyp.architecture.mvvm.injection.qualifier.ActivityContext;
import com.duyp.architecture.mvvm.injection.qualifier.ApplicationContext;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;

import java.util.List;

import io.realm.RealmResults;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by duypham on 10/26/17.
 *
 */

public abstract class BaseAdapter<T> extends BaseHeaderFooterAdapter {

    @Getter
    @Nullable
    protected List<T> data;

    @Nullable
    @Setter
    protected PlainConsumer<T> itemClickListener;

    private static final String TAG = "adapter";

    @Getter private boolean isProgressAdded = false;
    private int lastKnowingPosition = -1;
    private boolean enableAnimation = PrefGetter.isRVAnimationEnabled();

    private View progressView;

    protected LayoutInflater mInflater;

    @Nullable
    protected NavigatorHelper navigatorHelper;

    public BaseAdapter() {
        setHasStableIds(true);
    }

    public void setData(@Nullable List<T> newData, boolean refresh) {
        if (newData == null) {
            data = null;
            notifyDataSetChanged();
            return;
        }
        if (data == null || data instanceof RealmResults || newData instanceof RealmResults) {
            this.data = newData;
        } else {
            if (refresh) {
                data.clear();
            }
            data.addAll(newData);
        }
        notifyDataSetChanged();
    }

    public void initNavigator(NavigatorHelper navigatorHelper) {
        this.navigatorHelper = navigatorHelper;
    }

    @Override
    protected RecyclerView.ViewHolder createHolder(ViewGroup viewGroup, int itemType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(viewGroup.getContext());
        }
        RecyclerView.ViewHolder holder = createItemHolder(viewGroup, itemType);
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(v -> {
                T item = getItem(holder.getAdapterPosition());
                if (item != null) {
                    itemClickListener.accept(item);
                }
            });
        }
        return holder;
    }

    @Override
    protected void bindHolder(RecyclerView.ViewHolder viewHolder, int position) {
        T item = getItem(position);
        if (item != null) {
            bindItemViewHolder(viewHolder, item);
//            animate(viewHolder, position);
        }
    }

    protected abstract RecyclerView.ViewHolder createItemHolder(ViewGroup viewGroup, int itemType);

    protected abstract void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, @NonNull T t);

    @Override
    public int getItemCountExceptHeaderFooter() {
        return data != null ? data.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    protected T getItem(int adapterPosition) {
        if (data != null) {
            int pos = getRealItemPosition(adapterPosition);
            if (pos >= 0 && pos < data.size()) {
                return data.get(pos);
            }
        }
        return null;
    }

    public void addProgress() {
        Log.d(TAG, "addProgress: ");
        isProgressAdded = true;
        try {
            addFooter(getProgressView());
        } catch (Exception ignored) {}
    }

    public void removeProgress() {
        Log.d(TAG, "removeProgress: ");
        isProgressAdded = false;
        try {
            removeFooter(getProgressView());
        } catch (Exception ignored) {}
    }

    private View getProgressView() {
        if (progressView == null) {
            progressView = mInflater.inflate(R.layout.progress_layout, null);
        }
        return progressView;
    }

    protected void animate(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (isEnableAnimation() && position > lastKnowingPosition) {
            AnimHelper.startBeatsAnimation(holder.itemView);
            lastKnowingPosition = position;
        }
    }

    @SuppressWarnings("WeakerAccess") public boolean isEnableAnimation() {
        return enableAnimation;
    }
}
