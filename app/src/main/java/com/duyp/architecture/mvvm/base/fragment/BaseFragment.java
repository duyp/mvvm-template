package com.duyp.architecture.mvvm.base.fragment;

import android.arch.lifecycle.LifecycleFragment;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duyp.architecture.mvvm.utils.AutoClearedValue;

/**
 * Created by duypham on 10/19/17.
 * Base class using data binding. The binding object reference will be removed as soon as the fragment view is destroyed
 * See {@link AutoClearedValue}
 */

public abstract class BaseFragment<B extends ViewDataBinding> extends Fragment {

    B binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        B dataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        this.binding = new AutoClearedValue<B>(this, dataBinding).get();
        return binding.getRoot();
    }

    protected abstract int getLayout();
}
