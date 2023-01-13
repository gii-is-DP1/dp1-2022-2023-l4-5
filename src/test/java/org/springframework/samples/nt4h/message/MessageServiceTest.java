package org.springframework.samples.nt4h.message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@DataJpaTest(
    includeFilters = {@Filter({Service.class})}
)
public class MessageServiceTest {
    @Autowired
    protected MessageService messageService;
    @Autowired
    protected UserService userService;

    @MockBean
    private Advise advise;

    public MessageServiceTest() {
    }

    @Test
    public void findByIDTrue() {
        List<Message> message = this.messageService.getMessageBySenderWithReceiver("","");
        Assertions.assertNotNull(message);
        Assertions.assertTrue(message.isEmpty());
    }

    @Test
    public void findByIDFalse() {
        List<Message> message = this.messageService.getMessageBySenderWithReceiver("","");
        Assertions.assertNotNull(message);
        Assertions.assertFalse(!(message.isEmpty()));
    }

    @Test
    public void findByMessageSenderAndReceiver() {
        List<Message> messages = this.messageService.getMessageBySenderWithReceiver("alesanfe", "antonio");
        Assertions.assertNotNull(messages);
        Assertions.assertFalse(messages.isEmpty());
        Assertions.assertEquals(1, messages.size());
    }

    @Test
    public void shouldInsertMessage() {
        Message message = new Message();
        message.setContent("Hola");
        User sender = this.userService.getUserById(1);
        message.setSender(sender);
        User receiver = this.userService.getUserById(2);
        message.setReceiver(receiver);
        this.messageService.saveMessage(message);
        Message m = (Message)this.messageService.getMessageBySenderWithReceiver("alesanfe", "antonio").get(1);
        Assertions.assertEquals(message, m);
    }
}

