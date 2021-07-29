package org.projectzion.game.mmoconnector.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

import org.projectzion.game.mmoconnector.persistence.rewards.TargetSystemCall;
import org.projectzion.game.mmoconnector.persistence.security.User;

@Getter
@Setter
@Entity
@Table(name="target_systems")
public class TargetSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;

    String ip;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    String token;
    //TODO API KEY

    @OneToMany(mappedBy = "targetSystem")
    Set<Character> character;

    @OneToMany(mappedBy = "targetSystem")
    Set<TargetSystemCall> reward;
}
