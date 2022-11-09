package org.springframework.samples.petclinic.card.product;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.capacity.Capacity;
import org.springframework.samples.petclinic.card.Card;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product extends Card {
    // TODO: decidir si es obligatorio.
    @Min(0)
    private Integer quantity;

    // TODO: decidir si es obligatorio.
    @Range(min = 3, max = 8)
    private Integer price;

    // TODO: decidir si es obligatorio.
    @Range(min = 0, max = 4)
    private Integer attack;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Capacity> capacity;
}
