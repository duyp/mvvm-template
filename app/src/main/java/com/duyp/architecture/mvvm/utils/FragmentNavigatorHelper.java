package com.duyp.architecture.mvvm.utils;

import com.duyp.androidutils.navigation.FragmentNavigator;

/**
 * Created by duypham on 10/20/17.
 *
 */

public class FragmentNavigatorHelper extends NavigatorHelper {

    private final FragmentNavigator fragmentNavigator;

    public FragmentNavigatorHelper(FragmentNavigator navigator) {
        super(navigator);
        this.fragmentNavigator = navigator;
    }
}
