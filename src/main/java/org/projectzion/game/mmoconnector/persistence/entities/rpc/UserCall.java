package org.projectzion.game.mmoconnector.persistence.entities.rpc;

import lombok.Getter;
import lombok.Setter;
import org.projectzion.game.mmoconnector.persistence.entities.security.User;
import org.projectzion.game.mmoconnector.utils.calls.ICall;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="users_calls")
public class UserCall {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    User user;

    @ManyToOne
    @JoinColumn(name="call_id")
    Call call;

    String serializedBean;

    CallState state;

    Date created;

    Date finished;
}