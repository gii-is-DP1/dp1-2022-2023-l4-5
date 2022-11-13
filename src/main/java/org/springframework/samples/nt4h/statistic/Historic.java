package org.springframework.samples.nt4h.statistic;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.nt4h.card.ability.Ability;
import org.springframework.samples.nt4h.card.product.Product;
import org.springframework.samples.nt4h.model.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import java.util.List;

@Entity
@Getter
@Setter
public class Historic extends BaseEntity {

    @Min(value = 0)
    private Integer numOrcsKilled;

    @Min(value = 0)
    private Integer numWarLordKilled;

    @Min(value = 0)
    private Integer numGamesPlayed;

    @Min(value = 0)
    private Integer numGamesWon;

    @Min(value = 0)
    private Integer numGamesLost;

    @Min(value = 0)
    private Integer glory;

    @Min(value = 0)
    private Integer gold;

    @Min(value = 0)
    private Integer duration;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> products;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Ability> abilities;
}
