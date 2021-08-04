package org.projectzion.game.mmoconnector.batch;

import org.projectzion.game.mmoconnector.persistence.entities.rpc.CallState;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.UserCall;
import org.projectzion.game.mmoconnector.persistence.repositories.UserCallRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class UserCallReader implements ItemReader<UserCall> {
    private static final Logger log = LoggerFactory.getLogger(UserCallReader.class);

    @Autowired
    UserCallRepository userCallRepository;

    Iterator<UserCall> userCalliterator = null;
    @Override
    public UserCall read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if(userCalliterator == null){
            userCalliterator = userCallRepository.findUserCallsByStateInOrderByCreatedDesc(CallState.STATES_TODO).iterator();
        }

        UserCall call = null;
        try{
            call = userCalliterator.next();
        }catch(Exception e){
            log.info("no more items");
        }

        return call;
    }
}
