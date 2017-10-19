package com.duyp.architecture.mvvm.model.remote;

import android.support.annotation.Nullable;

import org.json.JSONObject;

import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Entity for handling error
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorEntity {

    public static final int HTTP_ERROR_CODE_UNAUTHORIZED = 401;

    public static final String OOPS = "Oops! please try again";
    public static final String NETWORK_UNAVAILABLE = "Network problem!";
    public static final String ERROR_UNAUTHORIZED = "Error! Please re-login!";

    private String message = "";
    private int httpCode = 0;

    public static ErrorEntity getError(@Nullable Throwable throwable) {
        ErrorEntity e = new ErrorEntity();
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            if (httpException.code() == HTTP_ERROR_CODE_UNAUTHORIZED) {
                e.setHttpCode(HTTP_ERROR_CODE_UNAUTHORIZED);
                e.setMessage(ERROR_UNAUTHORIZED);
            } else {
                // get the body fail
                Response baseResponse = httpException.response();
                e.setHttpCode(baseResponse.code());
                if (!baseResponse.isSuccessful() && baseResponse.errorBody() != null) {
                    try {
                        JSONObject jObjError = new JSONObject(baseResponse.errorBody().string());
                        e.setMessage(jObjError.getString("message"));
                    } catch (Exception eXX) {
                        eXX.printStackTrace();
                        e.setMessage("Error");
                    }
                }
            }
        } else if (throwable instanceof IOException){
            e.setMessage(NETWORK_UNAVAILABLE);
        } else {
            e.setMessage(OOPS);
        }
        return e;
    }

    public static ErrorEntity getError(int code, String reason) {
        if (reason != null) {
            return new ErrorEntity(reason, code);
        } else {
            return new ErrorEntity(OOPS, code);
        }
    }

    public static ErrorEntity getErrorOops() {
        return new ErrorEntity(OOPS, 0);
    }
}
