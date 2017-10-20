package com.duyp.architecture.mvvm.data.source;

import android.support.annotation.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Created by duypham on 10/19/17.
 *
 */

@Getter
@Setter
public class State {
    @NonNull
    Status status;

    @Nullable
    String message;

    boolean hardAlert = false;

    public State(@NonNull Status status, @Nullable String message) {
        this.status = status;
        this.message = message;
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
}
