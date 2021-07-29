package org.projectzion.game.mmoconnector.persistence;

import lombok.Getter;
import lombok.Setter;
import org.projectzion.game.mmoconnector.persistence.NodeType;
import org.projectzion.game.mmoconnector.persistence.rewards.Reward;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="node_types_rewards")
public class NodeTypeReward {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name="node_type_id")
    NodeType nodeType;

    @ManyToOne
    @JoinColumn(name="reward_id")
    Reward reward;
}
