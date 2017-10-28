package com.duyp.architecture.mvvm.ui.modules.profile;

import android.support.annotation.IntDef;
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

import static com.duyp.architecture.mvvm.ui.modules.profile.ProfilePagerAdapter.Tab.*;
/**
 * Created by duypham on 10/26/17.
 *
 */

public class ProfilePagerAdapter extends BasePagerAdapterWithIcon{

    @IntDef({TAB_OVERVIEW, TAB_REPO, TAB_FEED, TAB_STARRED, TAB_FOLLOWERS, TAB_FOLLOWING})
    public @interface Tab {
        int TAB_OVERVIEW = 0;
        int TAB_REPO = 1;
        int TAB_FEED = 2;
        int TAB_STARRED = 3;
        int TAB_FOLLOWERS = 4;
        int TAB_FOLLOWING = 5;
    }

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
            case TAB_OVERVIEW: return new OverviewFragment();
            case TAB_REPO: return RepoListFragment.newInstance(user, false);
            case TAB_FEED: return FeedFragment.newInstance(user, false);
            case TAB_STARRED: return StarredFragment.newInstance(user);
            case TAB_FOLLOWERS: return ProfileFollowersFragment.newInstance(user);
            case TAB_FOLLOWING: return ProfileFollowingFragment.newInstance(user);
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
