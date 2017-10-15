package com.duyp.architecture.mvvm.data.model.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Duy Pham on 10/27/2015.
 * Base response for all respond models
 */
public abstract class BaseResponse {

    @SerializedName("message")
    @Expose
    public String message = null;

    public boolean isSuccess() {
        // for GitHub api, we must check the respond message, not null mean we have error
        return message == null;
    }
}