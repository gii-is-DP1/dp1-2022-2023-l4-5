package org.springframework.samples.nt4h.card.enemy.night_lord;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.nt4h.card.enemy.Enemy;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "night_lords")
public class NightLord extends Enemy {

}
