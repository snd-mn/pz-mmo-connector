package org.projectzion.game.mmoconnector.configs;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class TargetSystemConfig {
    String ip;
    String port;
}
