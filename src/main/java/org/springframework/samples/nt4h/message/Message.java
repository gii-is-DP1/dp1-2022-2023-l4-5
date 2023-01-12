package org.springframework.samples.nt4h.message;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.model.BaseEntity;
import org.springframework.samples.nt4h.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "messages")
public class Message extends BaseEntity implements Jsonable {

    @NotNull
    @Size(max = 500)
    private String content;

    private boolean read;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime time;

    @ManyToOne(cascade = CascadeType.ALL)
    private User sender;
    @ManyToOne(cascade = CascadeType.ALL)
    private User receiver;

    @ManyToOne(cascade = CascadeType.ALL)
    private Game game;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    public void onDeleteSetNull() {
        sender = null;
        receiver = null;
        game = null;
    }

    @Override
    public String toJson() {
        JsonObject json = new JsonObject();
        json.put("content", content);
        json.put("time", time.toString());
        if (type != MessageType.ADVISE)json.put("sender", sender.getUsername());
        if (type != MessageType.GAME && type != MessageType.ADVISE ) json.put("receiver", receiver.getUsername());
        if (type != MessageType.CHAT) json.put("game", game.getName());
        json.put("read", read);
        json.put("type", type.toString());
        return json.toJson();
    }

    @Override
    public void toJson(Writer writer) {
        try {
            writer.write(toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
