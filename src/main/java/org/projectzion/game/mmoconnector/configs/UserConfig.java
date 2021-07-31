package org.projectzion.game.mmoconnector.configs;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class UserConfig {
    String email;
    String password;
}
