package org.projectzion.game.mmoconnector.configs;

import lombok.Data;
import org.projectzion.game.mmoconnector.persistence.entities.Character;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
public class CharacterConfig {
    String characterName;
    boolean isUsedForItemTransfer;
}
