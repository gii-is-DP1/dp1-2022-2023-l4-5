package org.springframework.samples.nt4h.card.hero;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public enum Role {
    WIZARD(Lists.newArrayList(16, 17, 18, 19, 20, 21, 22, 23, 24)),
    KNIGHT(Lists.newArrayList(8, 9, 10, 11, 12, 13, 14, 15)),
    EXPLORER(Lists.newArrayList(1, 2, 3, 4, 5, 6, 7)),
    THIEF(Lists.newArrayList(25, 26, 27, 28, 29, 30, 30, 31, 32));

    List<Integer> abilities;

    Role(ArrayList<Integer> abilities) {
        this.abilities = abilities;
    }

    public List<Integer> getAbilities() {
        return abilities;
    }
}
