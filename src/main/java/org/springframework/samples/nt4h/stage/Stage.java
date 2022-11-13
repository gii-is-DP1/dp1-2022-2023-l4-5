package org.springframework.samples.nt4h.stage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.nt4h.card.Card;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class Stage extends Card {

}
