package org.projectzion.game.mmoconnector.persistence;

import lombok.Getter;
import lombok.Setter;
import org.projectzion.game.persitence.entities.conditions.ItemCondition;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name="items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    //TODO localization
    String name;

    boolean tradeAble = false;

    //TODO why did i added this?
    boolean useAble = false;

    @OneToMany(mappedBy = "item")
    Collection<ItemCondition> itemConditions;

    //EFFECTS e.g. increase spawntime blub | increase gather amount blub | notifications | Achievment
}
