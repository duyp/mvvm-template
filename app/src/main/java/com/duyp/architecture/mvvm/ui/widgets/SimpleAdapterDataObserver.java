package com.duyp.architecture.mvvm.ui.widgets;

import android.support.v7.widget.RecyclerView;

/**
 * Created by duypham on 11/2/17.
 *
 */

public abstract class SimpleAdapterDataObserver extends RecyclerView.AdapterDataObserver {

    public abstract void onChanged();

    public void onItemRangeChanged(int positionStart, int itemCount) {
        // do nothing
        onChanged();
    }

    public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
        // fallback to onItemRangeChanged(positionStart, itemCount) if app
        // does not override this method.
        onItemRangeChanged(positionStart, itemCount);
    }

    public void onItemRangeInserted(int positionStart, int itemCount) {
        // do nothing
        onChanged();
    }

    public void onItemRangeRemoved(int positionStart, int itemCount) {
        // do nothing
        onChanged();
    }

    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        // do nothing
        onChanged();
    }
}
