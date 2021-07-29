package org.projectzion.game.mmoconnector.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="node_types")
public class NodeType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "nodeType")
    Set<NodeTypeTargetSystemCall> nodeTypeRewards;

    private int cooldown;


}
