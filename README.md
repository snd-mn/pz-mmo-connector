# pz-mmo-connector
This service connects 3rd party applications to the https://github.com/snd-mn/pz-game-webservice
It's simply a gateway for the rewards the user collected.

# how to
1. Edit your application-properties to fit your system:

###############################################
# db stuff
###############################################
server.port=1234
spring.datasource.url=%JDBC_URL%
spring.datasource.username=%DB_USER%
spring.datasource.password=%DB_PASSWORD%
spring.jpa.hibernate.ddl-auto=update

###############################################
# dev env stuff
###############################################
pz.mmoconnector.setup.devenv.trinitycore-connector-user=%TC_USER%,%TC_PW%
pz.mmoconnector.setup.devenv.trinity-core-target-system-config=%TARGET_SYSTEM_ID%,%TARGET_SYSTEM_IP%,%TARGET_SYSTEM_PORT%
pz.mmoconnector.setup.devenv.trinitycore-player-user=%TARGET_SYSTEM_PLAYER_TEST_ACCOUNT%,%TARGET_SYSTEM_PLAYER_TEST_ACCOUNT_PW%
pz.mmoconnector.setup.devenv.players-character=%TARGET_SYSTEM_PLAYER_TEST_ACCOUNT%,%TARGET_SYSTEM_PLAYER_TEST_ACCOUNT_bALLOWED%
pz.mmoconnector.setup.devenv.node-type-amount-config-list=1;23424;3;1;2;11370;3;1;3;2447;3;1 #Item configuration, can be configured later via db.

2. start the https://github.com/snd-mn/pz-game-webservice
3. start the pz-mmo-connector
