package org.projectzion.game.mmoconnector.utils.setup;

import lombok.SneakyThrows;
import org.projectzion.game.mmoconnector.configs.NodeTypeAmountConfig;
import org.projectzion.game.mmoconnector.configs.SetupDevelopmentEnvironmentConfig;
import org.projectzion.game.mmoconnector.persistence.entities.Character;
import org.projectzion.game.mmoconnector.persistence.entities.Item;
import org.projectzion.game.mmoconnector.persistence.entities.NodeType;
import org.projectzion.game.mmoconnector.persistence.entities.NodeTypeItem;
import org.projectzion.game.mmoconnector.persistence.entities.TargetSystem;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.Call;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.CallIdentifier;
import org.projectzion.game.mmoconnector.persistence.entities.security.Privilege;
import org.projectzion.game.mmoconnector.persistence.entities.security.Role;
import org.projectzion.game.mmoconnector.persistence.entities.security.User;
import org.projectzion.game.mmoconnector.persistence.repositories.*;
import org.projectzion.game.mmoconnector.services.KeyValueService;
import org.projectzion.game.mmoconnector.utils.calls.pick.PickTrinityCore;
import org.projectzion.game.mmoconnector.utils.calls.sendItems.SendItemTrinityCore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class SetupDevelopmentEnvironment implements ApplicationListener<ContextRefreshedEvent> {
    Logger logger = LoggerFactory.getLogger(SetupDevelopmentEnvironment.class);

    public static final String ROLE_SUPER_USER = "ROLE_SUPER_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

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
    PrivilegeRepository privilegeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    SetupDevelopmentEnvironmentConfig config;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static final String KEY_ALLREADY_RUN = "SetupDevelopmentEnvironment.ALLREADY_RUN";

    @Transactional
    void saveSetupIsDone() throws Exception{
        Date now = new Date();
        keyValueService.save(KEY_ALLREADY_RUN,now);
    }

    private boolean isSetupDone() throws Exception {
        return keyValueService.read(KEY_ALLREADY_RUN, Date.class) != null;
    }
    @Transactional
    protected Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    protected Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
    private void setupPriviliges(){
        // == create initial privileges
        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        Privilege writeSuperUser = createPrivilegeIfNotFound("WRITE_USERS_SUPER_USER");
        Privilege readSuperUser = createPrivilegeIfNotFound("READ_USERS_SUPER_USER");
        Privilege writeAdmin = createPrivilegeIfNotFound("WRITE_USERS_ADMIN");
        Privilege readAdmin = createPrivilegeIfNotFound("READ_USERS_USER");
        Privilege writeUser = createPrivilegeIfNotFound("WRITE_USERS_USER");
        Privilege readUser = createPrivilegeIfNotFound("READ_USERS_USER");

        // == create initial roles
        List<Privilege> super_userPrivileges = Arrays.asList(readPrivilege, writePrivilege,readSuperUser,writeAdmin,readAdmin,writeUser,readUser);
        createRoleIfNotFound(ROLE_SUPER_USER, super_userPrivileges);

        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege,readAdmin,writeUser,readUser);
        createRoleIfNotFound(ROLE_ADMIN, adminPrivileges);

        List<Privilege> rolePrivileges = Arrays.asList(readPrivilege, writePrivilege, readUser);
        createRoleIfNotFound(ROLE_USER, rolePrivileges);

    }

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(isSetupDone()){
            logger.info("SetupDevelopmentEnvironment already done");
            return;
        }
        setupPriviliges();

        ////////////////////////////////////////////
        // connection user
        User localTrinityCoreConnectionUser = new User();
        localTrinityCoreConnectionUser.setEmail(config.getTrinitycoreConnectorUser().getEmail());
        //TODO REMOVE ON OAUTH2
        localTrinityCoreConnectionUser.setPassword(passwordEncoder().encode(config.getTrinitycoreConnectorUser().getPassword()));
        localTrinityCoreConnectionUser.setPasswordClear(config.getTrinitycoreConnectorUser().getPassword());
        localTrinityCoreConnectionUser.setRemotePassword(config.getTrinitycoreConnectorUser().getPassword());

        Role role = roleRepository.findByName(ROLE_SUPER_USER);
        List<Role> roles = List.of(new Role[]{role});
        localTrinityCoreConnectionUser.setRoles(roles);
        userRepository.save(localTrinityCoreConnectionUser);

        TargetSystem localTrinityCore = new TargetSystem();
        localTrinityCore.setId(config.getTrinityCoreTargetSystemConfig().getId());
        localTrinityCore.setIp(config.getTrinityCoreTargetSystemConfig().getIp());
        localTrinityCore.setPort(config.getTrinityCoreTargetSystemConfig().getPort());
        localTrinityCore.setConnectionUser(localTrinityCoreConnectionUser);
        targetSystemRepository.save(localTrinityCore);

        ////////////////////////////////////////////
        // player
        User trinitycorePlayerUser = new User();
        trinitycorePlayerUser.setEmail(config.getTrinitycorePlayerUser().getEmail());
        trinitycorePlayerUser.setPasswordClear(config.getTrinitycorePlayerUser().getPassword());
        trinitycorePlayerUser.setPassword(passwordEncoder().encode(config.getTrinitycorePlayerUser().getPassword()));
        Role rolePlayer = roleRepository.findByName(ROLE_USER);
        List<Role> rolesPlayer = List.of(new Role[]{rolePlayer});
        trinitycorePlayerUser.setRoles(rolesPlayer);
        userRepository.save(trinitycorePlayerUser);

        Character usersCharacter = new Character();
        usersCharacter.setName(config.getPlayersCharacter().getCharacterName());
        usersCharacter.setUser(trinitycorePlayerUser);
        usersCharacter.setUsedForItemTransfer(config.getPlayersCharacter().isUsedForItemTransfer());
        usersCharacter.setTargetSystem(localTrinityCore);
        characterRepository.save(usersCharacter);

        Set<Character> characters = new HashSet<>();
        characters.add(usersCharacter);
        trinitycorePlayerUser.setCharacters(characters);
        userRepository.save(trinitycorePlayerUser);

        ////////////////////////////////////////////
        // nodetype stuff
        config.getNodeTypeAmountConfigList().forEach(nodeTypeConfig -> {
            TargetSystem currentTargetSystem = targetSystemRepository.findById(localTrinityCore.getId()).get();

            NodeType nodeType = new NodeType();
            nodeType.setId(nodeTypeConfig.getNodeTypeId());
            nodetypeRepository.save(nodeType);

            Item item = new Item();
            item.setId(nodeTypeConfig.getTargetSystemItemId());
            item.setTargetSystem(currentTargetSystem);
//            item.setTargetSystemItemId(nodeTypeConfig.getTargetSystemItemId());
            itemRepository.save(item);

            NodeTypeItem nodeTypeItem = new NodeTypeItem();
            nodeTypeItem.setNodeType(nodeType);
            nodeTypeItem.setItem(item);
            nodeTypeItem.setAmount(nodeTypeConfig.getAmount());
            nodeTypeItemRepository.save(nodeTypeItem);
        });

        Call sendItemsCall = new Call();
        sendItemsCall.setCallIdentifier(CallIdentifier.SEND_ITEMS);
        sendItemsCall.setBean(SendItemTrinityCore.class.getName());
        sendItemsCall.setTargetSystem(localTrinityCore);
        callRepository.save(sendItemsCall);

        Call pickCall = new Call();
        pickCall.setCallIdentifier(CallIdentifier.PICK);
        pickCall.setBean(PickTrinityCore.class.getName());
        pickCall.setTargetSystem(localTrinityCore);
        callRepository.save(pickCall);
        saveSetupIsDone();


        //jo ich forsche an nem heilmittelt gegen mmo-sucht... lasse mir das von euch bezahlen... weil ihr wegseht
        //ihr seht es in euren firmen
        //euren familien
        //degradiert diese menschen und fangt an sie zu hassen
        //dabei hasst ihr euch selbst und tretet nach denen die grade nicht stehen können


    }
}
