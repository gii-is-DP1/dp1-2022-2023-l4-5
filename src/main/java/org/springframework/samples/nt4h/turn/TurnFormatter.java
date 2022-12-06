package org.springframework.samples.nt4h.turn;

import lombok.AllArgsConstructor;
import org.springframework.format.Formatter;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@AllArgsConstructor
public class TurnFormatter implements Formatter<Turn> {

    private final TurnService turnService;
    private final UserService userService;

    @Override
    public Turn parse(String text, Locale locale) {
        System.out.println("TurnFormatter.parse");
        return turnService.getTurnsByPhaseAndPlayerId(Phase.valueOf(text), userService.getLoggedUser().getPlayer().getId());
    }

    @Override
    public String print(Turn turn, Locale locale) {
        System.out.println("TurnFormatter.print");
        return turn.getPhase().toString();
    }
}
