package org.projectzion.game.mmoconnector.persistence.repositories;

import org.projectzion.game.mmoconnector.persistence.entities.rpc.Call;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.CallState;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.UserCall;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCallRepository extends CrudRepository<UserCall, Long> {

    @Query("SELECT uc FROM UserCall uc WHERE uc.state = :callState")
    public List<UserCall> findUserCallsByStateEqual(@Param("callState") CallState callState);

    @Query("SELECT uc FROM UserCall uc WHERE uc.state IN :callStates ORDER BY uc.created ASC")
    public List<UserCall> findUserCallsByStateInOrderByCreatedDesc(@Param("callStates") List<CallState> callStates);
}
