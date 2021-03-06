package org.projectzion.game.mmoconnector.persistence.entities;

import lombok.Getter;
import lombok.Setter;
import org.projectzion.game.mmoconnector.persistence.entities.security.User;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name="characters")
public class Character implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    User user;

    String name;

    @ManyToOne
    @JoinColumn(name="target_system_id")
    TargetSystem targetSystem;

    boolean isUsedForItemTransfer;
}
