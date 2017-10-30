package com.duyp.architecture.mvvm.ui.navigator;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.duyp.architecture.mvvm.R;

/**
 * Created by duypham on 10/30/17.
 *
 */

public class BaseFragmentManager {

    protected final FragmentManager fragmentManager;

    public BaseFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }


    public void onShowHideFragment(@NonNull Fragment toShow, @Nullable Fragment toHide) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if(toHide != null) {
            ft.hide(toHide);
        }
        ft.show(toShow)
                .commit();
        if (toHide != null) {
            toHide.onHiddenChanged(true);
        }
        toShow.onHiddenChanged(false);
    }

    public void onAddAndHide(@NonNull Fragment toAdd, @Nullable Fragment toHide) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (toHide != null) {
                ft.hide(toHide);
        }
        ft.add(R.id.container, toAdd, toAdd.getClass().getSimpleName())
                .commit();

        //noinspection ConstantConditions really android?
        if (toHide != null) toHide.onHiddenChanged(true);
        //noinspection ConstantConditions really android?
        if (toAdd != null) toAdd.onHiddenChanged(false);
    }

    public Fragment findFragmentByTag(String tag) {
        return fragmentManager.findFragmentByTag(tag);
    }

}
