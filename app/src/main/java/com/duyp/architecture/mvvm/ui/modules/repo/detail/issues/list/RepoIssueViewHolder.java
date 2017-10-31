package com.duyp.architecture.mvvm.ui.modules.repo.detail.issues.list;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.model.Issue;
import com.duyp.architecture.mvvm.data.model.Label;
import com.duyp.architecture.mvvm.data.model.PullsIssuesParser;
import com.duyp.architecture.mvvm.data.model.type.IssueState;
import com.duyp.architecture.mvvm.helper.ParseDateFormat;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseViewHolder;
import com.duyp.architecture.mvvm.ui.widgets.AvatarLayout;
import com.duyp.architecture.mvvm.ui.widgets.FontTextView;
import com.duyp.architecture.mvvm.ui.widgets.SpannableBuilder;
import com.duyp.architecture.mvvm.utils.AvatarLoader;

import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by duypham on 10/30/17.
 *
 */

public class RepoIssueViewHolder extends BaseViewHolder<Issue> {

    @BindView(R.id.title) FontTextView title;
    @Nullable @BindView(R.id.avatarLayout) ImageView avatarLayout;
    @BindView(R.id.issue_state) AppCompatImageView issueState;
    @BindView(R.id.details) FontTextView details;
    @BindView(R.id.commentsNo) FontTextView commentsNo;
    @BindView(R.id.labelContainer) LinearLayout labelContainer;

    @BindString(R.string.by) String by;
    @BindDimen(R.dimen.base5)
    int defaultMargin;

    private boolean withAvatar;
    private boolean showRepoName;
    private boolean showState;

    private final AvatarLoader avatarLoader;

    private RepoIssueViewHolder(@NonNull View itemView, AvatarLoader avatarLoader, boolean withAvatar, boolean showRepoName) {
        this(itemView, avatarLoader, withAvatar, showRepoName, false);
    }

    private RepoIssueViewHolder(@NonNull View itemView, AvatarLoader avatarLoader, boolean withAvatar, boolean showRepoName, boolean showState) {
        super(itemView);
        this.withAvatar = withAvatar;
        this.showRepoName = showRepoName;
        this.showState = showState;
        this.avatarLoader = avatarLoader;
    }

    public static RepoIssueViewHolder newInstance(ViewGroup viewGroup, AvatarLoader avatarLoader, boolean withAvatar, boolean showRepoName) {
        return newInstance(viewGroup, avatarLoader, withAvatar, showRepoName, false);
    }

    public static RepoIssueViewHolder newInstance(ViewGroup viewGroup, AvatarLoader avatarLoader, boolean withAvatar, boolean showRepoName, boolean showState) {
        if (withAvatar) {
            return new RepoIssueViewHolder(getView(viewGroup, R.layout.issue_row_item), avatarLoader, true, showRepoName, showState);
        } else {
            return new RepoIssueViewHolder(getView(viewGroup, R.layout.issue_row_item_no_image), avatarLoader,false, showRepoName, showState);
        }
    }

    @Override public void bind(@NonNull Issue issueModel) {
        title.setText(issueModel.getTitle());
        if (issueModel.getState() != null) {
            CharSequence data = ParseDateFormat.getTimeAgo(issueModel.getState().equals(IssueState.OPEN)
                    ? issueModel.getCreatedAt() : issueModel.getClosedAt());
            SpannableBuilder builder = SpannableBuilder.builder();
            if (showRepoName) {
                PullsIssuesParser parser = PullsIssuesParser.getForIssue(issueModel.getHtmlUrl());
                if (parser != null) builder.bold(parser.getLogin())
                        .append("/")
                        .bold(parser.getRepoId())
                        .bold("#")
                        .bold(String.valueOf(issueModel.getNumber())).append(" ")
                        .append(" ");
            }
            if (!showRepoName) {
                if (issueModel.getState().equals(IssueState.CLOSED)) {
                    if (issueModel.getClosedBy() == null) {
                        builder.bold("#")
                                .bold(String.valueOf(issueModel.getNumber())).append(" ")
                                .append(" ");
                    } else {
                        builder.append("#")
                                .append(String.valueOf(issueModel.getNumber())).append(" ")
                                .append(issueModel.getClosedBy().getLogin())
                                .append(" ");
                    }
                } else {
                    builder.bold("#")
                            .bold(String.valueOf(issueModel.getNumber())).append(" ")
                            .append(issueModel.getUser().getLogin())
                            .append(" ");
                }
            }
            details.setText(builder
                    .append(issueModel.getState())
                    .append(" ")
                    .append(data));
            if (issueModel.getComments() > 0) {
                commentsNo.setText(String.valueOf(issueModel.getComments()));
                commentsNo.setVisibility(View.VISIBLE);
            } else {
                commentsNo.setVisibility(View.GONE);
            }
        }
        if (showState) {
            issueState.setVisibility(View.VISIBLE);
            issueState.setImageResource(issueModel.getState().equals(IssueState.OPEN) ?
                    R.drawable.ic_issue_opened_small : R.drawable.ic_issue_closed_small);
        } else {
            issueState.setVisibility(View.GONE);
        }
        if (withAvatar && avatarLayout != null && issueModel.getUser() != null) {
            avatarLoader.loadImage(issueModel.getUser().getAvatarUrl(), avatarLayout);
            avatarLayout.setVisibility(View.VISIBLE);
        }

        labelContainer.removeAllViews();
        labelContainer.setVisibility(issueModel.getLabels().isEmpty() ? View.GONE : View.VISIBLE);
        Context context = labelContainer.getContext();
        for (Label label : issueModel.getLabels()) {
            TextView textView = new TextView(context);
            textView.setText(label.getName());
            textView.setTextColor(Color.WHITE);
            textView.setBackgroundColor(Color.parseColor("#" + label.getColor()));
            textView.setPadding(defaultMargin, defaultMargin, defaultMargin, defaultMargin);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            );

            labelContainer.addView(textView, params);
        }
    }
}
