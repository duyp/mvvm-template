package com.duyp.architecture.mvvm.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Kosh on 28 Mar 2017, 9:15 PM
 */

@Getter
@Setter
public class ReactionsModel extends RealmObject {

    @PrimaryKey
    public long id;
    public String url;
    public int total_count;
    @SerializedName("+1") public int plusOne;
    @SerializedName("-1") public int minusOne;
    public int laugh;
    public int hooray;
    public int confused;
    public int heart;
    public String content;
    public User user;
    public boolean viewerHasReacted;
    public boolean isCallingApi;

    public ReactionsModel() {}

    @Override public String toString() {
        return "ReactionsModel{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", total_count=" + total_count +
                ", plusOne=" + plusOne +
                ", minusOne=" + minusOne +
                ", laugh=" + laugh +
                ", hooray=" + hooray +
                ", confused=" + confused +
                ", heart=" + heart +
                '}';
    }

//    @NonNull
//    public static List<ReactionsModel> getReactionGroup(@Nullable List<PullRequestTimelineQuery.ReactionGroup> reactions) {
//        List<ReactionsModel> models = new ArrayList<>();
//        if (reactions != null && !reactions.isEmpty()) {
//            for (PullRequestTimelineQuery.ReactionGroup reaction : reactions) {
//                ReactionsModel model = new ReactionsModel();
//                model.setContent(reaction.content().name());
//                model.setViewerHasReacted(reaction.viewerHasReacted());
//                model.setTotal_count(reaction.users().totalCount());
//                models.add(model);
//            }
//        }
//        return models;
//    }
//
//    @NonNull
//    public static List<ReactionsModel> getReaction(@Nullable List<PullRequestTimelineQuery.ReactionGroup1> reactions) {
//        List<ReactionsModel> models = new ArrayList<>();
//        if (reactions != null && !reactions.isEmpty()) {
//            for (PullRequestTimelineQuery.ReactionGroup1 reaction : reactions) {
//                ReactionsModel model = new ReactionsModel();
//                model.setContent(reaction.content().name());
//                model.setViewerHasReacted(reaction.viewerHasReacted());
//                model.setTotal_count(reaction.users().totalCount());
//                models.add(model);
//            }
//        }
//        return models;
//    }
//
//    @NonNull
//    public static List<ReactionsModel> getReaction2(@Nullable List<PullRequestTimelineQuery.ReactionGroup2> reactions) {
//        List<ReactionsModel> models = new ArrayList<>();
//        if (reactions != null && !reactions.isEmpty()) {
//            for (PullRequestTimelineQuery.ReactionGroup2 reaction : reactions) {
//                ReactionsModel model = new ReactionsModel();
//                model.setContent(reaction.content().name());
//                model.setViewerHasReacted(reaction.viewerHasReacted());
//                model.setTotal_count(reaction.users().totalCount());
//                models.add(model);
//            }
//        }
//        return models;
//    }
}
