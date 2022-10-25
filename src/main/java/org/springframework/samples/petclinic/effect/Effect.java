package org.springframework.samples.petclinic.effect;

import org.springframework.notimeforheroes.enumm.Phase;

public abstract class Effect {

    private Phase phase;

    abstract void useEffect();
}
