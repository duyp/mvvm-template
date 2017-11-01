package com.duyp.architecture.mvvm.ui.modules.profile;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.androidutils.glide.GlideUtils;
import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.model.UserDetail;
import com.duyp.architecture.mvvm.databinding.ActivityProfileUserBinding;
import com.duyp.architecture.mvvm.helper.AnimHelper;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.helper.Bundler;
import com.duyp.architecture.mvvm.helper.InputHelper;
import com.duyp.architecture.mvvm.ui.base.activity.BaseViewModelActivity;

import javax.inject.Inject;

import jp.wasabeef.blurry.Blurry;

import static com.duyp.architecture.mvvm.ui.modules.profile.ProfilePagerAdapter.Tab.TAB_STARRED;
/**
 * Created by duypham on 10/26/17.
 *
 */

public class ProfileActivity extends BaseViewModelActivity<ActivityProfileUserBinding, ProfileViewModel>{

    @Inject
    ProfilePagerAdapter adapter;

    @Override
    public int getLayout() {
        return R.layout.activity_profile_user;
    }

    @Override
    protected boolean shouldUseLayoutStableFullscreen() {
        return true;
    }

    @Override
    protected boolean canBack() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.getUser().observe(this, this::onUserUpdated);
    }

    public void onUserUpdated(@Nullable UserDetail user) {
        if (user != null) {

            if (binding.viewPager.getAdapter() == null) {
                new Handler(Looper.myLooper()).postDelayed(this::initPager, 500);
            }

            binding.tvName.setText(user.getDisplayName());
            binding.tvLink.setText(user.getHtmlUrl());

            if (InputHelper.isEmpty(user.getBio())) {
                binding.tvBio.setVisibility(View.GONE);
            } else {
                binding.tvBio.setVisibility(View.VISIBLE);
                binding.tvBio.setText(user.getBio());
            }
            GlideUtils.loadImageBitmap(this, user.getAvatarUrl(), bitmap -> {
                binding.imvAvatar.setImageBitmap(bitmap);
                Blurry.with(this).radius(25).from(bitmap).into(binding.imvBackground);
            });
        }
    }

    private void initPager() {
        adapter.initUser(viewModel.getUserLogin());
        binding.viewPager.setAdapter(adapter);
        binding.tab.setupWithViewPager(binding.viewPager);
        viewModel.getStaredCount().observe(this, starredCount -> {
            String title;
            if (starredCount == null || starredCount == 0) {
                title = getString(R.string.starred);
            } else {
                title = getString(R.string.starred_format, starredCount);
            }
            binding.tab.setTitleAt(TAB_STARRED, title);
        });
        binding.viewPager.addOnPageChangeListener(viewModel);
        viewModel.getCurrentTab().observe(this, tab -> {
            if (tab != null && binding.viewPager.getCurrentItem() != tab) {
                binding.viewPager.setCurrentItem(tab);
            }
        });
        AnimHelper.animateVisibility(binding.pagerContent, true);
    }

    public static void startActivity(@NonNull Context context, @NonNull String login, boolean isOrg,
                                     boolean isEnterprise, int index) {
        context.startActivity(createIntent(context, login, isOrg, isEnterprise, index));
    }

    public static Intent createIntent(@NonNull Context context, @NonNull String login) {
        return createIntent(context, login, false);
    }

    public static Intent createIntent(@NonNull Context context, @NonNull String login, boolean isOrg) {
        return createIntent(context, login, isOrg, false, -1);
    }

    public static Intent createIntent(@NonNull Context context, @NonNull String login, boolean isOrg,
                                      boolean isEnterprise, int index) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtras(Bundler.start()
                .put(BundleConstant.EXTRA, login)
                .put(BundleConstant.IS_ENTERPRISE, isEnterprise)
                .put(BundleConstant.EXTRA_TYPE, isOrg)
                .put(BundleConstant.EXTRA_TWO, index)
                .end());
        if (context instanceof Service || context instanceof Application) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        return intent;
    }
}
