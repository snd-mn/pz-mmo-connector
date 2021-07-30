package org.projectzion.game.mmoconnector.persistence;

import lombok.Getter;
import lombok.Setter;
import org.projectzion.game.mmoconnector.persistence.rewards.TargetSystemCall;
import org.projectzion.game.mmoconnector.persistence.security.User;
import org.projectzion.game.mmoconnector.utils.CallState;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name="molotows")
//@Brain("https://www.youtube.com/watch?v=n4AUY-v1nsE&list=RDMM&index=14")
public class Molotow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    User user;

    @ManyToOne
    TargetSystemCall targetSystemCall;

    CallState state;

}
