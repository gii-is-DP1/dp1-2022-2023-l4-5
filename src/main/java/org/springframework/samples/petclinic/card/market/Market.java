package org.springframework.samples.petclinic.card.market;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.card.product.Product;
import org.springframework.samples.petclinic.model.NamedEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "markets")
public class Market extends NamedEntity {

    @Range(min = 0, max = 5)
    private Integer amount;
    @Min(0)
    private Integer remainingAmount;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> product;
}
