package org.springframework.samples.nt4h.message;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.nt4h.model.BaseEntity;
import org.springframework.samples.nt4h.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.io.Serializable;
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

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime time;

    @ManyToOne(cascade = CascadeType.ALL)
    private User sender;

    //TODO probar Many To Many para un chat de grupo
    @ManyToOne(cascade = CascadeType.ALL)
    private User receiver;

    @Override
    public String toString() {
        return sender.getUsername() + ": " + content;
    }

    @Override
    public String toJson() {
        JsonObject json = new JsonObject();
        json.put("content", this.getContent());
        json.put("time", this.getTime().toString());
        json.put("sender", this.getSender().getUsername());
        json.put("receiver", this.getReceiver().getUsername());
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
