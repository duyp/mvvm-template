package com.duyp.architecture.mvvm.ui.modules.repo.detail.code.commit;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.model.Commit;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.helper.InputHelper;
import com.duyp.architecture.mvvm.helper.ParseDateFormat;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseViewHolder;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;
import com.duyp.architecture.mvvm.ui.widgets.FontTextView;
import com.duyp.architecture.mvvm.ui.widgets.SpannableBuilder;
import com.duyp.architecture.mvvm.utils.AvatarLoader;

import java.util.Date;

import butterknife.BindView;

/**
 * Created by Kosh on 11 Nov 2016, 2:08 PM
 */

public class CommitsViewHolder extends BaseViewHolder<Commit> {

    @BindView(R.id.title) FontTextView title;
    @BindView(R.id.avatarLayout) ImageView avatarLayout;
    @BindView(R.id.details) FontTextView details;
    @BindView(R.id.commentsNo) FontTextView commentsNo;

    private final AvatarLoader avatarLoader;
    private final NavigatorHelper navigatorHelper;

    private CommitsViewHolder(@NonNull View itemView, AvatarLoader loader, NavigatorHelper navigatorHelper) {
        super(itemView);
        this.avatarLoader = loader;
        this.navigatorHelper = navigatorHelper;
    }

    public static CommitsViewHolder newInstance(ViewGroup viewGroup, AvatarLoader avatarLoader, NavigatorHelper navigatorHelper) {
        return new CommitsViewHolder(getView(viewGroup, R.layout.issue_row_item), avatarLoader, navigatorHelper);
    }

    @Override public void bind(@NonNull Commit commit) {
        User user = commit.getUser();
        title.setText(commit.getGitCommit().getMessage());
        if (user != null) {
            String login = user.getLogin();
            String avatar = user.getAvatarUrl();
            Date date = commit.getGitCommit().getAuthor().getDate();
            details.setText(SpannableBuilder.builder()
                    .bold(InputHelper.toNA(login))
                    .append(" ")
                    .append(ParseDateFormat.getTimeAgo(date)));
            avatarLoader.loadImage(avatar, avatarLayout);
            avatarLayout.setOnClickListener(v -> {
                navigatorHelper.navigateUserProfile(user);
            });
//            avatarLayout.setUrl(avatar, login, false, LinkParserHelper
//                    .isEnterprise(commit.getAuthor() != null ? commit.getAuthor().getUrl() : commit.getGitCommit().getAuthor().getHtmlUrl()));
            avatarLayout.setVisibility(View.VISIBLE);
        }
        if (commit.getGitCommit() != null && commit.getGitCommit().getCommentCount() > 0) {
            commentsNo.setText(String.valueOf(commit.getGitCommit() != null ? commit.getGitCommit().getCommentCount() : 0));
            commentsNo.setVisibility(View.VISIBLE);
        } else {
            commentsNo.setVisibility(View.GONE);
        }
    }
}
