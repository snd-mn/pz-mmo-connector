package org.projectzion.game.mmoconnector.utils.calls;

import org.projectzion.game.mmoconnector.persistence.entities.rpc.CallState;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.UserCall;


public interface ICall {
    CallState executeCall(UserCall userCall) throws Exception;
}
