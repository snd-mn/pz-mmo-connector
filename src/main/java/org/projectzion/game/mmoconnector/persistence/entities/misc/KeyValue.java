package org.projectzion.game.mmoconnector.persistence.entities.misc;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

//TODO extend with Timestamps base entity mopped
@Getter
@Setter
@Entity
@Table(name = "key_values")
public class KeyValue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "the_key", unique = true, nullable = false)
    String key;

    @Column(name = "the_value")
    String value;
}
