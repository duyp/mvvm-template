package com.duyp.architecture.mvvm.ui.modules.repo.detail.issues;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.databinding.CenteredTabbedViewpagerBinding;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.helper.Bundler;
import com.duyp.architecture.mvvm.injection.Injectable;
import com.duyp.architecture.mvvm.ui.base.fragment.BaseFragment;

import javax.inject.Inject;

/**
 * Created by duypham on 10/30/17.
 *
 */

public class RepoIssuesPagerFragment extends BaseFragment<CenteredTabbedViewpagerBinding> implements Injectable {

    public static final String TAG = RepoIssuesPagerFragment.class.getSimpleName();

    public static RepoIssuesPagerFragment newInstance(@NonNull String repoId, @NonNull String login) {
        RepoIssuesPagerFragment view = new RepoIssuesPagerFragment();
        view.setArguments(Bundler.start()
                .put(BundleConstant.ID, repoId)
                .put(BundleConstant.EXTRA, login)
                .end());
        return view;
    }

    @Inject
    RepoIssuesPagerAdapter adapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String repoId = getArguments().getString(BundleConstant.ID);
        String login = getArguments().getString(BundleConstant.EXTRA);
        if (login == null || repoId == null) throw new NullPointerException("repoId || login is null???");

        adapter.init(repoId, login);
        binding.pager.setAdapter(adapter);

        binding.tabs.setupWithViewPager(binding.pager);
    }

    @Override
    protected int getLayout() {
        return R.layout.centered_tabbed_viewpager;
    }
}
