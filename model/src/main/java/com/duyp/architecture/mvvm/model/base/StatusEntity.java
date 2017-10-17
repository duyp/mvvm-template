package com.duyp.architecture.mvvm.model.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Duy Pham on 1/20/2016.
 * Status entity of every api response, differ HTTP Status.
 * Note: Github api response message only, replace with your own api status
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class StatusEntity {

    private boolean success = true;
    private int code = 0;
    private String message;
}
