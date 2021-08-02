package org.projectzion.game.mmoconnector.utils.calls.pick;

import org.projectzion.game.mmoconnector.persistence.entities.NodeTypeItem;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.CallState;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.UserCall;
import org.projectzion.game.mmoconnector.persistence.repositories.ItemRepository;
import org.projectzion.game.mmoconnector.persistence.repositories.NodeTypeItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public class PickTrinityCore extends Pick implements Serializable {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    NodeTypeItemRepository nodeTypeItemRepository;

    @Override
    public CallState executeCall(UserCall userCall) throws Exception {
        List<NodeTypeItem> ntis = nodeTypeItemRepository.getNodeTypeItemsByTargetSystemAndNodeType(nodeType,userCall.getCall().getTargetSystem().getId())
        ntis.forEach(nti -> {

        });
        //TODO copy all the stuff from SendItemTrinityCore
        //TODO create a mapping for node_type -> item_targetsystem-item_mapping -> actual item

        return null;
    }
}
