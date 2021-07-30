package org.projectzion.game.mmoconnector.persistence.repositories;

import org.projectzion.game.mmoconnector.persistence.entities.TargetSystem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends CrudRepository<TargetSystem, Long> {

}
