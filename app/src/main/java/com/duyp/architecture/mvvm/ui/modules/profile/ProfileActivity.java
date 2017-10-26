package com.duyp.architecture.mvvm.ui.modules.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.duyp.androidutils.glide.GlideUtils;
import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.model.UserDetail;
import com.duyp.architecture.mvvm.databinding.ActivityProfileUserBinding;
import com.duyp.architecture.mvvm.ui.base.activity.BaseViewModelActivity;

import jp.wasabeef.blurry.Blurry;

/**
 * Created by duypham on 10/26/17.
 *
 */

public class ProfileActivity extends BaseViewModelActivity<ActivityProfileUserBinding, ProfileViewModel>{

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
            binding.tvName.setText(user.getDisplayName());
            binding.tvLink.setText(user.getHtmlUrl());
            binding.tvBio.setText(user.getBio());
            GlideUtils.loadImageBitmap(this, user.getAvatarUrl(), bitmap -> {
                binding.imvAvatar.setImageBitmap(bitmap);
                Blurry.with(this).radius(25).from(bitmap).into(binding.imvBackground);
            });
        }
    }
}
