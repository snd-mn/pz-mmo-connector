package org.projectzion.game.mmoconnector.persistence.rewards;

import lombok.Getter;
import lombok.Setter;
import org.projectzion.game.mmoconnector.persistence.Item;
import org.projectzion.game.persitence.entities.Item;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class ItemReward extends Reward{
    @ManyToOne
    Item item;
    double itemAmount;
}
