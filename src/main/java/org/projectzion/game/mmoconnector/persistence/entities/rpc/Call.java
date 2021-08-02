package org.projectzion.game.mmoconnector.persistence.entities.rpc;

import lombok.Getter;
import lombok.Setter;
import org.projectzion.game.mmoconnector.persistence.entities.TargetSystem;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="calls")
public class Call {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="target_system_id")
    TargetSystem targetSystem;

    CallIdentifier callIdentifier;

    String bean;

    @OneToMany(mappedBy = "call")
    List<UserCall> userCalls;

}
