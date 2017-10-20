package com.duyp.architecture.mvvm.ui;

import com.duyp.androidutils.navigation.FragmentNavigator;
import com.duyp.androidutils.navigation.Navigator;
import com.duyp.architecture.mvvm.utils.NavigatorHelper;

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
