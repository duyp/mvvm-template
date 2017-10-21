
package com.duyp.architecture.mvvm.data.remote;

import android.support.annotation.NonNull;

import com.duyp.architecture.mvvm.model.remote.ApiResponse;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A Retrofit adapterthat converts the Call into a ApiResponse.
 * @param <R>
 */
public class ApiResponseCallAdapter<R> implements CallAdapter<R, ApiResponse<R>> {
    private final Type responseType;
    public ApiResponseCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public ApiResponse<R> adapt(@NonNull Call<R> call) {
        final ApiResponse<R> apiResponse = new ApiResponse<R>();

        call.enqueue(new Callback<R>() {
            @Override
            public void onResponse(@NonNull Call<R> call,@NonNull  Response<R> response) {
                apiResponse.init(response);
            }

            @Override
            public void onFailure(@NonNull Call<R> call,@NonNull  Throwable t) {
                apiResponse.init(t);
            }
        });

        return apiResponse;
    }
}
