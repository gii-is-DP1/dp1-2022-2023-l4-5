package org.springframework.samples.nt4h.game;

public enum Accessibility {
    PUBLIC, PRIVATE;

    public boolean isPublic() {
        System.out.println(this == PUBLIC ? "true" : "false");
        return this.equals(PUBLIC);
    }
}
