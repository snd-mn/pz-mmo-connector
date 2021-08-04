package org.projectzion.game.mmoconnector;

import org.projectzion.game.mmoconnector.configs.security.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(basePackages={"org.projectzion.game"})
@Import({ SecurityConfig.class })
public class PzMmoConnectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PzMmoConnectorApplication.class, args);
    }
}
