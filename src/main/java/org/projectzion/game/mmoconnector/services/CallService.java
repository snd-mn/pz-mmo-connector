package org.projectzion.game.mmoconnector.services;

import org.projectzion.game.mmoconnector.persistence.entities.rpc.Call;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.CallIdentifier;
import org.projectzion.game.mmoconnector.persistence.repositories.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CallService {
    @Autowired
    CallRepository callRepository;

    public Call getCallByCallIdentifierAndTargetSystem(CallIdentifier callIdentifier, Long targetSystem){
        return callRepository.getCallByCallIdentifierAndTargetSystem(callIdentifier,targetSystem);
    }
}
