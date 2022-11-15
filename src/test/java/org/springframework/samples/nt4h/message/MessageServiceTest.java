package org.springframework.samples.nt4h.message;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class MessageServiceTest {
    @Autowired
    protected MessageService messageService;
    @Autowired
    protected UserService userService;


    @Test
    public void findByIDTrue(){
        Message message = messageService.getMessageById(1);
        assertNotNull(message);
        assertEquals("Hola",message.getContent() );
    }
    @Test
    public void findByIDFalse(){
        Message message = messageService.getMessageById(1);
        assertNotNull(message);
        assertNotEquals("Adios",message.getContent() );
    }
    @Test
    public void findByMessageSenderAndReceiver(){
        List<Message> messages = messageService.getMessageBySenderWithReceiver("alesanfe","antonio");
        assertNotNull(messages);
        assertFalse(messages.isEmpty());
        assertEquals(2,messages.size());
    }
    //TO DO
    @Test
    public void shouldInsertMessage(){
        Message message = new Message();
        message.setContent("Hola");
        User sender = userService.getUserById(1);
        message.setSender(sender);
        User receiver = userService.getUserById(2);
        message.setReceiver(receiver);
        messageService.saveMessage(message);
        Message m= messageService.getMessageBySenderWithReceiver("alesanfe","antonio").get(2);
        assertEquals(message,m);
    }

}
