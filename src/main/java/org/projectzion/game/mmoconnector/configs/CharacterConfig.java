package org.projectzion.game.mmoconnector.configs;

import lombok.Data;
import org.projectzion.game.mmoconnector.persistence.entities.Character;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class CharacterConfig {
    String characterName;
    boolean isUsedForItemTransfer;
}
