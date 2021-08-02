package org.projectzion.game.mmoconnector.persistence.entities.rpc;

import java.util.Arrays;
import java.util.Optional;

public enum CallIdentifier {
    SEND_ITEMS(0L),
    PICK(1L);

    public final Long value;

    CallIdentifier(Long value) {
        this.value = value;
    }

    public static Optional<CallIdentifier> valueOf(int value) {
        return Arrays.stream(values())
                .filter(type -> type.value == value)
                .findFirst();
    }



}
