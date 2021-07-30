package org.projectzion.game.mmoconnector.persistence.entities.rpc;

import java.util.Arrays;
import java.util.Optional;

public enum CallState {
    READY(0),
    IN_PROGRESS(1),
    DONE(2),
    Failed(3);

    private final int value;

    CallState(int value) {
        this.value = value;
    }

    public static Optional<CallState> valueOf(int value) {
        return Arrays.stream(values())
                .filter(type -> type.value == value)
                .findFirst();
    }
}
