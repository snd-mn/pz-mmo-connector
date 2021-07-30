package org.projectzion.game.mmoconnector.utils.calls.sendItems;

import org.projectzion.game.mmoconnector.persistence.entities.TargetSystem;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.CallParameter;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.CallState;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.UserCall;
import org.projectzion.game.mmoconnector.persistence.entities.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Executable;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SendItemTrinityCore extends SendItem{

    @Override
    public CallState executeCall(UserCall userCall) {
        User user = null;
        TargetSystem targetSystem = null;
        Character character = null;

        try{

        }catch(Exception e){
            return CallState.Failed;
        }

        if(user == null || targetSystem == null || character == null){
            return CallState.Failed;
        }

        return CallState.DONE;
    }
}
