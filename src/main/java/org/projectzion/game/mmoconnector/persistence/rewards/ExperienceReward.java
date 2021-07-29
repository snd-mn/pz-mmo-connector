package org.projectzion.game.mmoconnector.persistence.rewards;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class ExperienceReward extends Reward{
    Long experience;
}
