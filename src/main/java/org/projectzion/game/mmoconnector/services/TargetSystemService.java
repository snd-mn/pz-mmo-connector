package org.projectzion.game.mmoconnector.services;

import org.projectzion.game.mmoconnector.persistence.entities.TargetSystem;
import org.projectzion.game.mmoconnector.persistence.repositories.TargetSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TargetSystemService {
    @Autowired
    TargetSystemRepository targetSystemRepository;

    public TargetSystem getTargetSystemById(Long id){
        return targetSystemRepository.findById(id).get();
    }
}
