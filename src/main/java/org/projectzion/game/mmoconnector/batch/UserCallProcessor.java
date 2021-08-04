package org.projectzion.game.mmoconnector.batch;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.CallState;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.UserCall;
import org.projectzion.game.mmoconnector.persistence.repositories.UserCallRepository;
import org.projectzion.game.mmoconnector.utils.calls.ICall;
import org.projectzion.game.mmoconnector.utils.calls.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

public class UserCallProcessor implements ItemProcessor<UserCall, UserCall> {
    private static final Logger log = LoggerFactory.getLogger(UserCallProcessor.class);

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    UserCallRepository userCallRepository;

    @Override
    public UserCall process(UserCall userCall) throws Exception {
        log.info("UserCallProcessor möööp");

        //hmm move this to processor?
        userCall.setState(CallState.IN_PROGRESS);
        userCallRepository.save(userCall);

        String serializedBean = userCall.getSerializedBean();
        Class<? extends ICall> callClass = (Class<ICall>) Class.forName(userCall.getCall().getBean());

        ObjectMapper objectMapper = JsonUtils.getObjectMapper();
        ICall call = objectMapper.readValue(serializedBean,callClass); //.. na ob das klappt :D

        //somehow this needs to be done, otherwise the autowired members are null
        AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();
        factory.autowireBean(call);

        CallState state = call.executeCall(userCall);
        userCall.setState(state);

        return userCall;
    }
}
