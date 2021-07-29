package org.projectzion.game.mmoconnector.persistence.rewards;

import lombok.Getter;
import lombok.Setter;
import org.projectzion.game.mmoconnector.persistence.NodeTypeTargetSystemCall;
import org.projectzion.game.mmoconnector.persistence.TargetSystem;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="target_system_calls")
public class TargetSystemCall {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name=" target_system_id")
    TargetSystem targetSystem;

    String fullyQualifiedBeanName;

    @OneToMany(mappedBy = "targetSystemCall")
    List<TargetSystemCallParameter> targetSystemCallParameters;

    @OneToMany(mappedBy = "reward")
    private Set<NodeTypeTargetSystemCall> nodeTypeRewards;
















}
