package org.springframework.samples.nt4h.turn;

import lombok.AllArgsConstructor;
import org.springframework.format.Formatter;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
@AllArgsConstructor
public class TurnFormatter implements Formatter<TurnDTO> {

    @Override
    public TurnDTO parse(String s, Locale locale) {
        System.out.println("PhaseFormatter.parse");
        return new TurnDTO(s);
    }

    @Override
    public String print(TurnDTO turnDTO, Locale locale) {
        return turnDTO.getPhase();
    }
}
