package org.springframework.samples.nt4h.message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class MessageTest {

    @Autowired
    private MessageService messageService;
    @Autowired
    private Validator validator;
    @Autowired
    private UserService userService;

    private Message message;
    private User sender;
    private User receiver;

    @BeforeEach
    public void setUp() {
        message = new Message();
        message.setContent("This is a test message");
        message.setTime(LocalDateTime.now());
        sender = userService.getUserById(1);
        message.setSender(sender);
        receiver = userService.getUserById(2);
        message.setReceiver(receiver);
    }

    @Test
    public void testMessageProperties() {
        assertThat(message.getContent()).isEqualTo("This is a test message");
        assertThat(message.getTime()).isNotNull();
        assertThat(message.getSender()).isEqualTo(sender);
        assertThat(message.getReceiver()).isEqualTo(receiver);
    }

    @Test
    public void testMessageConstraints() {
        // Test content constraints
        message.setContent(null);
        assertThat(validator.validate(message)).isNotEmpty();
        message.setContent("");
        assertThat(validator.validate(message)).isEmpty();
        message.setContent("a".repeat( 501));
        assertThat(validator.validate(message)).isNotEmpty();
        message.setContent("This is a test message");
        assertThat(validator.validate(message)).isEmpty();
    }

    @Test
    public void testMessageLifecycle() {
        // Test saving a message
        messageService.saveMessage(message);
        assertThat(message.getId()).isNotNull();

        // Test updating an existing message
        message.setContent("This is an updated test message");
        messageService.saveMessage(message);
        assertThat(message.getContent()).isEqualTo("This is an updated test message");

        // Test deleting an existing message
        assertThat(messageService.getMessageBySenderWithReceiver(sender.getUsername(), receiver.getUsername()).size()).isEqualTo(2);
        messageService.deleteMessage(message);
        assertThat(messageService.getMessageBySenderWithReceiver(sender.getUsername(), receiver.getUsername()).size()).isEqualTo(1);
    }

    @Test
    public void testMessageQueries() {
        // Test finding messages by sender and receiver
        assertThat(messageService.getMessageBySenderWithReceiver(sender.getUsername(), receiver.getUsername())).isNotEmpty();
        assertThat(messageService.getMessageBySenderWithReceiver("nonexistent", receiver.getUsername())).isEmpty();
        assertThat(messageService.getMessageBySenderWithReceiver(sender.getUsername(), "nonexistent")).isEmpty();
        assertThat(messageService.getMessageBySenderWithReceiver("nonexistent", "nonexistent")).isEmpty();
    }

}
