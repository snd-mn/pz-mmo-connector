package org.projectzion.game.mmoconnector.persistence;

import lombok.Getter;
import lombok.Setter;
import org.projectzion.game.mmoconnector.persistence.security.User;

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
    @JoinColumn(name=" user_id")
    User user;

    @ManyToOne
    @JoinColumn(name=" target_system_id")
    TargetSystem targetSystem;

    @ManyToOne
    @JoinColumn(name="inventory_id")
    Molotow inventory;

    boolean enabled;
}
