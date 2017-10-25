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

import com.duyp.androidutils.realm.BaseRealmLiveDataAdapter;
import com.duyp.androidutils.realm.LiveRealmResultPair;
import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.helper.AnimHelper;
import com.duyp.architecture.mvvm.helper.PrefGetter;

import io.realm.RealmObject;
import lombok.Getter;

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

    public BaseRecyclerViewAdapter(Context context, @NonNull LifecycleOwner owner) {
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
    protected void bindHolder(RecyclerView.ViewHolder viewHolder, @NonNull T t) {
        bindItemViewHolder(viewHolder, t);
        animate(viewHolder, viewHolder.getAdapterPosition());
    }

    protected abstract void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, @NonNull T t);

    @Override
    public long getItemId(int index) {
        return 0;
    }

    public void addProgress() {
        Log.d(TAG, "addProgress: ");
        isProgressAdded = true;
        addFooter(getProgressView());
//        this.registerAdapterDataObserver(adapterDataObserver);
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


//    private final RecyclerView.AdapterDataObserver adapterDataObserver = new RecyclerView.AdapterDataObserver() {
//        @Override
//        public void onChanged() {
//            super.onChanged();
//            // remove progress and unRegister observer immediately
//            removeProgress();
//            unregisterAdapterDataObserver(adapterDataObserver);
//        }
//    };
}
