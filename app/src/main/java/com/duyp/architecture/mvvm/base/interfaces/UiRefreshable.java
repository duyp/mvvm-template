package com.duyp.architecture.mvvm.base.interfaces;

/**
 * Created by duypham on 9/10/17.
 * Indicate refreshable ui objects (be able to swipe to refresh), eg. activity, fragment...
 */

public interface UiRefreshable extends Refreshable{
    void doneRefresh();
    void refreshWithUi();
    void refreshWithUi(int delay);
    void setRefreshEnabled(boolean enabled);
}
