package org.projectzion.game.mmoconnector.persistence.entities.rpc;

import lombok.Getter;
import lombok.Setter;
import org.projectzion.game.mmoconnector.persistence.entities.NodeTypeCall;
import org.projectzion.game.mmoconnector.persistence.entities.TargetSystem;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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
    Set<NodeTypeCall> nodeTypeCalls;

    @OneToMany(mappedBy = "call")
    List<UserCall> userCalls;

}
