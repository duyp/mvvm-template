package com.duyp.architecture.mvvm.model;

import android.support.annotation.NonNull;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.duyp.architecture.mvvm.model.type.IssueState;

import java.util.List;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Kosh on 10 Dec 2016, 8:53 AM
 */

@Getter
@Setter
@NoArgsConstructor
public class IssueRequestModel {

    private String state; // todo IssueState
    private String title;
    private String body;
    private Integer milestone;
    private String assignee;
    private List<String> labels;
    private String base;

    public static IssueRequestModel clone(@NonNull Issue issue, boolean toClose) {
        IssueRequestModel model = new IssueRequestModel();
        if (issue.getLabels() != null) {
            model.setLabels(Stream.of(issue.getLabels()).filter(value -> value.getName() != null)
                    .map(Label::getName).collect(Collectors.toList()));
        }
        model.setAssignee(issue.getAssignee() != null ? issue.getAssignee().getLogin() : null);
        model.setBody(issue.getBody());
        model.setMilestone(issue.getMilestone() != null ? issue.getMilestone().getNumber() : null);
        model.setState(toClose ? issue.getState().equalsIgnoreCase(IssueState.closed.getStatus())
                ? IssueState.open.getStatus() : IssueState.closed.getStatus() : issue.getState());
        model.setTitle(issue.getTitle());
        return model;
    }

    public static IssueRequestModel clone(@NonNull PullRequest issue, boolean toClose) {
        IssueRequestModel model = new IssueRequestModel();
        if (issue.getLabels() != null) {
            model.setLabels(Stream.of(issue.getLabels()).filter(value -> value.getName() != null)
                    .map(Label::getName).collect(Collectors.toList()));
        }
        model.setBase(issue.getBase() != null ? issue.getBase().getRef() : "master");
        model.setAssignee(issue.getAssignee() != null ? issue.getAssignee().getLogin() : null);
        model.setBody(issue.getBody());
        model.setMilestone(issue.getMilestone() != null ? issue.getMilestone().getNumber() : null);
        model.setState(toClose ? issue.getState().equalsIgnoreCase(IssueState.closed.getStatus())
                ? IssueState.open.getStatus() : IssueState.closed.getStatus() : issue.getState());
        model.setTitle(issue.getTitle());
        return model;
    }
}
