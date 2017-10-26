package com.duyp.architecture.mvvm.ui.modules.feed;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.model.Comment;
import com.duyp.architecture.mvvm.data.model.Commit;
import com.duyp.architecture.mvvm.data.model.Event;
import com.duyp.architecture.mvvm.data.model.Issue;
import com.duyp.architecture.mvvm.data.model.Label;
import com.duyp.architecture.mvvm.data.model.PayloadModel;
import com.duyp.architecture.mvvm.data.model.PullRequest;
import com.duyp.architecture.mvvm.data.model.Release;
import com.duyp.architecture.mvvm.data.model.TeamsModel;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.data.model.WikiModel;
import com.duyp.architecture.mvvm.data.provider.markdown.MarkDownProvider;
import com.duyp.architecture.mvvm.helper.ParseDateFormat;
import com.duyp.architecture.mvvm.ui.base.adapter.BaseViewHolder;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;
import com.duyp.architecture.mvvm.ui.widgets.AvatarLayout;
import com.duyp.architecture.mvvm.ui.widgets.FontTextView;
import com.duyp.architecture.mvvm.ui.widgets.SpannableBuilder;
import com.duyp.architecture.mvvm.utils.AvatarLoader;

import java.util.List;

import butterknife.BindView;

import static com.duyp.architecture.mvvm.data.model.type.EventType.CommitCommentEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.CreateEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.DeleteEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.DownloadEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.FollowEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.ForkApplyEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.ForkEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.GistEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.GollumEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.IssueCommentEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.IssuesEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.MemberEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.OrgBlockEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.OrganizationEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.ProjectCardEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.ProjectColumnEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.ProjectEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.PublicEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.PullRequestEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.PullRequestReviewCommentEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.PullRequestReviewEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.PushEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.ReleaseEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.RepositoryEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.TeamAddEvent;
import static com.duyp.architecture.mvvm.data.model.type.EventType.WatchEvent;

/**
 * Created by Kosh on 11 Nov 2016, 2:08 PM
 */

public class FeedsViewHolder extends BaseViewHolder<Event> {

    @Nullable @BindView(R.id.avatarLayout)
    ImageView avatar;
    @BindView(R.id.description)
    FontTextView description;
    @BindView(R.id.title)
    FontTextView title;
    @BindView(R.id.date)
    FontTextView date;
    private Resources resources;

    private final AvatarLoader avatarLoader;
    private final NavigatorHelper navigatorHelper;

    public FeedsViewHolder(@NonNull View itemView, NavigatorHelper navigatorHelper) {
        super(itemView);
        this.navigatorHelper = navigatorHelper;
        this.resources = itemView.getResources();
        avatarLoader = new AvatarLoader(itemView.getContext());
    }

    public static View getView(@NonNull ViewGroup viewGroup, boolean noImage) {
        if (noImage) {
            return getView(viewGroup, R.layout.feeds_row_no_image_item);
        } else {
            return getView(viewGroup, R.layout.feeds_row_item);
        }
    }

    @Override public void bind(@NonNull Event eventsModel) {
        appendAvatar(eventsModel);
        SpannableBuilder spannableBuilder = SpannableBuilder.builder();
        appendActor(eventsModel, spannableBuilder);
        description.setMaxLines(2);
        description.setText("");
        description.setVisibility(View.GONE);
        if (eventsModel.getType() != null) {
            String type = eventsModel.getType();
            if (type.equals(WatchEvent)) {
                appendWatch(spannableBuilder, eventsModel);
            } else if (type.equals(CreateEvent)) {
                appendCreateEvent(spannableBuilder, eventsModel);
            } else if (type.equals(CommitCommentEvent)) {
                appendCommitComment(spannableBuilder, eventsModel);
            } else if (type.equals(DownloadEvent)) {
                appendDownloadEvent(spannableBuilder, eventsModel);
            } else if (type.equals(FollowEvent)) {
                appendFollowEvent(spannableBuilder, eventsModel);
            } else if (type.equals(ForkEvent)) {
                appendForkEvent(spannableBuilder, eventsModel);
            } else if (type.equals(GistEvent)) {
                appendGistEvent(spannableBuilder, eventsModel);
            } else if (type.equals(GollumEvent)) {
                appendGollumEvent(spannableBuilder, eventsModel);
            } else if (type.equals(IssueCommentEvent)) {
                appendIssueCommentEvent(spannableBuilder, eventsModel);
            } else if (type.equals(IssuesEvent)) {
                appendIssueEvent(spannableBuilder, eventsModel);
            } else if (type.equals(MemberEvent)) {
                appendMemberEvent(spannableBuilder, eventsModel);
            } else if (type.equals(PublicEvent)) {
                appendPublicEvent(spannableBuilder, eventsModel);
            } else if (type.equals(PullRequestEvent)) {
                appendPullRequestEvent(spannableBuilder, eventsModel);
            } else if (type.equals(PullRequestReviewCommentEvent)) {
                appendPullRequestReviewCommentEvent(spannableBuilder, eventsModel);
            } else if (type.equals(PullRequestReviewEvent)) {
                appendPullRequestReviewCommentEvent(spannableBuilder, eventsModel);
            } else if (type.equals(RepositoryEvent)) {
                appendPublicEvent(spannableBuilder, eventsModel);
            } else if (type.equals(PushEvent)) {
                appendPushEvent(spannableBuilder, eventsModel);
            } else if (type.equals(TeamAddEvent)) {
                appendTeamEvent(spannableBuilder, eventsModel);
            } else if (type.equals(DeleteEvent)) {
                appendDeleteEvent(spannableBuilder, eventsModel);
            } else if (type.equals(ReleaseEvent)) {
                appendReleaseEvent(spannableBuilder, eventsModel);
            } else if (type.equals(ForkApplyEvent)) {
                appendForkApplyEvent(spannableBuilder, eventsModel);
            } else if (type.equals(OrgBlockEvent)) {
                appendOrgBlockEvent(spannableBuilder, eventsModel);
            } else if (type.equals(ProjectCardEvent)) {
                appendProjectCardEvent(spannableBuilder, eventsModel, false);
            } else if (type.equals(ProjectColumnEvent)) {
                appendProjectCardEvent(spannableBuilder, eventsModel, true);
            } else if (type.equals(OrganizationEvent)) {
                appendOrganizationEvent(spannableBuilder, eventsModel);
            } else if (type.equals(ProjectEvent)) {
                appendProjectCardEvent(spannableBuilder, eventsModel, false);
            }
            date.setGravity(Gravity.CENTER);
//            date.setEventsIcon(type.getDrawableRes());
        }
        title.setText(spannableBuilder);
        date.setText(ParseDateFormat.getTimeAgo(eventsModel.getCreatedAt()));
    }

    private void appendOrganizationEvent(SpannableBuilder spannableBuilder, Event eventsModel) {
        spannableBuilder.bold(eventsModel.getPayload().getAction().replaceAll("_", ""))
                .append(" ")
                .append(eventsModel.getPayload().getInvitation() != null ? eventsModel.getPayload().getInvitation().getLogin() + " " : "")
                .append(eventsModel.getPayload().getOrganization().getLogin());
    }

    private void appendProjectCardEvent(SpannableBuilder spannableBuilder, Event eventsModel, boolean isColumn) {
        spannableBuilder.bold(eventsModel.getPayload().getAction())
                .append(" ")
                .append(!isColumn ? "project" : "column")
                .append(" ")
                .append(eventsModel.getRepo().getName());
    }

    private void appendOrgBlockEvent(SpannableBuilder spannableBuilder, Event eventsModel) {
        spannableBuilder.bold(eventsModel.getPayload().getAction())
                .append(" ")
                .append(eventsModel.getPayload().getBlockedUser().getLogin())
                .append(" ")
                .append(eventsModel.getPayload().getOrganization().getLogin());
    }

    private void appendForkApplyEvent(SpannableBuilder spannableBuilder, Event eventsModel) {
        spannableBuilder.bold(eventsModel.getPayload().getHead())
                .append(" ")
                .append(eventsModel.getPayload().getBefore())
                .append(" ")
                .append(eventsModel.getRepo() != null ? "in " + eventsModel.getRepo().getName() : "");
    }

    private void appendReleaseEvent(SpannableBuilder spannableBuilder, Event eventsModel) {
        Release release = eventsModel.getPayload().getRelease();
        spannableBuilder.bold("released")
                .append(" ")
                .append(release.getName())
                .append(" ")
                .append(eventsModel.getRepo().getName());
    }

    private void appendDeleteEvent(SpannableBuilder spannableBuilder, Event eventsModel) {
        spannableBuilder.bold("deleted")
                .append(" ")
                .append(eventsModel.getPayload().getRefType())
                .append(" ")
                .append(eventsModel.getPayload().getRef())
                .append(" ")
                .bold("at")
                .append(" ")
                .append(eventsModel.getRepo().getName());
    }

    private void appendTeamEvent(SpannableBuilder spannableBuilder, Event eventsModel) {
        TeamsModel teamsModel = eventsModel.getPayload().getTeam();
        User user = eventsModel.getPayload().getUser();
        spannableBuilder.bold("added")
                .append(" ")
                .append(user != null ? user.getLogin() : eventsModel.getRepo().getName())
                .append(" ")
                .bold("in")
                .append(" ")
                .append(teamsModel.getName() != null ? teamsModel.getName() : teamsModel.getSlug());
    }

    private void appendPushEvent(SpannableBuilder spannableBuilder, Event eventsModel) {
        String ref = eventsModel.getPayload().getRef();
        if (ref.startsWith("refs/heads/")) {
            ref = ref.substring(11);
        }
        spannableBuilder.bold("pushed to")
                .append(" ")
                .append(ref)
                .append(" ")
                .bold("at")
                .append(" ")
                .append(eventsModel.getRepo() != null ? eventsModel.getRepo().getName() : "null repo");
        final List<Commit> commits = eventsModel.getPayload().getCommits();
        int size = commits != null ? commits.size() : -1;
        SpannableBuilder spanCommits = SpannableBuilder.builder();
        if (size > 0) {
            if (size != 1) spanCommits.append(String.valueOf(eventsModel.getPayload().getSize())).append(" new commits").append("\n");
            else spanCommits.append("1 new commit").append("\n");
            int max = 5;
            int appended = 0;
            for (Commit commit : commits) {
                if (commit == null) continue;
                String sha = commit.getSha();
                if (TextUtils.isEmpty(sha)) continue;
                sha = sha.length() > 7 ? sha.substring(0, 7) : sha;
                spanCommits.url(sha).append(" ")
                        .append(commit.getMessage() != null ? commit.getMessage().replaceAll("\\r?\\n|\\r", " ") : "")
                        .append("\n");
                appended++;
                if (appended == max) break;
            }
        }
        if (spanCommits.length() > 0) {
            int last = spanCommits.length();
            description.setMaxLines(5);
            description.setText(spanCommits.delete(last - 1, last));
            description.setVisibility(View.VISIBLE);
        } else {
            description.setText("");
            description.setMaxLines(2);
            description.setVisibility(View.GONE);
        }
    }

    private void appendPullRequestReviewCommentEvent(SpannableBuilder spannableBuilder, Event eventsModel) {
        PullRequest pullRequest = eventsModel.getPayload().getPullRequest();
        Comment comment = eventsModel.getPayload().getComment();
        spannableBuilder.bold("reviewed")
                .append(" ")
                .bold("pull request")
                .append(" ")
                .bold("in")
                .append(" ")
                .append(eventsModel.getRepo().getName())
                .bold("#")
                .bold(String.valueOf(pullRequest.getNumber()));
        if (comment.getBody() != null) {
            MarkDownProvider.stripMdText(description, comment.getBody().replaceAll("\\r?\\n|\\r", " "));
            description.setVisibility(View.VISIBLE);
        } else {
            description.setText("");
            description.setVisibility(View.GONE);
        }
    }

    private void appendPullRequestEvent(SpannableBuilder spannableBuilder, Event eventsModel) {
        PullRequest issue = eventsModel.getPayload().getPullRequest();
        String action = eventsModel.getPayload().getAction();
        if ("synchronize".equals(action)) {
            action = "updated";
        }
        if (eventsModel.getPayload().getPullRequest().isMerged()) {
            action = "merged";
        }
        spannableBuilder.bold(action)
                .append(" ")
                .bold("pull request")
                .append(" ")
                .append(eventsModel.getRepo().getName())
                .bold("#")
                .bold(String.valueOf(issue.getNumber()));
        if ("opened".equals(action) || "closed".equals(action)) {
            if (issue.getTitle() != null) {
                MarkDownProvider.stripMdText(description, issue.getTitle().replaceAll("\\r?\\n|\\r", " "));
                description.setVisibility(View.VISIBLE);
            } else {
                description.setText("");
                description.setVisibility(View.GONE);
            }
        }
    }

    private void appendPublicEvent(SpannableBuilder spannableBuilder, Event eventsModel) {
        String action = "public";
        if (eventsModel.getPayload() != null && "privatized".equalsIgnoreCase(eventsModel.getPayload().getAction())) {
            action = "private";
        }
        spannableBuilder.append("made")
                .append(" ")
                .append(eventsModel.getRepo().getName())
                .append(" ")
                .append(action);
    }

    private void appendMemberEvent(SpannableBuilder spannableBuilder, Event eventsModel) {
        User user = eventsModel.getPayload().getMember();
        spannableBuilder.bold("added")
                .append(" ")
                .append(user != null ? user.getLogin() + " " : "")
                .append("as a collaborator")
                .append(" ")
                .append("to")
                .append(" ")
                .append(eventsModel.getRepo().getName());
    }

    private void appendIssueEvent(SpannableBuilder spannableBuilder, Event eventsModel) {
        Issue issue = eventsModel.getPayload().getIssue();
        boolean isLabel = "label".equals(eventsModel.getPayload().getAction());
        Label label = isLabel ? issue.getLabels() != null && !issue.getLabels().isEmpty()
                                     ? issue.getLabels().get(issue.getLabels().size() - 1) : null : null;
        spannableBuilder.bold(isLabel && label != null ? ("Labeled " + label.getName()) : eventsModel.getPayload().getAction())
                .append(" ")
                .bold("issue")
                .append(" ")
                .append(eventsModel.getRepo().getName())
                .bold("#")
                .bold(String.valueOf(issue.getNumber()));
        if (issue.getTitle() != null) {
            MarkDownProvider.stripMdText(description, issue.getTitle().replaceAll("\\r?\\n|\\r", " "));
            description.setVisibility(View.VISIBLE);
        } else {
            description.setText("");
            description.setVisibility(View.GONE);
        }
    }

    private void appendIssueCommentEvent(SpannableBuilder spannableBuilder, Event eventsModel) {
        Comment comment = eventsModel.getPayload().getComment();
        Issue issue = eventsModel.getPayload().getIssue();
        spannableBuilder.bold("commented")
                .append(" ")
                .bold("on")
                .append(" ")
                .bold(issue.getPullRequest() != null ? "pull request" : "issue")
                .append(" ")
                .append(eventsModel.getRepo().getName())
                .bold("#")
                .bold(String.valueOf(issue.getNumber()));
        if (comment.getBody() != null) {
            MarkDownProvider.stripMdText(description, comment.getBody().replaceAll("\\r?\\n|\\r", " "));
            description.setVisibility(View.VISIBLE);
        } else {
            description.setText("");
            description.setVisibility(View.GONE);
        }
    }

    private void appendGollumEvent(SpannableBuilder spannableBuilder, Event eventsModel) {
        List<WikiModel> wiki = eventsModel.getPayload().getPages();
        if (wiki != null && !wiki.isEmpty()) {
            for (WikiModel wikiModel : wiki) {
                spannableBuilder.bold(wikiModel.getAction())
                        .append(" ")
                        .append(wikiModel.getPageName())
                        .append(" ");
            }
        } else {
            spannableBuilder.bold(resources.getString(R.string.gollum))
                    .append(" ");
        }
        spannableBuilder
                .append(eventsModel.getRepo().getName());

    }

    private void appendGistEvent(SpannableBuilder spannableBuilder, Event eventsModel) {
        String action = eventsModel.getPayload().getAction();
        action = "create".equals(action) ? "created" : "update".equals(action) ? "updated" : action;
        spannableBuilder.bold(action)
                .append(" ")
                .append(itemView.getResources().getString(R.string.gist))
                .append(" ")
                .append(eventsModel.getPayload().getGist().getGistId());
    }

    private void appendForkEvent(SpannableBuilder spannableBuilder, Event eventsModel) {
        spannableBuilder.bold("forked")
                .append(" ")
                .append(eventsModel.getRepo().getName());
    }

    private void appendFollowEvent(SpannableBuilder spannableBuilder, Event eventsModel) {
        spannableBuilder.bold("started following")
                .append(" ")
                .bold(eventsModel.getPayload().getTarget().getLogin());
    }

    private void appendDownloadEvent(SpannableBuilder spannableBuilder, Event eventsModel) {
        spannableBuilder.bold("uploaded a file")
                .append(" ")
                .append(eventsModel.getPayload().getDownload() != null ? eventsModel.getPayload().getDownload().getName() : "")
                .append(" ")
                .append("to")
                .append(" ")
                .append(eventsModel.getRepo().getName());
    }

    private void appendCreateEvent(SpannableBuilder spannableBuilder, Event eventsModel) {
        PayloadModel payloadModel = eventsModel.getPayload();
        String refType = payloadModel.getRefType();
        spannableBuilder
                .bold("created")
                .append(" ")
                .append(refType)
                .append(" ")
                .append(!"repository".equalsIgnoreCase(refType) ? payloadModel.getRef() + " " : "")
                .bold("at")
                .append(" ")
                .append(eventsModel.getRepo().getName());
        if (payloadModel.getDescription() != null) {
            MarkDownProvider.stripMdText(description, payloadModel.getDescription().replaceAll("\\r?\\n|\\r", " "));
            description.setVisibility(View.VISIBLE);
        } else {
            description.setText("");
            description.setVisibility(View.GONE);
        }
    }

    private void appendWatch(SpannableBuilder spannableBuilder, Event eventsModel) {
        spannableBuilder.bold(resources.getString(R.string.starred).toLowerCase())
                .append(" ")
                .append(eventsModel.getRepo().getName());
    }

    private void appendCommitComment(SpannableBuilder spannableBuilder, Event eventsModel) {
        Comment comment = eventsModel.getPayload().getCommitComment() == null ? eventsModel.getPayload().getComment() : eventsModel.getPayload()
                .getCommitComment();
        String commitId = comment != null && comment.getCommitId() != null && comment.getCommitId().length() > 10 ?
                          comment.getCommitId().substring(0, 10) : null;
        spannableBuilder.bold("commented")
                .append(" ")
                .bold("on")
                .append(" ")
                .bold("commit")
                .append(" ")
                .append(eventsModel.getRepo().getName())
                .url(commitId != null ? "@" + commitId : "");
        if (comment != null && comment.getBody() != null) {
            MarkDownProvider.stripMdText(description, comment.getBody().replaceAll("\\r?\\n|\\r", " "));
            description.setVisibility(View.VISIBLE);
        } else {
            description.setText("");
            description.setVisibility(View.GONE);
        }
    }

    private void appendActor(@NonNull Event eventsModel, SpannableBuilder spannableBuilder) {
        if (eventsModel.getActor() != null) {
            spannableBuilder.append(eventsModel.getActor().getLogin()).append(" ");
        }
    }

    private void appendAvatar(@NonNull Event eventsModel) {
        if (avatar != null) {
//            if (eventsModel.getActor() != null) {
//                avatar.bindData(avatarLoader, eventsModel.getActor());
//            } else {
//                avatar.bindData(null, null);
//            }
            avatarLoader.loadImage(eventsModel.getActor().getAvatarUrl(), avatar);
        }
    }
}