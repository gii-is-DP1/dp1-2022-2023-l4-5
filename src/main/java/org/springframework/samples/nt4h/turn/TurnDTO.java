package org.springframework.samples.nt4h.turn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.samples.nt4h.action.Phase;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TurnDTO {
    private String phase;
}
