package org.projectzion.game.mmoconnector.persistence.repositories;

import org.projectzion.game.mmoconnector.persistence.entities.NodeType;
import org.projectzion.game.mmoconnector.persistence.entities.NodeTypeItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeTypeRepository extends CrudRepository<NodeType, Long> {
}
