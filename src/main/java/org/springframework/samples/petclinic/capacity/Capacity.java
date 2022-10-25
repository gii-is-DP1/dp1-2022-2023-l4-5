package org.springframework.samples.petclinic.capacity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.model.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "capacities")
public class Capacity extends BaseEntity {
    private StateCapacity stateCapacity;
    private Boolean lessDamage;
}
