/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.duyp.architecture.mvvm.data.remote;

import android.support.annotation.NonNull;

import com.duyp.architecture.mvvm.model.remote.ApiResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class ApiResponseCallAdapterFactory extends CallAdapter.Factory {

    @Override
    public CallAdapter<?, ?> get(@NonNull Type returnType,@NonNull  Annotation[] annotations,@NonNull  Retrofit retrofit) {
        if (getRawType(returnType) != ApiResponse.class) {
            return null;
        }
        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        if (! (observableType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("resource must be parameterized");
        }
        Type bodyType = getParameterUpperBound(0, (ParameterizedType) observableType);
        return new ApiResponseCallAdapter<>(bodyType);
    }
}
