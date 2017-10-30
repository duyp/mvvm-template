package com.duyp.architecture.mvvm.ui.modules.repo.detail.code;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.injection.qualifier.ActivityContext;
import com.duyp.architecture.mvvm.injection.qualifier.ChildFragmentManager;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.code.prettifier.ViewerFragment;

import javax.inject.Inject;

/**
 * Created by duypham on 10/30/17.
 *
 */

public class RepoCodePagerAdapter extends FragmentStatePagerAdapter {

    private final Context context;
    private String repoId;
    private String login;
    private String url;
    private String htmlUrl;
    private String defaultBranch;

    @Inject
    public RepoCodePagerAdapter(@ActivityContext Context context, @ChildFragmentManager FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    public void init(@NonNull Bundle bundle) {
        repoId = bundle.getString(BundleConstant.ID);
        login = bundle.getString(BundleConstant.EXTRA);
        url = bundle.getString(BundleConstant.EXTRA_TWO);
        htmlUrl = bundle.getString(BundleConstant.EXTRA_FOUR);
        defaultBranch = bundle.getString(BundleConstant.EXTRA_THREE);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ViewerFragment.newInstance(url, htmlUrl, true);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.readme);
            default: return "";
        }
    }
}
