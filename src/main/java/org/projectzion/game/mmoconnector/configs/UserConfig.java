package org.projectzion.game.mmoconnector.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Data
public class UserConfig {
    String email;
    String password;
}
