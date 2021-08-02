package org.projectzion.game.mmoconnector.utils.setup;

import lombok.SneakyThrows;
import org.projectzion.game.mmoconnector.configs.SetupDevelopmentEnvironmentConfig;
import org.projectzion.game.mmoconnector.persistence.entities.Item;
import org.projectzion.game.mmoconnector.persistence.entities.NodeType;
import org.projectzion.game.mmoconnector.persistence.entities.NodeTypeItem;
import org.projectzion.game.mmoconnector.persistence.entities.TargetSystem;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.Call;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.CallIdentifier;
import org.projectzion.game.mmoconnector.persistence.entities.security.User;
import org.projectzion.game.mmoconnector.persistence.repositories.*;
import org.projectzion.game.mmoconnector.services.KeyValueService;
import org.projectzion.game.mmoconnector.utils.calls.sendItems.SendItemTrinityCore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
    KeyValueService keyValueService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    NodeTypeRepository nodetypeRepository;

    @Autowired
    NodeTypeItemRepository nodeTypeItemRepository;

    @Autowired
    SetupDevelopmentEnvironmentConfig config;

    public static final String KEY_ALLREADY_RUN = "SetupDevelopmentEnvironment.ALLREADY_RUN";

    @Transactional
    void saveSetupIsDone() throws Exception{
        Date now = new Date();
        keyValueService.save(KEY_ALLREADY_RUN,now);
    }

    private boolean isSetupDone() throws Exception {
        return keyValueService.read(KEY_ALLREADY_RUN, Date.class) != null;
    }

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(isSetupDone()){
            logger.info("SetupDevelopmentEnvironment allready done");
            return;
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

        config.getNodeTypeAmountConfigList().forEach(nodeTypeConfig -> {
            TargetSystem currentTargetSystem = targetSystemRepository.findById(nodeTypeConfig.getTargetSystemItemId()).get();

            NodeType nodeType = new NodeType();
            nodeType.setId(nodeTypeConfig.getNodeTypeId());
            nodetypeRepository.save(nodeType);

            Item item = new Item();
            item.setTargetSystemItemId(currentTargetSystem.getId());
            item.setTargetSystemItemId(nodeTypeConfig.getTargetSystemItemId());
            itemRepository.save(item);

            NodeTypeItem nodeTypeItem = new NodeTypeItem();
            nodeTypeItem.setNodeType(nodeType);
            nodeTypeItem.setItem(item);
            nodeTypeItem.setAmount(nodeTypeConfig.getAmount());
            nodeTypeItemRepository.save(nodeTypeItem);
        });

        Call sendItemsCall = new Call();
        sendItemsCall.setCallIdentifier(CallIdentifier.SEND_ITEMS);
        sendItemsCall.setBean(SendItemTrinityCore.class.toString());
        sendItemsCall.setTargetSystem(localTrinityCore);
        callRepository.save(sendItemsCall);
        saveSetupIsDone();


    }
}
