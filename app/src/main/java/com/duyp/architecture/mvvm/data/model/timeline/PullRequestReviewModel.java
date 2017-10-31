package com.duyp.architecture.mvvm.data.model.timeline;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.duyp.architecture.mvvm.data.model.ReactionsModel;
import com.duyp.architecture.mvvm.helper.Logger;
import com.duyp.architecture.mvvm.helper.ParseDateFormat;
import java.util.ArrayList;
import java.util.List;

import github.PullRequestTimelineQuery;
import github.type.PullRequestReviewState;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by kosh on 20/08/2017.
 */

@Getter
@Setter
public class PullRequestReviewModel {

    private PullRequestTimelineQuery.AsReviewDismissedEvent reviewDismissedEvent;
    private PullRequestTimelineQuery.AsReviewRequestedEvent reviewRequestedEvent;
    private PullRequestTimelineQuery.AsReviewRequestRemovedEvent reviewRequestRemovedEvent;
    private PullRequestTimelineQuery.Node2 node;
    private List<ReactionsModel> reaction;
    private List<PullRequestReviewModel> comments;
    private String id;
    private String url;
    private PullRequestTimelineQuery.Author2 author;
    private String bodyHTML;
    private String createdAt;
    private PullRequestReviewState state;

    @Nullable public static PullRequestReviewModel build(@NonNull PullRequestTimelineQuery.Node node) {
        PullRequestReviewModel model = new PullRequestReviewModel();
        if (node.asReviewRequestRemovedEvent() != null) {
            model.reviewRequestRemovedEvent = node.asReviewRequestRemovedEvent();
        } else if (node.asReviewDismissedEvent() != null) {
            model.reviewDismissedEvent = node.asReviewDismissedEvent();
        } else if (node.asReviewRequestedEvent() != null) {
            model.reviewRequestedEvent = node.asReviewRequestedEvent();
        } else {
            PullRequestTimelineQuery.AsPullRequestReview pullRequestReview = node.asPullRequestReview();
            if (pullRequestReview != null) {
                model.state = pullRequestReview.state();
                model.url = pullRequestReview.url().toString();
                model.author = pullRequestReview.author();
                model.bodyHTML = pullRequestReview.bodyHTML().toString();
                model.createdAt = ParseDateFormat.getTimeAgo(pullRequestReview.createdAt().toString()).toString();
                model.id = pullRequestReview.id();
                model.url = pullRequestReview.url().toString();
                List<PullRequestTimelineQuery.Edge2> edges = pullRequestReview.comments().edges();
                if (edges != null && !edges.isEmpty()) {
                    List<PullRequestReviewModel> comments = new ArrayList<>();
                    for (PullRequestTimelineQuery.Edge2 edge : edges) {
                        PullRequestTimelineQuery.Node2 node2 = edge.node();
                        if (node2 != null) {
                            PullRequestReviewModel comment = new PullRequestReviewModel();
                            comment.node = node2;
                            comment.reaction = ReactionsModel.getReaction(node2.reactionGroups());
                            comments.add(comment);
                        }
                    }
                    Logger.e(comments.size());
                    model.comments = comments;
                }
            } else {
                return null;
            }
        }
        return model;
    }
}
