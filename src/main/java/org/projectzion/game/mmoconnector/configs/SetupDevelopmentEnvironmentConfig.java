package org.projectzion.game.mmoconnector.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@EnableConfigurationProperties
@Configuration
@ConfigurationProperties(prefix = "pz.mmoconnector.setup.devenv")
public class SetupDevelopmentEnvironmentConfig {
    UserConfig trinitycoreConnectorUser;
    TargetSystemConfig trinityCoreTargetSystemConfig;
    UserConfig trinitycorePlayerUser;
    CharacterConfig PlayersCharacter;
    List<NodeTypeAmountConfig> nodeTypeAmountConfigList;
}
