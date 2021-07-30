package org.projectzion.game.mmoconnector.persistence.entities;

import lombok.Getter;
import lombok.Setter;
import org.projectzion.game.mmoconnector.persistence.entities.rpc.Call;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="node_types_calls")
public class NodeTypeCall {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name="node_type_id")
    NodeType nodeType;

    @ManyToOne
    @JoinColumn(name="call_id")
    Call call;
}
