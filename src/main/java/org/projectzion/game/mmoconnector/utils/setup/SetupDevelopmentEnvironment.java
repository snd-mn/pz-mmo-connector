package org.projectzion.game.mmoconnector.utils.setup;

import org.projectzion.game.mmoconnector.configs.SetupDevelopmentEnvironmentConfig;
import org.projectzion.game.mmoconnector.persistence.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SetupDevelopmentEnvironment implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TargetSystemRepository targetSystemRepository;

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    CallRepository callRepository;

    @Autowired
    KeyValueRepository keyValueRepository;

    @Autowired
    SetupDevelopmentEnvironmentConfig config;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("");
    }
}
