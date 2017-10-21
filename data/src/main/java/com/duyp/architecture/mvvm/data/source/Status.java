package com.duyp.architecture.mvvm.data.source;

/**
 * Status of a resource that is provided to the UI.
 * <p>
 * These are usually created by the Repo classes where they return
 * {@code LiveData<Resource<T>>} to pass back the latest data to the UI with its fetch status.
 */
public enum Status {
    SUCCESS,
    ERROR,
    LOADING
}
