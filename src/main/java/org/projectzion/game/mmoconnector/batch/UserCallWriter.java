package org.projectzion.game.mmoconnector.batch;

import org.projectzion.game.mmoconnector.persistence.entities.rpc.UserCall;
import org.projectzion.game.mmoconnector.persistence.repositories.UserCallRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserCallWriter implements ItemWriter<UserCall> {
    @Autowired
    UserCallRepository userCallRepository;

    @Override
    public void write(List<? extends UserCall> list) throws Exception {
        //just persits the callstate
        list.forEach(userCall -> {
            userCallRepository.save(userCall);
        });
    }
}
