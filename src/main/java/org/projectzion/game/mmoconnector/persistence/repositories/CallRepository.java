package org.projectzion.game.mmoconnector.persistence.repositories;

import org.projectzion.game.mmoconnector.persistence.entities.TargetSystem;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.Call;
import org.projectzion.game.mmoconnector.persistence.entities.security.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CallRepository extends CrudRepository<Call, Long> {

    @Query("SELECT c FROM Call c WHERE c.targetSystem.id = :targetSystemId AND c.callIdentifier = :callIdentifier")
    Call getCallByCallIdentifierAndTargetSystem(@Param("callIdentifier") Long callIdentifier, @Param("targetSystemId") Long targetSystemId);


}
