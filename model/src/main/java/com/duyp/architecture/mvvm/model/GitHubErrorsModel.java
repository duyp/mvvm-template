package com.duyp.architecture.mvvm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Kosh on 18 Feb 2017, 2:10 PM
 */

@Getter
@Setter
@NoArgsConstructor
class GitHubErrorsModel {
    private String resource;
    private String field;
    private String code;
}
