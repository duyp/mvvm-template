package com.duyp.architecture.mvvm.data.remote;

import com.duyp.architecture.mvvm.data.model.User;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Created by duypham on 9/7/17.
 * Github Rest API retrofit service (See https://developer.github.com/v3/) in USER SCOPE
 * Contains all APIs which need Authorization token added in request header
 */

public interface UserService {

    @GET("user")
    Single<Response<User>> updateUser();

}
