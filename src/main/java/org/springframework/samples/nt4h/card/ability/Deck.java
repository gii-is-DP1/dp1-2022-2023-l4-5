package org.springframework.samples.nt4h.card.ability;

import com.google.common.collect.Lists;
import lombok.*;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.action.RemoveCardFromHandToDiscard;
import org.springframework.samples.nt4h.action.TakeCardFromAbilityPile;
import org.springframework.samples.nt4h.model.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Deck extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL)
    private List<AbilityInGame> inHand;
    @OneToMany(cascade = CascadeType.ALL)
    private List<AbilityInGame> inDeck;
    @OneToMany(cascade = CascadeType.ALL)
    private List<AbilityInGame> inDiscard;

    public static Deck createEmptyDeck() {
        return Deck.builder().inDeck(Lists.newArrayList()).inHand(Lists.newArrayList()).inDiscard(Lists.newArrayList()).build();
    }

    public void onDeleteSetNull() {
        inHand.forEach(AbilityInGame::onDeleteSetNull);
        inDeck.forEach(AbilityInGame::onDeleteSetNull);
        inDiscard.forEach(AbilityInGame::onDeleteSetNull);
    }


}
