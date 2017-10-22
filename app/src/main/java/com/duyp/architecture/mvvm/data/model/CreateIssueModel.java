package com.duyp.architecture.mvvm.data.model;

import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Kosh on 19 Feb 2017, 12:13 PM
 */

@Getter
@Setter
@NoArgsConstructor
public class CreateIssueModel {
    private String title;
    private String body;
    private ArrayList<String> labels;
    private ArrayList<String> assignees;
    private Long milestone;
}
