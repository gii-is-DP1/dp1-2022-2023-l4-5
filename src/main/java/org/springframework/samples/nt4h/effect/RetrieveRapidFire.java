package org.springframework.samples.nt4h.effect;

import org.springframework.samples.nt4h.player.Player;

public class RetrieveRapidFire extends Effect {
    @Override
    public boolean useEffect(Player player) {
        var abilityInGame = player.getInDiscard().stream().filter(c -> c.getAbility().getName().equals("Disparo Rápido")).findFirst();
        if (abilityInGame.isPresent()) {
            player.getInDiscard().remove(abilityInGame.get());
            player.getInDeck().add(abilityInGame.get());
        }
        return true;
    }
}
