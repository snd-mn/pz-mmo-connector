package org.projectzion.game.mmoconnector.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "target_system_id")
    TargetSystem targetSystem;

    Long targetSystemItemId;

    @OneToMany(mappedBy = "item")
    List<NodeTypeItem> nodeTypeItems;
}
