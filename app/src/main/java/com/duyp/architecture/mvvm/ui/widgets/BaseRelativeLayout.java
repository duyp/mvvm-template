package com.duyp.architecture.mvvm.ui.widgets;

import android.content.Context;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;


import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by duy pham on 3/27/17.
 *
 */


public abstract class BaseRelativeLayout extends RelativeLayout {

    Unbinder unbinder;

    public BaseRelativeLayout(Context context) {
        super(context);
        initView(context);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    protected void initView(Context context) {
        if (unbinder != null) {
            unbinder.unbind();
            this.removeAllViews();
        }
        View view = LayoutInflater.from(context).inflate(getLayout(), this);
        unbinder = ButterKnife.bind(this, view);
    }

    protected abstract int getLayout();

    public String getString(@StringRes int res) {
        return getContext().getString(res);
    }
}
