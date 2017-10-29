package com.duyp.architecture.mvvm.ui.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseViewHolder;
import com.duyp.architecture.mvvm.ui.widgets.FontTextView;
import butterknife.BindView;

/**
 * Created by Kosh on 31 Dec 2016, 3:12 PM
 */

public class SimpleViewHolder<O> extends BaseViewHolder<O> {

    @BindView(R.id.title)
    FontTextView title;

    public SimpleViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override public void bind(@NonNull O o) {
        title.setText(o.toString());
    }
}
