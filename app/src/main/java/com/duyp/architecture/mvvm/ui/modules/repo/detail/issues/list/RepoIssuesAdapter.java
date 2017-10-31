package com.duyp.architecture.mvvm.ui.modules.repo.detail.issues.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.duyp.architecture.mvvm.data.model.Issue;
import com.duyp.architecture.mvvm.injection.qualifier.ActivityContext;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseAdapter;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;
import com.duyp.architecture.mvvm.utils.AvatarLoader;

import javax.inject.Inject;

/**
 * Created by duypham on 10/30/17.
 *
 */

public class RepoIssuesAdapter extends BaseAdapter<Issue> {

    private boolean withAvatar = true;
    private boolean showRepoName = false;
    private boolean showState = true;

    private final AvatarLoader avatarLoader;

    @Inject
    public RepoIssuesAdapter(@ActivityContext Context context, NavigatorHelper navigatorHelper, AvatarLoader avatarLoader) {
        super(context, navigatorHelper);
        this.avatarLoader = avatarLoader;
    }

    public void init(boolean withAvatar, boolean showRepoName, boolean showState) {
        this.withAvatar = withAvatar;
        this.showRepoName = showRepoName;
        this.showState = showState;
    }

    @Override
    protected RecyclerView.ViewHolder createItemHolder(ViewGroup viewGroup, int itemType) {
        RepoIssueViewHolder holder = RepoIssueViewHolder.newInstance(viewGroup, avatarLoader, withAvatar, showRepoName, showState);
        if (withAvatar && holder.avatarLayout != null) {
            holder.avatarLayout.setOnClickListener(v -> {
                Issue issue = getItem(holder.getAdapterPosition());
                if (issue != null) {
                    navigatorHelper.navigateUserProfile(issue.getUser());
                }
            });
        }
        return holder;
    }

    @Override
    protected void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, @NonNull Issue issue) {
        ((RepoIssueViewHolder)viewHolder).bind(issue);
    }
}
