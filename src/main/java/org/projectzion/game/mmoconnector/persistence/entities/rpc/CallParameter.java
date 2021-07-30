package org.projectzion.game.mmoconnector.persistence.entities.rpc;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="call_parameters")
public class CallParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="call_id")
    Call call;

    @ManyToOne
    @JoinColumn(name="user_call_id")
    UserCall userCall;

    private Integer parameterIndex;

    private String name;

    private String serializedValue;

}
