package org.springframework.samples.petclinic.card.product;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.card.Card;

import javax.persistence.*;

@Getter
@Setter
@Entity

public class Product extends Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Range (min=3, max=8)
    private Integer price;
    @Range(min=0, max=4)
    private Integer attack;
}
