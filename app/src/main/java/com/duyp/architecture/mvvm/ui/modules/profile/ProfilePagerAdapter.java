package com.duyp.architecture.mvvm.ui.modules.profile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.injection.qualifier.ActivityFragmentManager;
import com.duyp.architecture.mvvm.ui.modules.feed.FeedFragment;
import com.duyp.architecture.mvvm.ui.modules.profile.followers.ProfileFollowersFragment;
import com.duyp.architecture.mvvm.ui.modules.profile.following.ProfileFollowingFragment;
import com.duyp.architecture.mvvm.ui.modules.profile.overview.OverviewFragment;
import com.duyp.architecture.mvvm.ui.modules.profile.starred.StarredFragment;
import com.duyp.architecture.mvvm.ui.modules.repo.list.RepoListFragment;
import com.duyp.architecture.mvvm.ui.widgets.BasePagerAdapterWithIcon;

import javax.inject.Inject;

/**
 * Created by duypham on 10/26/17.
 *
 */

public class ProfilePagerAdapter extends BasePagerAdapterWithIcon{

    public static final int[] ICONS = new int[] {R.drawable.ic_profile, R.drawable.ic_repo, R.drawable.ic_clock,
            R.drawable.ic_star_filled, R.drawable.ic_follower, R.drawable.ic_following};
    public static final String[] TITLES = new String[] {"Overview", "Repositories", "Events", "Starred", "Followers", "Followings"};

    private String user;

    @Inject
    public ProfilePagerAdapter(@ActivityFragmentManager FragmentManager fm) {
        super(fm);
    }

    public void initUser(String userLogin) {
        this.user = userLogin;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new OverviewFragment();
            case 1: return RepoListFragment.newInstance(user, false);
            case 2: return FeedFragment.newInstance(user, false);
            case 3: return StarredFragment.newInstance(user);
            case 4: return ProfileFollowersFragment.newInstance(user);
            case 5: return ProfileFollowingFragment.newInstance(user);
            default: return FeedFragment.newInstance(user, false);
        }
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public int getPageIcon(int position) {
        return ICONS[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
