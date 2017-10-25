package com.duyp.architecture.mvvm.ui.modules.main;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.injection.qualifier.ActivityFragmentManager;
import com.duyp.architecture.mvvm.ui.base.BaseViewModel;
import com.duyp.architecture.mvvm.ui.modules.Issue.IssueFragment;
import com.duyp.architecture.mvvm.ui.modules.feed.FeedFragment;
import com.duyp.architecture.mvvm.ui.modules.pullrequest.PullRequestFragment;
import com.duyp.architecture.mvvm.ui.modules.repo.RepoFragment;
import com.duyp.architecture.mvvm.utils.FragmentUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by duypham on 10/23/17.
 * ViewModel for {@link MainActivity}, deals with fragments manager, bottom navigation
 */

@Getter
@Setter
public class MainViewModel extends BaseViewModel {

    private int currentTab = 0;

    @Inject
    public MainViewModel(UserManager userManager) {
        super(userManager);
    }
}
