package org.projectzion.game.mmoconnector.batch;

import org.projectzion.game.mmoconnector.persistence.entities.rpc.UserCall;
import org.projectzion.game.mmoconnector.persistence.repositories.UserCallRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserCallWriter implements ItemWriter<UserCall> {
    private static final Logger log = LoggerFactory.getLogger(UserCallWriter.class);

    @Autowired
    UserCallRepository userCallRepository;

    public void write(List<? extends UserCall> items){
        userCallRepository.saveAll(items);
        log.info("finished writing");
    }

}
