package com.duyp.architecture.mvvm.ui.widgets.recyclerview;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseViewHolder;

/**
 * Created by kosh on 03/08/2017.
 *
 */

public class ProgressBarViewHolder extends BaseViewHolder {

    private ProgressBarViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public static ProgressBarViewHolder newInstance(ViewGroup viewGroup) {
        return new ProgressBarViewHolder(getView(viewGroup, R.layout.progress_layout));
    }

    @Override public void bind(@NonNull Object o) {}
}
