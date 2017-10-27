package com.duyp.architecture.mvvm.ui.adapter;

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

/**
 * Created by Kosh on 11 Nov 2016, 2:08 PM
 */

public class UsersViewHolder extends BaseViewHolder<User> {

    @BindView(R.id.avatarLayout)
    AvatarLayout avatar;
    @BindView(R.id.title)
    FontTextView title;
    @BindView(R.id.date)
    FontTextView date;

    private boolean isFilter;

    private final AvatarLoader avatarLoader;

    private UsersViewHolder(@NonNull View itemView, boolean isFilter) {
        super(itemView);
        this.isFilter = isFilter;
        avatarLoader = new AvatarLoader(itemView.getContext());
    }

    public static UsersViewHolder newInstance(@NonNull ViewGroup parent, boolean isFilter) {
        return new UsersViewHolder(getView(parent, isFilter ? R.layout.users_small_row_item : R.layout.feeds_row_item), isFilter);
    }

    @Override public void bind(@NonNull User user) {}

    public void bind(@NonNull User user, boolean isContributor) {
        avatar.bindData(avatarLoader, user);
        title.setText(user.getLogin());
        date.setVisibility(!isContributor ? View.GONE : View.VISIBLE);
        if (isContributor) {
            date.setText(String.format("%s (%s)", date.getResources().getString(R.string.commits), user.getContributions()));
        }
    }
}
