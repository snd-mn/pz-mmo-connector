package org.projectzion.game.mmoconnector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScan(basePackages={"org.projectzion.game"})
public class PzMmoConnectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PzMmoConnectorApplication.class, args);
    }
}
