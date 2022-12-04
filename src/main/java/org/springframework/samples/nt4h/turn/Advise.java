package org.springframework.samples.nt4h.turn;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Advise {

    private String message = "";
    private String messageType = "";

    public void resetMessage() {
        this.message = "";
        this.messageType = "";
    }

    public void resetMessageType() {
        this.messageType = "";
    }

    public String sendError(String message, String redirect) {
        this.message = message;
        this.messageType = "error";
        return redirect;
    }
}
