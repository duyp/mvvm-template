package com.duyp.architecture.mvvm.ui.widgets;

import android.support.annotation.DrawableRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by duypham on 9/20/17.
 *
 */

public abstract class BasePagerAdapterWithIcon extends FragmentStatePagerAdapter {

    public BasePagerAdapterWithIcon(FragmentManager fm) {
        super(fm);
    }

    @DrawableRes
    public abstract int getPageIcon(int position);
}
