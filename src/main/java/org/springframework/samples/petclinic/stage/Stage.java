package org.springframework.samples.petclinic.stage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.gameManager.GameManager;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class Stage extends Card {

    @ManyToOne(optional=true)
    private GameManager gameManager;

    @ManyToOne(optional=false)
    private GameManager gameManager2;

    //a
}
