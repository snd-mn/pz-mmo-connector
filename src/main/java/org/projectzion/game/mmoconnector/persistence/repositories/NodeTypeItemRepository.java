package org.projectzion.game.mmoconnector.persistence.repositories;

import org.projectzion.game.mmoconnector.persistence.entities.NodeTypeItem;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.Call;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeTypeItemRepository extends CrudRepository<NodeTypeItem, Long> {

    @Query("SELECT nti FROM NodeTypeItem nti " +
            "JOIN Item i on i.id = nti.item.id AND i.targetSystem.id = :targetSystemId " +
            "WHERE nti.nodeType.id = :nodeTypeId "

    )
    List<NodeTypeItem> getNodeTypeItemsByTargetSystemAndNodeType(@Param("nodeTypeId") Long nodeTypeId, @Param("targetSystemId") Long targetSystemId);


}
