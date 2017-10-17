package com.duyp.architecture.mvvm.utils;

/**
 * Created by duypham on 9/7/17.
 *
 */

public class RemoteConstants {

    public static final String HEADER_AUTH = "Authorization";

    public static final String ENDPOINT = "https://api.github.com";
    public static final int TIME_OUT_API = 30; // second

    // format for all timestamps returned from github apis
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
}
