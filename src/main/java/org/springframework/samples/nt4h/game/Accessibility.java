package org.springframework.samples.nt4h.game;

public enum Accessibility {
    PUBLIC, PRIVATE;

    public boolean isPublic() {
        return this.equals(PUBLIC);
    }
}
