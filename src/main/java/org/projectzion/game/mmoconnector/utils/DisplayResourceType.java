package org.projectzion.game.mmoconnector.utils;

import java.util.Arrays;
import java.util.Optional;

public enum DisplayResourceType {
    ORE(0),
    FLOWER(1),
    TREE(2),
    ROCK(3),
    MUSHROOM(4),
    TRANSFER_STATION(5),
    CHEST(5),

    ;

    private final int value;

    DisplayResourceType(int value) {
        this.value = value;
    }

    public static Optional<DisplayResourceType> valueOf(int value) {
        return Arrays.stream(values())
                .filter(type -> type.value == value)
                .findFirst();
    }
}
