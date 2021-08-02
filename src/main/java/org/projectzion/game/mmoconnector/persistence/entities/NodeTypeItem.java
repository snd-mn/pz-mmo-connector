package org.projectzion.game.mmoconnector.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="node_types_items")
public class NodeTypeItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name="node_type_id")
    NodeType nodeType;

    @ManyToOne
    @JoinColumn(name="item_id")
    Item item;

    Float amount;
}
