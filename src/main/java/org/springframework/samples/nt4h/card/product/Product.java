package org.springframework.samples.nt4h.card.product;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.nt4h.capacity.Capacity;
import org.springframework.samples.nt4h.card.Card;
import org.springframework.samples.nt4h.card.ability.AbilityEffectEnum;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product extends Card {
    @NotNull
    @Min(1)
    private Integer quantity;

    @NotNull
    @Range(min = 3, max = 8)
    private Integer price;

    @NotNull
    @Range(min = 0, max = 4)
    private Integer attack;

    //TODO probar si se puede mandar una lista vacía
    @NotNull
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Capacity> capacity;

    private AbilityEffectEnum abilityEffectEnum;
}
