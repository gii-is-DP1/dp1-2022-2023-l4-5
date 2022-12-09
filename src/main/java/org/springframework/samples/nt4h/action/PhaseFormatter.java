package org.springframework.samples.nt4h.action;

import lombok.AllArgsConstructor;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@AllArgsConstructor
public class PhaseFormatter implements Formatter<Phase> {

    @Override
    public Phase parse(String text, Locale locale) {
        System.out.println("PhaseFormatter.parse");
        return Phase.valueOf(text);
    }

    @Override
    public String print(Phase object, Locale locale) {
        System.out.println("PhaseFormatter.print");
        return object.toString();
    }
}
