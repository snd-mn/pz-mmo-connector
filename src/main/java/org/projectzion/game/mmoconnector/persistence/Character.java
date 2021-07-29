package org.projectzion.game.mmoconnector.persistence;

import lombok.Getter;
import lombok.Setter;
import org.projectzion.game.persitence.entities.security.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

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
    Inventory inventory;

    @OneToMany(mappedBy = "character")
    private Set<CollectedNodes> collectedNodes;

    boolean enabled;



}
