package org.springframework.samples.petclinic.capacity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.model.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "capacities")
public class Capacity extends BaseEntity {
    @NotNull
    @Enumerated(EnumType.STRING)
    private StateCapacity stateCapacity;
    @NotNull
    private Boolean lessDamage;
}
