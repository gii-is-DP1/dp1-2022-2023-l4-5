package org.springframework.samples.petclinic.message;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.user.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "messages")
public class Message extends BaseEntity {

    @NotNull
    @Size(min = 1, max = 100)
    private String content;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime time;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    @Override
    public String toString() {
        return sender.getUsername() + ": " + content;
    }

}
