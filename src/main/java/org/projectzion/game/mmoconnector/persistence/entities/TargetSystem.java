package org.projectzion.game.mmoconnector.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

import org.projectzion.game.mmoconnector.persistence.entities.rpc.Call;
import org.projectzion.game.mmoconnector.persistence.entities.security.User;

@Getter
@Setter
@Entity
@Table(name="target_systems")
public class TargetSystem {
    @Id
    Long id;

    String name;

    String ip;

    String port;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User connectionUser;

    String token;
    //TODO API KEY

    @OneToMany(mappedBy = "targetSystem")
    Set<Character> characters;

    @OneToMany(mappedBy = "targetSystem")
    Set<Call> calls;

    @OneToMany(mappedBy = "targetSystem")
    Set<Item> items;
}
