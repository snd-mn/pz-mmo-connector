package org.projectzion.game.mmoconnector.persistence;

import lombok.Getter;
import lombok.Setter;
import org.projectzion.game.mmoconnector.persistence.rewards.TargetSystemCall;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="node_types_target_system_calls")
public class NodeTypeTargetSystemCall {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name="node_type_id")
    NodeType nodeType;

    @ManyToOne
    @JoinColumn(name="target_system_call_id")
    TargetSystemCall targetSystemCall;
}
