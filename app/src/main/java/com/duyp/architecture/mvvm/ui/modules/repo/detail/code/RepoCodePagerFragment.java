package com.duyp.architecture.mvvm.ui.modules.repo.detail.code;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.databinding.TabbedViewpagerBinding;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.helper.Bundler;
import com.duyp.architecture.mvvm.injection.Injectable;
import com.duyp.architecture.mvvm.ui.base.fragment.BaseFragment;
import com.duyp.architecture.mvvm.ui.base.interfaces.TabBadgeListener;

import java.util.Locale;

import javax.inject.Inject;

import static com.duyp.architecture.mvvm.ui.modules.repo.detail.code.RepoCodePagerAdapter.TITLES;

/**
 * Created by duypham on 10/30/17.
 *
 */

public class RepoCodePagerFragment extends BaseFragment<TabbedViewpagerBinding> implements Injectable, TabBadgeListener{
    public static final String TAG = RepoCodePagerFragment.class.getSimpleName();

    public static RepoCodePagerFragment newInstance(@NonNull String repoId, @NonNull String login,
                                                    @NonNull String htmlLink, @NonNull String url, @NonNull String defaultBranch) {
        RepoCodePagerFragment view = new RepoCodePagerFragment();
        view.setArguments(Bundler.start()
                .put(BundleConstant.ID, repoId)
                .put(BundleConstant.EXTRA, login)
                .put(BundleConstant.EXTRA_TWO, url)
                .put(BundleConstant.EXTRA_THREE, defaultBranch)
                .put(BundleConstant.EXTRA_FOUR, htmlLink)
                .end());
        return view;
    }

    @Override
    protected int getLayout() {
        return R.layout.tabbed_viewpager;
    }

    @Inject
    RepoCodePagerAdapter adapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter.init(getArguments());
        binding.pager.setAdapter(adapter);
        binding.tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        binding.tabs.setupWithViewPager(binding.pager);

        binding.tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(binding.pager) {
            @Override public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
                onScrollTop(tab.getPosition());
            }
        });
    }

    @Override
    public void onScrollTop(int index) {
//        if (binding.pager == null || binding.pager.getAdapter() == null) return;
//        Fragment fragment = (BaseFragment) binding.pager.getAdapter().instantiateItem(binding.pager, index);
//        if (fragment != null && fragment instanceof BaseFragment) {
//            ((BaseFragment) fragment).onScrollTop(index);
//        }
    }

    @Override
    public void setBadge(int index, int count) {
        if (binding.tabs.getTabCount() > index) {
            TabLayout.Tab tab = binding.tabs.getTabAt(index);
            if (tab != null) {
                if (count > 0) {
                    tab.setText(String.format(Locale.ENGLISH, "%s (%d)", getString(TITLES[index]), count));
                } else {
                    tab.setText(getString(TITLES[index]));
                }
            }
        }
    }
}
