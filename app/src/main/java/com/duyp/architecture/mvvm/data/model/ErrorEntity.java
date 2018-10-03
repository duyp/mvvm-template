package com.duyp.architecture.mvvm.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity for handling error
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorEntity {

    public static final int HTTP_ERROR_CODE_UNAUTHORIZED = 401;

    public static final String OOPS = "Unexpected error while requesting API";
    public static final String NETWORK_UNAVAILABLE = "Network problem!";
    public static final String ERROR_UNAUTHORIZED = "Error! Please re-login!";

    private String message = "";
    private int httpCode = 0;

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

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ErrorEntity entity = (ErrorEntity)obj;
        return this.httpCode == entity.httpCode && this.message.equals(entity.getMessage());
    }
}