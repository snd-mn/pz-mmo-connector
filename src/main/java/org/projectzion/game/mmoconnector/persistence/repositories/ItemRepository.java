package org.projectzion.game.mmoconnector.persistence.repositories;

import org.projectzion.game.mmoconnector.persistence.entities.Item;
import org.projectzion.game.mmoconnector.persistence.entities.NodeTypeItem;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.Call;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

    @Query("SELECT i FROM Item i " +
            "JOIN NodeTypeItem  nti on nti.item = i " +
            "WHERE i.id = :itemId And i.targetSystem.id = :targetSystemId"
    )
    Item getItemForNodeTypeTargetsystem(@Param("itemId") Long itemId, @Param("targetSystemId") Long targetSystemId);


}
