package org.projectzion.game.mmoconnector.utils.calls.pick;

import lombok.Data;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.CallState;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.UserCall;
import org.projectzion.game.mmoconnector.utils.calls.ICall;

import java.io.Serializable;

@Data

public abstract class Pick implements ICall, Serializable {
    Long nodeType;
}
