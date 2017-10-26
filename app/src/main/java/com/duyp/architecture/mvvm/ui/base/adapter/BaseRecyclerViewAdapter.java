package com.duyp.architecture.mvvm.ui.base.adapter;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.duyp.androidutils.realm.BaseRealmLiveDataAdapter;
import com.duyp.androidutils.realm.LiveRealmResultPair;
import com.duyp.androidutils.rx.functions.PlainConsumer;
import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.helper.AnimHelper;
import com.duyp.architecture.mvvm.helper.PrefGetter;
import com.duyp.architecture.mvvm.injection.qualifier.ActivityContext;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by duypham on 10/24/17.
 * Base adapter
 */

public abstract class BaseRecyclerViewAdapter<T extends RealmObject> extends BaseRealmLiveDataAdapter<T> {

    private static final String TAG = "adapter";

    @Getter private boolean isProgressAdded = false;
    private int lastKnowingPosition = -1;
    private boolean enableAnimation = PrefGetter.isRVAnimationEnabled();

    private View progressView;

    @Nullable
    @Setter
    protected PlainConsumer<T> itemClickListener;

    public BaseRecyclerViewAdapter(@ActivityContext Context context, @NonNull LifecycleOwner owner) {
        super(context, owner);
        setHasStableIds(false);
    }

    @Override
    protected Observer<LiveRealmResultPair<T>> createObserver() {
        return liveRealmResultPair -> {
            notifyDataSetChanged();
        };
    }

    @Override
    protected RecyclerView.ViewHolder createHolder(ViewGroup viewGroup, int itemType) {
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
    protected void bindHolder(RecyclerView.ViewHolder viewHolder, @NonNull T t) {
        bindItemViewHolder(viewHolder, t);
        animate(viewHolder, viewHolder.getAdapterPosition());
    }

    protected abstract RecyclerView.ViewHolder createItemHolder(ViewGroup viewGroup, int itemType);

    protected abstract void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, @NonNull T t);

    @Override
    public long getItemId(int index) {
        return 0;
    }

    public void addProgress() {
        Log.d(TAG, "addProgress: ");
        isProgressAdded = true;
        addFooter(getProgressView());
    }

    public void removeProgress() {
        Log.d(TAG, "removeProgress: ");
        isProgressAdded = false;
        removeFooter(getProgressView());
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
