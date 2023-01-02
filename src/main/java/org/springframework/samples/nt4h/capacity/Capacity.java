package org.springframework.samples.nt4h.capacity;

import lombok.*;
import org.springframework.samples.nt4h.model.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "capacities")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Capacity extends BaseEntity {
    @NotNull
    @Enumerated(EnumType.STRING)
    private StateCapacity stateCapacity;
    @NotNull
    private Boolean lessDamage;
}
