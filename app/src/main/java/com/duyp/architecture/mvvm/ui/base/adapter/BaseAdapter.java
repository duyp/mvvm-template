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
import com.duyp.architecture.mvvm.ui.base.interfaces.OnItemClickListener;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.DestroyFailedException;
import javax.security.auth.Destroyable;

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
    protected OnItemClickListener<T> itemClickListener;

    private static final String TAG = "adapter";

    @Getter private boolean isProgressAdded = false;
    private int lastKnowingPosition = -1;
    private boolean enableAnimation = PrefGetter.isRVAnimationEnabled();

    private View progressView;

    @NonNull protected LayoutInflater mInflater;

    @NonNull protected NavigatorHelper navigatorHelper;

    @NonNull protected final Context mContext;

    public BaseAdapter(@ActivityContext Context context, NavigatorHelper navigatorHelper) {
        setHasStableIds(true);
        this.mContext = context;
        this.navigatorHelper = navigatorHelper;
        this.mInflater = LayoutInflater.from(context);
    }

    public void setData(@Nullable List<T> newData) {
        if (this.data != newData) {
            this.data = newData;
        }
        notifyDataSetChanged();
    }

    @Override
    protected RecyclerView.ViewHolder createHolder(ViewGroup viewGroup, int itemType) {
        RecyclerView.ViewHolder holder = createItemHolder(viewGroup, itemType);
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                T item = getItem(holder.getAdapterPosition());
                if (item != null) {
                    itemClickListener.onItemClick(holder.itemView, item);
                }
            }
        });
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

    /**
     * Add an item to adapter data
     * @param item item to be added
     */
    public void addItem(@NonNull T item) {
        if (data == null) {
            data = new ArrayList<>();
        }
        data.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    /**
     * Remove some items from adapter data
     * @param fromPosition start item should be removed
     * @param toPosition end position of items should be removed
     */
    public void subList(int fromPosition, int toPosition) {
        if (data == null || data.isEmpty()) return;
        data.subList(fromPosition, toPosition).clear();
        notifyItemRangeRemoved(fromPosition, toPosition);
    }

    public void removeItem(int adapterPosition) {
        if (data == null || data.isEmpty()) return;
        int itemPosition = getRealItemPosition(adapterPosition);
        if (itemPosition >=0 && itemPosition < data.size()) {
            data.remove(itemPosition);
            notifyItemRemoved(adapterPosition);
        }
    }

    public void removeLastItem() {
        int pos = getHeaders().size() + getItemCountExceptHeaderFooter();
        removeItem(pos - 1);
    }

    /**
     * Get adapter position of given item
     * @param item
     * @return adapter position (header size + realm item position)
     */
    public int getAdapterPosition(T item) {
        if (data == null) {
            return -1;
        }
        int position = data.indexOf(item);
        if (position >= 0) {
            return getHeaders().size() + position;
        }
        return position;
    }

    /**
     * clear adapter data
     */
    public void clear() {
        data = null;
        notifyDataSetChanged();
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
