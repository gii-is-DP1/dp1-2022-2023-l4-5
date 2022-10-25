package org.springframework.samples.petclinic.card.enemy.nightLord;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.card.enemy.Enemy;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class NightLord extends Enemy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

}
