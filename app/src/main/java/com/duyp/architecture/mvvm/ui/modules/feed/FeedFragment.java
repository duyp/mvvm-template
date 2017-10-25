package com.duyp.architecture.mvvm.ui.modules.feed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.architecture.mvvm.data.model.Event;
import com.duyp.architecture.mvvm.databinding.RefreshRecyclerViewBinding;
import com.duyp.architecture.mvvm.ui.base.fragment.BaseRecyclerViewFragment;
import com.duyp.architecture.mvvm.ui.navigator.FragmentNavigatorHelper;
import com.duyp.architecture.mvvm.utils.FragmentUtils;

import javax.inject.Inject;

/**
 * Created by duypham on 10/24/17.
 *
 */

public class FeedFragment extends BaseRecyclerViewFragment<RefreshRecyclerViewBinding, Event, FeedAdapter, FeedViewModel> {

    public static FeedFragment newInstance(String user) {
        return FragmentUtils.createFragmentInstance(new FeedFragment(), user);
    }

    @Override
    protected Class<FeedViewModel> getViewModelClass() {
        return FeedViewModel.class;
    }
}
