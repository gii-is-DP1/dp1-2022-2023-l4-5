package org.springframework.samples.petclinic.card.enemy;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.model.BaseEntity;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class EnemyInGame extends BaseEntity {
    private Integer actualHealth;
}
