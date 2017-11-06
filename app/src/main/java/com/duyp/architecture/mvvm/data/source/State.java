package com.duyp.architecture.mvvm.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by duypham on 10/19/17.
 *
 */

@Getter
@Setter
public class State {
    @NonNull
    public Status status;

    @Nullable
    public String message;

    boolean hardAlert = false;

    public State(@NonNull Status status, @Nullable String message) {
        this.status = status;
        this.message = message;
    }

    public static State loading(String message) {
        return new State(Status.LOADING, message);
    }

    public static State error(String message) {
        return new State(Status.ERROR, message);
    }

    public static State success(String message) {
        return new State(Status.SUCCESS, message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        State state = (State)o;

        if (status != state.getStatus()) {
            return false;
        }

        return message != null ? message.equals(state.message) : state.message == null;
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        return  31 * result + (message != null ? message.hashCode() : 0);
    }

    @Override
    public String toString() {
        return "status: " + status + ", message: " + message;
    }
}
