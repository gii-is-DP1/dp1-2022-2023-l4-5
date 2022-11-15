package org.springframework.samples.nt4h.card.enemy.night_lord;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.nt4h.card.enemy.Enemy;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue("night_lord")
public class NightLord extends Enemy {

}
