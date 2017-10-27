package com.duyp.architecture.mvvm.ui.modules.feed;

import com.duyp.architecture.mvvm.data.model.Event;
import com.duyp.architecture.mvvm.databinding.RefreshRecyclerViewBinding;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.ui.base.fragment.BaseRecyclerViewFragment;
import com.duyp.architecture.mvvm.utils.FragmentUtils;

/**
 * Created by duypham on 10/24/17.
 *
 */

public class FeedFragment extends BaseRecyclerViewFragment<RefreshRecyclerViewBinding, Event, FeedAdapter, FeedViewModel> {

    public static FeedFragment newInstance(String user, boolean hasImage) {
        return FragmentUtils.createFragmentInstance(new FeedFragment(), bundle -> {
            bundle.putString(BundleConstant.EXTRA, user);
            bundle.putBoolean(BundleConstant.EXTRA_TWO, hasImage);
        });
    }

    @Override
    protected Class<FeedViewModel> getViewModelClass() {
        return FeedViewModel.class;
    }
}
