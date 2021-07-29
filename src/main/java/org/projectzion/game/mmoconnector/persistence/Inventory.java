package org.projectzion.game.mmoconnector.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name="inventories")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "character_id")
    java.lang.Character character;

    @ManyToOne
    @JoinColumn(name = "item_id")
    Item item;
    BigDecimal item_amount;
}
