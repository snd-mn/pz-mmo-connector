package org.projectzion.game.mmoconnector.configs;

import lombok.Data;

@Data
public class NodeTypeAmountConfig {
    Long nodeTypeId;
    Long targetSystemId;
    Long targetSystemItemId;
    float amount;
}
