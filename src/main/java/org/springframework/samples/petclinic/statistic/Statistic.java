package org.springframework.samples.petclinic.statistic;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.card.ability.Ability;
import org.springframework.samples.petclinic.card.product.Product;
import org.springframework.samples.petclinic.model.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;
import org.springframework.samples.petclinic.model.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.List;
@Entity
@Getter
@Setter
public class Statistic extends BaseEntity {

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

        //hace falta hacer los calculos del max para sacar los valores
        @OneToOne(cascade = CascadeType.ALL)
        private Product products;

        @OneToOne(cascade = CascadeType.ALL)
        private Ability abilities;
}
