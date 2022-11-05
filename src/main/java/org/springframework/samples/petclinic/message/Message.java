package org.springframework.samples.petclinic.message;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.user.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Message extends BaseEntity {

    @NotNull
    @Size(min = 1, max = 100)
    private String content;

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
