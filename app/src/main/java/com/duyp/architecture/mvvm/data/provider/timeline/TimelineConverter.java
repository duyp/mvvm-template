package com.duyp.architecture.mvvm.data.provider.timeline;

import com.duyp.architecture.mvvm.App;
import com.duyp.architecture.mvvm.data.model.Comment;
import com.duyp.architecture.mvvm.data.model.TimelineModel;
import com.duyp.architecture.mvvm.data.model.timeline.GenericEvent;
import com.duyp.architecture.mvvm.data.model.timeline.PullRequestCommitModel;
import com.duyp.architecture.mvvm.data.model.type.IssueEventType;
import com.duyp.architecture.mvvm.helper.InputHelper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duypham on 10/31/17.
 *
 */

public class TimelineConverter {

    public static List<TimelineModel> convert(List<JsonObject> jsonObjects) {
        if (jsonObjects == null) return null;
        List<TimelineModel> models = new ArrayList<>();
        Gson gson = App.getInstance().getGson();
        for (JsonObject object : jsonObjects) {
            String event = object.get("event").getAsString();
            TimelineModel timelineModel = new TimelineModel();
            if (!InputHelper.isEmpty(event)) {
                IssueEventType type = IssueEventType.getType(event);
                timelineModel.setEvent(type);
                if (type != null) {
                    if (type == IssueEventType.commented) {
                        timelineModel.setComment(getComment(object, gson));
                    } else {
                        timelineModel.setGenericEvent(getGenericEvent(object, gson));
                    }
                }
            } else {
                timelineModel.setGenericEvent(getGenericEvent(object, gson));
            }
            if (filterEvents(timelineModel.getEvent())) {
                models.add(timelineModel);
            }
        }
        return models;
    }

    private static PullRequestCommitModel getCommit(JsonObject jsonObject, Gson gson){
        return gson.fromJson(jsonObject, PullRequestCommitModel.class);
    }

    private static GenericEvent getGenericEvent(JsonObject jsonObject, Gson gson) {
        return gson.fromJson(jsonObject, GenericEvent.class);
    }

    private static Comment getComment(JsonObject jsonObject, Gson gson) {
        return gson.fromJson(jsonObject, Comment.class);
    }

    private static boolean filterEvents(IssueEventType type) {
        return type != null && type != IssueEventType.subscribed && type != IssueEventType.unsubscribed
                && type != IssueEventType.mentioned;
    }
}
