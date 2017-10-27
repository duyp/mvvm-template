
package com.duyp.architecture.mvvm.ui.modules.profile.overview.organizations;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseViewHolder;
import com.duyp.architecture.mvvm.ui.widgets.AvatarLayout;
import com.duyp.architecture.mvvm.ui.widgets.FontTextView;
import com.duyp.architecture.mvvm.utils.AvatarLoader;

import butterknife.BindView;

public class OrganizationViewHolder extends BaseViewHolder<User> {

        @BindView(R.id.avatarLayout)
        AvatarLayout avatarLayout;
        @BindView(R.id.name)
        FontTextView name;
        
        private final AvatarLoader avatarLoader;
        
        private OrganizationViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarLoader = new AvatarLoader(itemView.getContext());
        }

        public static OrganizationViewHolder newInstance(@NonNull ViewGroup parent) {
            return new OrganizationViewHolder(getView(parent, R.layout.profile_org_row_item));
        }

        @Override public void bind(@NonNull User user) {
            name.setText(user.getLogin());
            avatarLayout.bindData(avatarLoader, user);
        }
    }