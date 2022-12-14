package org.springframework.samples.nt4h.message;
/*
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Service;

@DataJpaTest(
    includeFilters = {@Filter({Service.class})}
)
public class MessageServiceTest {
    @Autowired
    protected MessageService messageService;
    @Autowired
    protected UserService userService;

    public MessageServiceTest() {
    }

    @Test
    public void findByIDTrue() {
        ListMessage message = this.messageService.getMessageBySenderWithReceiver("","");
        Assertions.assertNotNull(message);
        Assertions.assertEquals("Hola, soy Alesanfe", message.getContent());
    }

    @Test
    public void findByIDFalse() {
        Message message = this.messageService.getMessageBySenderWithReceiver("","");
        Assertions.assertNotNull(message);
        Assertions.assertNotEquals("Adios", message.getContent());
    }

    @Test
    public void findByMessageSenderAndReceiver() {
        List<Message> messages = this.messageService.getMessageBySenderWithReceiver("alesanfe", "antonio");
        Assertions.assertNotNull(messages);
        Assertions.assertFalse(messages.isEmpty());
        Assertions.assertEquals(2, messages.size());
    }

    @Test
    public void findAll() {
        List<Message> ls = this.messageService.getAllMessages();
        Assertions.assertNotNull(ls);
        Assertions.assertFalse(ls.isEmpty());
        Assertions.assertEquals(2, ls.size());
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
        Message m = (Message)this.messageService.getMessageBySenderWithReceiver("alesanfe", "antonio").get(2);
        Assertions.assertEquals(message, m);
    }

    @Test
    public void deleteMessageTest() {
        this.messageService.deleteMessageById(1);
        Assertions.assertFalse(this.messageService.messageExists(1));
    }
}

 */
