package org.projectzion.game.mmoconnector.batch;

import org.projectzion.game.mmoconnector.persistence.entities.rpc.CallState;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.UserCall;
import org.projectzion.game.mmoconnector.persistence.repositories.UserCallRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class UserCallReader implements ItemReader<UserCall> {

    @Autowired
    UserCallRepository userCallRepository;

    @Override
    public UserCall read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        List<UserCall> calls = userCallRepository.findUserCallsByStateInOrderByCreatedDesc(CallState.STATES_TODO);
        Optional<UserCall> call = calls.stream().findFirst();
        if(call.isPresent()){
            return call.get();
        }
        return null;
    }
}
