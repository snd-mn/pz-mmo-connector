package org.projectzion.game.mmoconnector.persistence.entities.rpc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum CallState {
    FAILED(0),
    READY(1),
    IN_PROGRESS(2),
    DONE(3)
    ;

    private final int value;

    CallState(int value) {
        this.value = value;
    }

    public static Optional<CallState> valueOf(int value) {
        return Arrays.stream(values())
                .filter(type -> type.value == value)
                .findFirst();
    }

    public static List<CallState> STATES_TODO = List.of(new CallState[]{READY, FAILED});
}
