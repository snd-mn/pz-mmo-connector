package org.projectzion.game.mmoconnector.utils.setup;

import org.projectzion.game.mmoconnector.configs.SetupDevelopmentEnvironmentConfig;
import org.projectzion.game.mmoconnector.persistence.entities.TargetSystem;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.Call;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.CallIdentifier;
import org.projectzion.game.mmoconnector.persistence.entities.security.User;
import org.projectzion.game.mmoconnector.persistence.repositories.*;
import org.projectzion.game.mmoconnector.utils.calls.sendItems.SendItemTrinityCore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SetupDevelopmentEnvironment implements ApplicationListener<ContextRefreshedEvent> {
    Logger logger = LoggerFactory.getLogger(SetupDevelopmentEnvironment.class);

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

    public static final String KEY_ALLREADY_RUN = "SetupDevelopmentEnvironment.ALLREADY_RUN";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(null == keyValueRepository.findByKey(KEY_ALLREADY_RUN)){
            logger.info("SetupDevelopmentEnvironment allready done");
        }

        User localTrinityCoreConnectionUser = new User();
        localTrinityCoreConnectionUser.setEmail(config.getTrinitycoreConnectorUser().getEmail());
        localTrinityCoreConnectionUser.setPassword(config.getTrinitycoreConnectorUser().getPassword());

        userRepository.save(localTrinityCoreConnectionUser);

        TargetSystem localTrinityCore = new TargetSystem();
        localTrinityCore.setIp(config.getTrinityCoreTargetSystemConfig().getIp());
        localTrinityCore.setPort(config.getTrinityCoreTargetSystemConfig().getPort());
        localTrinityCore.setConnectionUser(localTrinityCoreConnectionUser);

        targetSystemRepository.save(localTrinityCore);

        Call sendItemsCall = new Call();
        sendItemsCall.setCallIdentifier(CallIdentifier.SEND_ITEMS);
        sendItemsCall.setBean(SendItemTrinityCore.class.toString());
        //todo nodetypes

        // sendItemsCall.setNodeTypeCalls();
        sendItemsCall.setTargetSystem(localTrinityCore);




    }
}
