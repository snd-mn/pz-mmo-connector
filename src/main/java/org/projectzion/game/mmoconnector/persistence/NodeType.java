package org.projectzion.game.mmoconnector.persistence;

import lombok.Getter;
import lombok.Setter;
import org.projectzion.game.persitence.entities.conditions.Condition;
import org.projectzion.game.utils.DisplayResourceType;

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
    Set<NodeTypeReward> nodeTypeRewards;

    @OneToMany(mappedBy = "nodeType")
    Set<OsmMatcherNodeType> osmMatcherNodeTypes;

    private DisplayResourceType displayResourceType;

    private int cooldown;


}
