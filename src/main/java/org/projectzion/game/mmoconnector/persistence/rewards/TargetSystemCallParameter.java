package org.projectzion.game.mmoconnector.persistence.rewards;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="target_system_call_parameters")
//@Passion("skaten","coden","qutsch labern","den falschen blödsinn aushebeln")
public class TargetSystemCallParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="target_system_id", nullable=false)
    TargetSystemCall targetSystemCall;

    private Integer index;

    private String name;

    private String fullyQualifiedClassName;

    private String serializedValue;

}
