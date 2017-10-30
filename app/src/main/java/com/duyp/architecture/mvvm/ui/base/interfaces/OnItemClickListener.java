package com.duyp.architecture.mvvm.ui.base.interfaces;

import android.view.View;

/**
 * Created by duypham on 10/30/17.
 */

public interface OnItemClickListener<T> {
    void onItemClick(View v, T item);
}
